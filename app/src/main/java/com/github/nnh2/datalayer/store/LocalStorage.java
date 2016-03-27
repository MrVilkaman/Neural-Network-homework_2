package com.github.nnh2.datalayer.store;

/**
 * Created by root on 12.03.16.
 */
public interface LocalStorage {

	String getToken();
	void saveToken(String token);
}
