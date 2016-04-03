package com.github.nnh2.presentationlayer.fragments.hello;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.github.nnh2.datalayer.entity.PixelWrapper;
import com.github.nnh2.datalayer.eventbus.ImageContentEvent;
import com.github.nnh2.datalayer.eventbus.ImageInfoEvent;
import com.github.nnh2.datalayer.eventbus.QueriesBus;
import com.github.nnh2.datalayer.providers.DefaultSubscriber;
import com.github.nnh2.domainlayer.filters.Filters;
import com.github.nnh2.domainlayer.providers.ImageStoreProvider;
import com.github.nnh2.domainlayer.providers.SchedulersProvider;
import com.github.nnh2.presentationlayer.fragments.core.BasePresenter;

import net.jokubasdargis.rxbus.Bus;

import java.io.InputStream;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;
import rx.Subscription;

/**
 * Created by root on 12.03.16.
 */

public class HelloScreenPresenter extends BasePresenter<HelloScreenView> {

	private SchedulersProvider schedulers;
	private Filters filters;
	private Bus bus;
	private Subscription showImageBus;
	private ImageStoreProvider imageStoreProvider;


	@Inject
	public HelloScreenPresenter(SchedulersProvider schedulers, Filters filters, Bus bus, ImageStoreProvider imageStoreProvider) {
		this.schedulers = schedulers;
		this.filters = filters;
		this.bus = bus;
		this.imageStoreProvider = imageStoreProvider;
	}

	@Override
	protected void onViewDetached() {
		showImageBus.unsubscribe();
	}

	@Override
	protected void onViewAttached() {
		ShowImageObs showImageObs = new ShowImageObs(view(), schedulers.mainThread(), imageStoreProvider);
		showImageBus = bus.subscribe(QueriesBus.SHOW_IMAGE, showImageObs, schedulers.computation());
//		MedianFilter filter = new MedianFilter(3);
//
//		int[] pixels = new int[16];
//		filter.doWork(pixels,4,4);
		newPhotoTaken();
	}

	public void newPhotoTaken() {
		view().showProgress();
		Observable.just(view().getLastPath())
				.map(this::readImage)
				.concatWith(Observable.just(readImageFromAsset(view().getAssetStreem())))
				.first(bitmap -> bitmap != null)
				.subscribeOn(schedulers.io())
//				.observeOn(schedulers.mainThread())
//				.doOnNext(bm -> view().setImage(bm))
				.observeOn(schedulers.computation())
				.first(bitmapWrapper -> bitmapWrapper != null)
				.map(bitmap -> filters.transform(bitmap))
				.doOnNext(this::sendImageInfo)
				.observeOn(schedulers.mainThread())
				.doOnTerminate(() -> view().hideProgress())
				.subscribe(bm -> view().setImage(bm),
						throwable -> view().showMessage(throwable.getMessage()));

	}

	private void sendImageInfo(Bitmap bitmap) {
		int height = bitmap.getHeight();
		int width = bitmap.getWidth();
		int[] pixels = new int[height * width];

		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
		int count = 0;
		for (int i = 0; i < height; i++) {
			final int current = i * width;
			for (int j = 0; j < width; j++) {
				int index = current + j;
				int pixel = pixels[index];

				if (pixel == Color.BLACK) {
					count++;
				}
			}
		}

		bus.publish(QueriesBus.IMAGE_INFO, new ImageInfoEvent(count, width, height));
		bus.publish(QueriesBus.IMAGE_HANDLE, new ImageContentEvent(pixels, width, height));
	}

	private Bitmap readImage(String path) {
		if (path == null) {
			return null;
		}

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		options.inMutable = true;
		return BitmapFactory.decodeFile(path, options);
	}

	private static Bitmap readImageFromAsset(InputStream streem) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		options.inMutable = true;
		Bitmap bitmap = BitmapFactory.decodeStream(streem);
		if (bitmap != null) {
			return bitmap.copy(bitmap.getConfig(), true);
		} else {
			return null;
		}
	}

	public static class ShowImageObs extends DefaultSubscriber<String> {
		private final HelloScreenView view;
		private final Scheduler scheduler;
		private final ImageStoreProvider imageStoreProvider;

		public ShowImageObs(HelloScreenView view, Scheduler scheduler, ImageStoreProvider imageStoreProvider) {
			this.view = view;
			this.scheduler = scheduler;
			this.imageStoreProvider = imageStoreProvider;
		}

		@Override
		public void onNext(String path) {

			for (PixelWrapper wrapper : imageStoreProvider.getImages()) {
				if (wrapper.getName()
						.equals(path)) {
					Observable.just(wrapper)
							.map(pixelWrapper -> {
								int width = pixelWrapper.getWidth();
								int height = pixelWrapper.getHeight();
								Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
								bitmap.setPixels(pixelWrapper.getPixels(), 0, width, 0, 0, width, height);
								return bitmap;
							})
							.observeOn(scheduler)
							.subscribe(view::showImage);
					return;
				}
			}

		}
	}

}
