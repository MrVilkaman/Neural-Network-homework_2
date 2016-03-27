package com.github.nnh2.domainlayer.filters;

import android.graphics.Color;

/**
 * Created by Zahar on 27.03.16.
 */
public class MonochromeFilter extends PixelFilterAbs{
	private int level;

	public MonochromeFilter(int level) {
		this.level = level;
	}

	@Override
	protected void doWork(int[] pixels, int width, int height) {
		for (int j = 0; j < height; j++)
			for (int i = 0; i < width; i++){

				int current = i * height + j;
				int pixel = pixels[current];
				int summ = Color.red(pixel);
				summ += Color.green(pixel);
				summ += Color.blue(pixel);
				summ /= 3;

				pixels[current] = summ < level ? Color.BLACK: Color.WHITE;
			}
	}
}
