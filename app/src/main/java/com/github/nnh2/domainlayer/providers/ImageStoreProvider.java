package com.github.nnh2.domainlayer.providers;

import com.github.nnh2.datalayer.entity.PixelWrapper;

import java.util.List;

/**
 * Created by Zahar on 03.04.16.
 */
public interface ImageStoreProvider {

	List<PixelWrapper> getImages();
}
