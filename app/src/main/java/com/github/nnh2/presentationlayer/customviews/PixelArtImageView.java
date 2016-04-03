package com.github.nnh2.presentationlayer.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Zahar on 02.04.16.
 */
public class PixelArtImageView extends ImageView {
	private Paint paintRed;
	private float[] floats;

	public PixelArtImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PixelArtImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		floats = new float[9];

		paintRed = new Paint();
		paintRed.setColor(Color.GREEN);
		paintRed.setAntiAlias(true);
		paintRed.setStrokeWidth(1);
		paintRed.setStyle(Paint.Style.STROKE);
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		super.setImageBitmap(bm);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (getImageMatrix() != null) {
			getImageMatrix().getValues(floats);
		}


		Drawable drawable = getDrawable();
		if (drawable == null) {
			return;
		}
		int dwidth = (int) (drawable.getIntrinsicWidth() * floats[0]);
		int dheight = (int) (drawable.getIntrinsicHeight() * floats[0]);


		float startX = floats[2] + getPaddingLeft();
		float startY = floats[5] + getPaddingTop();
		float stopX = floats[2] + dwidth + getPaddingRight();
		float stopY = floats[5] + dheight + getPaddingBottom();


		canvas.drawLine(startX, startY, stopX, startY, paintRed);
		canvas.drawLine(startX, stopY, stopX, stopY, paintRed);

		canvas.drawLine(startX, startY, startX, stopY, paintRed);
		canvas.drawLine(stopX, startY, stopX, stopY, paintRed);


	}
}
