package com.github.nnh2.datalayer.eventbus;

/**
 * Created by Zahar on 03.04.16.
 */
public class ImageProcessResponse {
	private final float total;
	private final String title;

	public ImageProcessResponse(String title, float total) {
		this.title = title;
		this.total = total;
	}

	public float getTotal() {
		return total;
	}

	public String getTitle() {
		return title;
	}
}
