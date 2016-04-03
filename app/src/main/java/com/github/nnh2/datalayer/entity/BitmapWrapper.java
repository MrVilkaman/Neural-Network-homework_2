package com.github.nnh2.datalayer.entity;

import android.graphics.Bitmap;

/**
 * Created by Zahar on 03.04.16.
 */
public class BitmapWrapper {
	private Bitmap bitmap;
	private String name;

	public BitmapWrapper(Bitmap bitmap, String name) {
		this.bitmap = bitmap;
		this.name = name;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public String getName() {
		return name;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
}
