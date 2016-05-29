package com.github.nnh2.domainlayer.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by Zahar on 02.04.16.
 */
public class ScaleFilter implements Filters {


	private final int level;
	private final float deltaPersent;
	private int delta;
	private boolean onlyShow;

	public ScaleFilter(int level, float delta, int deltaMin, boolean onlyShow) {
		this.level = level;
		this.delta = deltaMin;
		this.deltaPersent = delta;
		this.onlyShow = onlyShow;
	}

	@Override
	public Bitmap transform(Bitmap bitmap) {
		int height = bitmap.getHeight();
		int width = bitmap.getWidth();
		int[] pixels = new int[height * width];

		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
		EndPoints endPoints = doWork(pixels, width, height);

		if (onlyShow) {
			bitmap.setPixels(endPoints.pixels, 0, width, 0, 0, width, height);
			return bitmap;
		} else {
			int width2 = endPoints.right - endPoints.left;
			int height2 = endPoints.bottom - endPoints.top;
			return Bitmap.createBitmap(bitmap, endPoints.left, endPoints.top, width2, height2);
		}
	}//x + width must be <= bitmap.width()

	protected EndPoints doWork(int[] pixels, int width, int height) {
		int[] pixelsNew = pixels.clone();
		EndPoints endPoints = new EndPoints();
		endPoints.top = findTop(pixelsNew, width, height);
		endPoints.bottom = findBottom(pixelsNew, width, height);
		endPoints.left = findLeft(pixelsNew, width, height);
		endPoints.right = findRight(pixelsNew, width, height);
		endPoints.handle(width, height, delta,deltaPersent);

		drawVLine(pixelsNew, width, height, endPoints);
		endPoints.pixels = pixelsNew;
		return endPoints;
	}

	protected void drawVLine(int[] pixelsNew, int width, int height, EndPoints endPoints) {
		for (int i = 0; i < height; i++) {
			final int current = i * width;
			for (int j = 0; j < width; j++) {
				int index = current + j;
				if (endPoints.hit(j, i)) {
					pixelsNew[index] = getLineColor();
				}
			}
		}
	}

	private int findBottom(int[] pixelsNew, int width, int height) {
		for (int i = height - 1; i >= 0; i--) {
			int current = i * width;
			for (int j = 0; j < width; j++) {
				int index = current + j;
				if (check(pixelsNew, index)) {
					return i;
				}
			}
		}
		return -1;
	}

	private int findTop(int[] pixelsNew, int width, int height) {
		for (int i = 0; i < height; i++) {
			final int current = i * width;
			for (int j = 0; j < width; j++) {
				int index = current + j;
				if (check(pixelsNew, index)) {
					return i;
				}
			}
		}
		return -1;

	}

	private int findLeft(int[] pixelsNew, int width, int height) {
		for (int j = 0; j < width; j++) {
			for (int i = 0; i < height; i++) {
				final int current = i * width;
				int index = current + j;
				if (check(pixelsNew, index)) {
					return j;
				}
			}
		}
		return -1;
	}

	private int findRight(int[] pixelsNew, int width, int height) {
		for (int j = width - 1; j >= 0; j--) {
			for (int i = 0; i < height; i++) {
				final int current = i * width;
				int index = current + j;
				if (check(pixelsNew, index)) {
					return j;
				}
			}
		}
		return -1;
	}


	private boolean check(int[] pixelsNew, int index) {
		int pixel = pixelsNew[index];
		if (pixel == Color.TRANSPARENT) {
			return false;
		}

		int summ = Color.red(pixel);
		summ += Color.green(pixel);
		summ += Color.blue(pixel);
		summ /= 3;

		return summ < level;
	}


	private int getLineColor() {
		return Color.BLUE;
	}

	public class EndPoints {
		int top;
		int bottom;
		int left;
		int right;
		public int[] pixels;

		public boolean hit(int j, int i) {
			return left == j || right == j || top == i || bottom == i;
		}
	
		public void handle(int width, int height, int delta, float deltaPersent) {
			float del = Math.max(deltaPersent * height, delta);
			float v0 = top - del;
			top = 0 < v0 ? (int) (top - del) : 0;
			float v1 = bottom + del;
			bottom = (v1 < height) ? (int) (bottom + del) : height;

			del = Math.max(deltaPersent * width, delta);
			float v = left - del;
			left = 0 < v ? (int) (left - del) : 0;
			float v2 = right + del;
			right = (v2 < width) ? (int) (right + del) : width;
		}


	}
}
