package com.github.nnh2.domainlayer.filters;

import android.graphics.Color;

/**
 * Created by Zahar on 27.03.16.
 */
public class ClearBackgroundFilter extends PixelFilterAbs {

	private int redBase;
	private int greenBase;
	private int blueBase;
	private int delta;

	public ClearBackgroundFilter(int baseColor, int delta) {
		redBase = Color.red(baseColor);
		greenBase = Color.green(baseColor);
		blueBase = Color.blue(baseColor);
		this.delta = delta;
	}

	@Override
	protected void doWork(int[] pixels, int[] pixelsNew, int width, int height) {
		for (int j = 0; j < height; j++)
			for (int i = 0; i < width; i++){

				int current = i * height + j;
				int pixel = pixels[current];

				int red = Color.red(pixel);
				int green = Color.green(pixel);
				int blue = Color.blue(pixel);

				boolean b = inRound(red,redBase) && inRound(green,greenBase) && inRound(blue,blueBase);
				pixels[current] = b ? Color.WHITE: pixel;
			}
	}

	private boolean inRound(int pixel,int basePixel) {
		return Math.abs(basePixel - pixel) <= delta;
	}
}
