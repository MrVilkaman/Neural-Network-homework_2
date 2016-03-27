package com.github.nnh2.presentationlayer.fragments.hello;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.github.nnh2.domainlayer.filters.Filters;
import com.github.nnh2.domainlayer.providers.SchedulersProvider;
import com.github.nnh2.presentationlayer.fragments.core.BasePresenter;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by root on 12.03.16.
 */

public class HelloScreenPresenter extends BasePresenter<HelloScreenView> {

	private SchedulersProvider schedulers;
	private Filters filters;

	@Inject
	public HelloScreenPresenter(SchedulersProvider schedulers,Filters filters) {
		this.schedulers = schedulers;
		this.filters = filters;
	}

	@Override
	protected void onViewAttached() {
		newPhotoTaken();
	}

	public void newPhotoTaken() {
		view().showProgress();
		Observable.just(view().getLastPath())
				.map(this::readImage)
				.subscribeOn(schedulers.io())
				.observeOn(schedulers.mainThread())
				.doOnNext(bm -> view().setImage(bm))
				.observeOn(schedulers.computation())
				.map(filters::transform)
				.observeOn(schedulers.mainThread())
				.doOnTerminate(() -> view().hideProgress())
				.subscribe(bm -> view().setImage(bm));

	}

	private Bitmap readImage(String path) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		options.inMutable = true;
		return BitmapFactory.decodeFile(path, options);
	}
}
