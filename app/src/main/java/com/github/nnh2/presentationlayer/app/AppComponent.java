package com.github.nnh2.presentationlayer.app;

import android.support.annotation.NonNull;

import com.github.nnh2.datalayer.providers.ProvidersModule;
import com.github.nnh2.domainlayer.providers.SchedulersProvider;
import com.github.nnh2.domainlayer.providers.SessionDataProvider;
import com.github.nnh2.presentationlayer.activities.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Zahar on 24.03.16.
 */
@Component(modules = {AppModule.class, ProvidersModule.class})
@Singleton
public interface AppComponent {

	SessionDataProvider getSessionDataProvider();

	SchedulersProvider getSchedulersProvider();


	void inject(@NonNull App app);

	void inject(@NonNull MainActivity mainActivity);
}
