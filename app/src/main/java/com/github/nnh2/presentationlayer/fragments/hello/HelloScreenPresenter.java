package com.github.nnh2.presentationlayer.fragments.hello;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.github.nnh2.datalayer.eventbus.ImageContentEvent;
import com.github.nnh2.datalayer.eventbus.QueriesBus;
import com.github.nnh2.datalayer.eventbus.ImageInfoEvent;
import com.github.nnh2.domainlayer.filters.Filters;
import com.github.nnh2.domainlayer.providers.SchedulersProvider;
import com.github.nnh2.presentationlayer.fragments.core.BasePresenter;

import net.jokubasdargis.rxbus.Bus;

import java.io.InputStream;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by root on 12.03.16.
 */

public class HelloScreenPresenter extends BasePresenter<HelloScreenView> {

	private SchedulersProvider schedulers;
	private Filters filters;
	private Bus bus;


	@Inject
	public HelloScreenPresenter(SchedulersProvider schedulers,Filters filters,Bus bus) {
		this.schedulers = schedulers;
		this.filters = filters;
		this.bus = bus;
	}

	@Override
	protected void onViewAttached() {

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
				.first( bitmap -> bitmap != null)
				.subscribeOn(schedulers.io())
//				.observeOn(schedulers.mainThread())
//				.doOnNext(bm -> view().setImage(bm))
				.observeOn(schedulers.computation())
				.map(filters::transform)
				.doOnNext(this::sendImageInfo)
				.observeOn(schedulers.mainThread())
				.doOnTerminate(() -> view().hideProgress())
				.subscribe(bm -> view().setImage(bm));

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

				if (pixel == Color.BLACK){
					count++;
				}
			}
		}

		bus.publish(QueriesBus.IMAGE_INFO,new ImageInfoEvent(count,width,height));
		bus.publish(QueriesBus.IMAGE_HANDLE,new ImageContentEvent(pixels,width,height));
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

	private Bitmap readImageFromAsset(InputStream streem) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		options.inMutable = true;
		Bitmap bitmap = BitmapFactory.decodeStream(streem);
		return bitmap.copy(bitmap.getConfig(),true);
	}
}
