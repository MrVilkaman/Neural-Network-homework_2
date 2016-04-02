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
		int[] pixelsNew = new int[height * width];

		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
		doWork(pixels,pixelsNew, width, height);

		bitmap.setPixels(pixelsNew, 0, width, 0, 0, width, height);
		return bitmap;
	}

	protected abstract void doWork(int[] pixels, int[] pixelsNew, int width, int height);
}
