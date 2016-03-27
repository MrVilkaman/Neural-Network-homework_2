package com.github.nnh2.datalayer.providers;

import com.github.nnh2.datalayer.store.LocalCacheItemType;
import com.github.nnh2.datalayer.store.LocalStorage;
import com.github.nnh2.datalayer.store.MemoryStorage;
import com.github.nnh2.domainlayer.providers.SessionDataProvider;

/**
 * Created by Zahar on 16.03.16.
 */
public class SessionDataProviderImpl implements SessionDataProvider {
	private final LocalStorage localStorage;
	private final MemoryStorage memoryStorage;

	public SessionDataProviderImpl(LocalStorage localStorage,MemoryStorage memoryStorage) {
		this.localStorage = localStorage;
		this.memoryStorage = memoryStorage;
	}

	@Override
	public String getToken() {
		String token = memoryStorage.get(LocalCacheItemType.TOKEN);
		return token != null ? token : localStorage.getToken();
	}

	@Override
	public void saveToken(String token) {
		localStorage.saveToken(token);
		memoryStorage.save(LocalCacheItemType.TOKEN,token);
	}
}
