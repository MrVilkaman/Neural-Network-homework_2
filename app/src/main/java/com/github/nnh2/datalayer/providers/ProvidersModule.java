package com.github.nnh2.datalayer.providers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.nnh2.datalayer.store.LocalStorage;
import com.github.nnh2.datalayer.store.LocalStorageImpl;
import com.github.nnh2.datalayer.store.MemoryStorage;
import com.github.nnh2.datalayer.store.MemoryStorageImpl;
import com.github.nnh2.domainlayer.providers.SchedulersProvider;
import com.github.nnh2.domainlayer.providers.SessionDataProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zahar on 24.03.16.
 */
@Module
@Singleton
public class ProvidersModule {

	@Singleton
	@Provides
	SessionDataProvider provideSessionDataProvider(LocalStorage localStorage, MemoryStorage memoryStorage) {
		return new SessionDataProviderImpl(localStorage, memoryStorage);
	}

	@Singleton
	@Provides
	SchedulersProvider provideSchedulersProvider() {
		return new MainSchedulersProvider();
	}

	@Singleton
	@Provides
	@NonNull
	LocalStorage provideLocalStorage(Context context) {
		return new LocalStorageImpl(context);
	}

	@Singleton
	@Provides
	@NonNull
	MemoryStorage provideMemoryStorage() {
		return new MemoryStorageImpl();
	}

}
