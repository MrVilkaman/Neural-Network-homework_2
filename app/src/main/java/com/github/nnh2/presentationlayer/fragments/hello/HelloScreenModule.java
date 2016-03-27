package com.github.nnh2.presentationlayer.fragments.hello;

import android.support.annotation.NonNull;

import com.github.nnh2.domainlayer.providers.SchedulersProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zahar on 24.03.16.
 */
@Module
public class HelloScreenModule {

	@Provides
	@NonNull
	HelloScreenPresenter provideHelloScreenPresenter(SchedulersProvider schedulers) {
		return new HelloScreenPresenter(schedulers);
	}
}

