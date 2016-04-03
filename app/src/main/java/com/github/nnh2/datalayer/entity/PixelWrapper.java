package com.github.nnh2.datalayer.entity;

/**
 * Created by Zahar on 03.04.16.
 */
public class PixelWrapper {

	private final int width;
	private final int height;
	private int[] pixels;
	private String name;

	public PixelWrapper(int[] pixels, String name, int width, int height) {
		this.pixels = pixels;
		this.name = name;
		this.width = width;
		this.height = height;
	}

	public int[] getPixels() {
		return pixels;
	}

	public String getName() {
		return name;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
