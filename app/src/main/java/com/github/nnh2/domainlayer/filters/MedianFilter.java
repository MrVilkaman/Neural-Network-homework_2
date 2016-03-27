package com.github.nnh2.domainlayer.filters;

import android.graphics.Color;

import java.util.Arrays;

/**
 * Created by Zahar on 27.03.16.
 */
public class MedianFilter extends PixelFilterAbs {


	private final int windowSize;

	public MedianFilter(int windowSize) {
		this.windowSize = windowSize;
	}

	@Override
	protected void doWork(int[] pixels, int width, int height) {
		for (int i = 0; i < width - windowSize; i++)
			for (int j = 0; j < height - windowSize; j++) {

				int length = windowSize * windowSize;
				int[] red = new int[length];
				int[] green = new int[length];
				int[] blue = new int[length];

				for (int wi = 0; wi < windowSize; wi++)
					for (int wj = 0; wj < windowSize; wj++) {
						int currentPixel = (i + wi) * height + j + wj;

						int pixel = pixels[currentPixel];
						red[wi * windowSize + wj] = Color.red(pixel);
						green[wi * windowSize + wj] = Color.green(pixel);
						blue[wi * windowSize + wj] = Color.blue(pixel);
					}

				Arrays.sort(red);
				Arrays.sort(green);
				Arrays.sort(blue);

				int medium = (this.windowSize + 1) / 2;
				pixels[i * height + i] = Color.rgb(red[medium], green[medium], blue[medium]);
			}
	}
}
