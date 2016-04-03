package com.github.nnh2.datalayer.eventbus;

/**
 * Created by Zahar on 03.04.16.
 */
public class ImageContentEvent {

	private int[] pixels;
	private int width;
	private int height;

	public ImageContentEvent(int[] pixels, int width, int height) {
		this.pixels = pixels;
		this.width = width;
		this.height = height;
	}

	public int[] getPixels() {
		return pixels;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean hasError() {
		return false;
	}
}
