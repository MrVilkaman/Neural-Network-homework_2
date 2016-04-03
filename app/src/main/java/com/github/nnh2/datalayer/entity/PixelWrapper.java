package com.github.nnh2.datalayer.entity;

/**
 * Created by Zahar on 03.04.16.
 */
public class PixelWrapper {

	private int[] pixels;
	private String name;

	public PixelWrapper(int[] pixels, String name) {
		this.pixels = pixels;
		this.name = name;
	}

	public int[] getPixels() {
		return pixels;
	}

	public String getName() {
		return name;
	}
}
