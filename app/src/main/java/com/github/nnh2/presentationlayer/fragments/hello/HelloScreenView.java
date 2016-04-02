package com.github.nnh2.presentationlayer.fragments.hello;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.github.nnh2.presentationlayer.fragments.core.BaseView;

import java.io.InputStream;

import rx.Observable;

/**
 * Created by root on 12.03.16.
 */

public interface HelloScreenView extends BaseView {

	@NonNull
	String getLastPath();

	void setImage(Bitmap bm);

	InputStream getAssetStreem();
}