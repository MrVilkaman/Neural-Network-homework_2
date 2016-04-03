package com.github.nnh2.datalayer.entity;

/**
 * Created by Zahar on 03.04.16.
 */
public class ImageProcessData {

	private final float total;
	private final float total1;
	private final String title;
	private final float total2;
	private float newParam;

	public ImageProcessData(String title, float total, float total1, float total2) {
		this.title = title;
		this.total = total;
		this.total1 = total1;
		this.total2 = total2;
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

	public float getTotal2() {
		return total2;
	}

	public float getNewParam() {
		return newParam;
	}

	public void setNewParam(float newParam) {
		this.newParam = newParam;
	}
}
