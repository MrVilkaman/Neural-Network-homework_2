package com.github.nnh2.domainlayer.usecase;

import com.github.nnh2.domainlayer.providers.SchedulersProvider;

/**
 * Created by Zahar on 19.03.16.
 */
public abstract class LoadUseCase<T> extends UseCase<T> {

	public LoadUseCase(SchedulersProvider provider){
		super(provider.io(), provider.mainThread());
	}
}
