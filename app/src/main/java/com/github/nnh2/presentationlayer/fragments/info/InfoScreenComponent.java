package com.github.nnh2.presentationlayer.fragments.info;
/**
 * Created by Zahar on 03.04.16.
 */

import com.github.nnh2.presentationlayer.app.AppComponent;
import com.github.nnh2.presentationlayer.app.PerActivity;

import dagger.Component;

/**
 * Created by Zahar on 24.03.16.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = {InfoScreenModule.class})
public interface InfoScreenComponent {
	void inject(InfoScreenFragment fragment);
}