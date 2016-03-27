package com.github.nnh2.domainlayer.filters;

import android.graphics.Color;

/**
 * Created by Zahar on 27.03.16.
 */
public class RedFilter extends PixelFilterAbs {


	@Override
	protected void doWork(int[] pixels, int width, int height) {
		for (int i = 0; i < width*5; i++) {
			pixels[i] = Color.RED;
		}
	}
}
