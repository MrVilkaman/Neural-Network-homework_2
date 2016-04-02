package com.github.nnh2.domainlayer.filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * Created by Zahar on 02.04.16.
 */
public class ResizeFilter implements Filters {


	private float width;
	private float height;

	public ResizeFilter(float width, float height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public Bitmap transform(Bitmap bitmap) {
		Bitmap background = Bitmap.createBitmap((int)width, (int)height, Bitmap.Config.ARGB_8888);
		float originalWidth = bitmap.getWidth(), originalHeight = bitmap.getHeight();
		Canvas canvas = new Canvas(background);
		float scale = width/originalWidth;
		float xTranslation = 0.0f, yTranslation = (height - originalHeight * scale)/2.0f;
		Matrix transformation = new Matrix();
		transformation.postTranslate(xTranslation, yTranslation);
		transformation.preScale(scale, scale);
		Paint paint = new Paint();
		paint.setFilterBitmap(true);
		canvas.drawBitmap(bitmap, transformation, paint);
		return background;
	}
}
