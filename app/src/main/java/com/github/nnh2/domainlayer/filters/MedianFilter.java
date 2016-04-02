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
	public void doWork(int[] pixels, int[] pixelsNew, int width, int height) {
		int half = windowSize / 2;

		for (int i = half; i < width - half; i++)
			for (int j = half; j < height - half; j++) {

				int length = windowSize * windowSize;
				int[] red = new int[length];
				int[] green = new int[length];
				int[] blue = new int[length];

				int alpha = 0;
				int count = 0;
				for (int wi = -half; wi <= half; wi++)
					for (int wj = -half; wj <= half; wj++) {
						int currentPixel = (j + wj) * width + i + wi;
						alpha = Color.alpha(currentPixel);

						int pixel = pixels[currentPixel];
						red[count] = Color.red(pixel);
						green[count] = Color.green(pixel);
						blue[count] = Color.blue(pixel);
						count++;
					}

				Arrays.sort(red);
				Arrays.sort(green);
				Arrays.sort(blue);

				int medium = length-1;//(length + 1) / 2;
				int index = j * width + i;
				pixelsNew[index] = Color.argb(alpha,red[medium], green[medium], blue[medium]);
			}
	}
}
