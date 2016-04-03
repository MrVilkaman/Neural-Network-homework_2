package com.github.nnh2.datalayer.eventbus;

/**
 * Created by Zahar on 03.04.16.
 */
public class ImageInfoEvent extends Event {
	private int count;
	private int width;
	private int height;

	public ImageInfoEvent(int count, int width, int height) {
		this.count = count;
		this.width = width;
		this.height = height;
	}

	public int getCount() {
		return count;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	@Override
	public String toString() {
		return "ImageInfoEvent{" +
				"count=" + count +
				", width=" + width +
				", height=" + height +
				'}';
	}

	public int getSize() {
		return width*height;
	}
}
