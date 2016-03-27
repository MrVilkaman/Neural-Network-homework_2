package com.github.nnh2.presentationlayer.fragments.other;

import android.graphics.Bitmap;

import com.github.nnh2.presentationlayer.fragments.core.BaseView;

/**
 * Created by Zahar on 27.03.16.
 */
public interface CropImageScreenView extends BaseView{
	Bitmap getCroppedBitmap();

	String getOutPath();

	void sendResults();
}
