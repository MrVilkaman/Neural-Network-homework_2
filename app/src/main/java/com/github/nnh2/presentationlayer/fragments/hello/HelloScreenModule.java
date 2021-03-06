package com.github.nnh2.presentationlayer.fragments.hello;

import android.support.annotation.NonNull;

import com.github.nnh2.datalayer.Const;
import com.github.nnh2.domainlayer.filters.CombiFilter;
import com.github.nnh2.domainlayer.filters.Filters;
import com.github.nnh2.domainlayer.filters.MonochromeFilter;
import com.github.nnh2.domainlayer.filters.ResizeFilter;
import com.github.nnh2.domainlayer.filters.ScaleFilter;
import com.github.nnh2.domainlayer.providers.ImageStoreProvider;
import com.github.nnh2.domainlayer.providers.SchedulersProvider;

import net.jokubasdargis.rxbus.Bus;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zahar on 24.03.16.
 */
@Module
public class HelloScreenModule {

	@Provides
	@NonNull
	HelloScreenPresenter provideHelloScreenPresenter(SchedulersProvider schedulers, Filters filters, Bus bus, ImageStoreProvider imageStore) {
		return new HelloScreenPresenter(schedulers, filters,bus,imageStore);
	}


	@Provides
	@NonNull
	Filters provideFilters() {
		int level = 90;
		return new CombiFilter(
//				new MedianFilter(3)
//				new ResizeFilter(90,120),
				new ScaleFilter(level, 0.05f, 2, false),
				new ResizeFilter(Const.WIDTH, Const.HEIGHT),
//				new ResizeFilter(60, 80),
				new MonochromeFilter(level)
		);
	}
}

