package com.github.nnh2.datalayer.providers;

import android.util.Log;

import com.github.mrvilkaman.namegenerator.BuildConfig;

/**
 * Created by root on 15.03.16.
 */
public class DefaultSubscriber<T> extends rx.Subscriber<T>  {

	@Override
	public void onNext(T t) {

	}

	@Override
	public void onError(Throwable e) {
		if (BuildConfig.DEBUG) {
			Log.d("DefaultSubscriber","onError",e);
		}
	}

	@Override
	public void onCompleted() {

	}
}
