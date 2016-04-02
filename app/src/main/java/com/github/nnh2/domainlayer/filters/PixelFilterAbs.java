package com.github.nnh2.domainlayer.filters;

import android.graphics.Bitmap;

/**
 * Created by Zahar on 27.03.16.
 */
public abstract class PixelFilterAbs implements Filters {

	@Override
	public Bitmap transform(Bitmap bitmap) {
		int height = bitmap.getHeight();
		int width = bitmap.getWidth();
		int[] pixels = new int[height * width];

		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
		pixels = doWork(pixels, width, height);

		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

	protected abstract int[] doWork(int[] pixels, int width, int height);
}
