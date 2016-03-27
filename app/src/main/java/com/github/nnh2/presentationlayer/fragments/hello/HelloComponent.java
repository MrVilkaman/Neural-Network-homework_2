package com.github.nnh2.presentationlayer.fragments.hello;

import com.github.nnh2.presentationlayer.app.AppComponent;
import com.github.nnh2.presentationlayer.app.PerActivity;

import dagger.Component;

/**
 * Created by Zahar on 24.03.16.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = {HelloScreenModule.class})
public interface HelloComponent {
	void inject(HelloScreenFragment fragment);
}
