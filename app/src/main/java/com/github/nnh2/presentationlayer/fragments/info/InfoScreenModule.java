package com.github.nnh2.presentationlayer.fragments.info;

import android.support.annotation.NonNull;

import com.github.nnh2.domainlayer.providers.SchedulersProvider;

import net.jokubasdargis.rxbus.Bus;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zahar on 03.04.16.
 */
@Module
public class InfoScreenModule {

	@Provides
	@NonNull
	InfoPresenter provideInfoPresenter(SchedulersProvider schedulersProvider,Bus bus) {
		return new InfoPresenter(schedulersProvider,bus);
	}
}