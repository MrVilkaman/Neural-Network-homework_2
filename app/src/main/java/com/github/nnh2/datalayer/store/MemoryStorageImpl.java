package com.github.nnh2.datalayer.store;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Zahar on 18.03.16.
 */
public class MemoryStorageImpl implements MemoryStorage {

	private Map<LocalCacheItemType,Object> friends = new HashMap<>();

	@Override
	public <T> void save(LocalCacheItemType type, T object) {
		friends.put(type,object);
	}

	@Override
	public <T> T get(LocalCacheItemType type) {
		return (T)friends.get(type);
	}

	@Override
	public boolean has(LocalCacheItemType type) {
		return friends.containsKey(type);
	}

}
