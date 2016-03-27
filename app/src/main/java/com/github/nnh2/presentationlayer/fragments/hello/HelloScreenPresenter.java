package com.github.nnh2.presentationlayer.fragments.hello;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.github.nnh2.domainlayer.providers.SchedulersProvider;
import com.github.nnh2.presentationlayer.fragments.core.BasePresenter;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by root on 12.03.16.
 */

public class HelloScreenPresenter extends BasePresenter<HelloScreenView> {

	private SchedulersProvider schedulers;

	@Inject
	public HelloScreenPresenter(SchedulersProvider schedulers) {
		this.schedulers = schedulers;
	}

	@Override
	protected void onViewAttached() {
		newPhotoTaken();
	}

	public void newPhotoTaken() {
		Observable.just(view().getLastPath())
				.map(this::readImage)
				.subscribeOn(schedulers.io())
				.observeOn(schedulers.mainThread())
				.subscribe(bm -> view().setImage(bm));

	}

	private Bitmap readImage(String path) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		return BitmapFactory.decodeFile(path, options);
	}
}
