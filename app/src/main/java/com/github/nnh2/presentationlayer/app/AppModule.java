package com.github.nnh2.presentationlayer.app;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.nnh2.domainlayer.ImageProcesses;
import com.github.nnh2.domainlayer.providers.ImageStoreProvider;
import com.github.nnh2.domainlayer.providers.SchedulersProvider;

import net.jokubasdargis.rxbus.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zahar on 24.03.16.
 */
@Module
@Singleton
public class AppModule {

	@NonNull
	private Context context;

	public AppModule(@NonNull Context context) {
		this.context = context;
	}

	@Provides @NonNull @Singleton
	public Context provideContext() {
		return context;
	}

	@Provides @NonNull @Singleton
	public ImageProcesses provideImageProcesses(Bus bus, SchedulersProvider schedular, ImageStoreProvider imageStoreProvider) {
		return new ImageProcesses(bus,schedular,imageStoreProvider);
	}



}
