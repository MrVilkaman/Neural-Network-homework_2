package com.github.nnh2.datalayer.entity;

/**
 * Created by Zahar on 03.04.16.
 */
public class ImageProcessData {

	private final float total;
	private final float total1;
	private final String title;

	public ImageProcessData(String title, float total, float total1) {
		this.title = title;
		this.total = total;
		this.total1 = total1;
	}

	public float getTotal() {
		return total;
	}

	public String getTitle() {
		return title;
	}

	public float getTotal1() {
		return total1;
	}
}
