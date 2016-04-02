package com.github.nnh2.domainlayer.filters;

import android.graphics.Color;

/**
 * Created by Zahar on 02.04.16.
 */
public class ScaleFilter extends MonochromeFilter {


	public ScaleFilter() {
		super(180);
	}

	@Override
	protected void doWork(int[] pixels, int[] pixelsNew, int width, int height) {
		super.doWork(pixels, pixelsNew, width, height);
		findTop(pixelsNew, width, height);
		findBottom(pixelsNew, width, height);
		findLeft(pixelsNew, width, height);
		findRight(pixelsNew, width, height);
	}


	private void findBottom(int[] pixelsNew, int width, int height) {
		for (int i = height - 1; i >= 0; i--) {
			int current = i * width;
			for (int j = 0; j < width; j++) {

				int index = current + j;
				int pixel = pixelsNew[index];

				if (pixel == getBlack()) {
					for (int jj = 0; jj < width; jj++)
						pixelsNew[current + jj] = getLineColor();
					return;
				}
			}
		}
	}

	private void findTop(int[] pixelsNew, int width, int height) {
		for (int i = 0; i < height; i++) {
			final int current = i * width;
			for (int j = 0; j < width; j++) {

				int index = current + j;
				int pixel = pixelsNew[index];

				if (pixel == getBlack()) {
					for (int jj = 0; jj < width; jj++)
						pixelsNew[current + jj] = getLineColor();
					return;
				}
			}
		}
	}

	private void findLeft(int[] pixelsNew, int width, int height) {
		for (int j = 0; j < width; j++) {
			for (int i = 0; i < height; i++) {
				final int current = i * width;
				int index = current + j;
				int pixel = pixelsNew[index];

				if (pixel == getBlack()) {
					for (int jj = 0; jj < height; jj++)
						pixelsNew[j + width * jj] = getLineColor();
					return;
				}
			}
		}
	}

	private void findRight(int[] pixelsNew, int width, int height) {
		for (int j = width - 1; j >= 0; j--) {
			for (int i = 0; i < height; i++) {
				final int current = i * width;
				int index = current + j;
				int pixel = pixelsNew[index];

				if (pixel == getBlack()) {
					for (int jj = 0; jj < height; jj++)
						pixelsNew[j + width * jj] = getLineColor();
					return;
				}
			}
		}
	}

	private int getLineColor() {
		return Color.BLUE;
	}
}
