package com.github.nnh2.domainlayer.filters;

import android.graphics.Color;

/**
 * Created by Zahar on 27.03.16.
 */
public class MonochromeFilter extends PixelFilterAbs {
	private int level;

	public MonochromeFilter(int level) {
		this.level = level;
	}

	@Override
	protected void doWork(int[] pixels, int[] pixelsNew, int width, int height) {

		for (int i = 0; i < height; i++) {
			final int current = i * width;
			for (int j = 0; j < width; j++) {

				int index = current + j;
				int pixel = pixels[index];

				if (pixel == Color.TRANSPARENT) {
					continue;
				}

				int summ = Color.red(pixel);
				summ += Color.green(pixel);
				summ += Color.blue(pixel);
				summ /= 3;

				pixelsNew[index] = (summ < level) ? getBlack() : getWhite();
			}
		}
	}

	public int getWhite() {
		return Color.BLACK;
	}

	public int getBlack() {
		return Color.RED;
	}
}
