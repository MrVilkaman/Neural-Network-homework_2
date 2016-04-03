package com.github.nnh2.datalayer.eventbus;

import android.util.Log;

import net.jokubasdargis.rxbus.Bus;
import net.jokubasdargis.rxbus.RxBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zahar on 03.04.16.
 */
@Module
public class EventBusModule {

	@Provides
	@Singleton
	public Bus provideRxBus(){
		return RxBus.create(message -> Log.d("Bus",message));
	}
}
