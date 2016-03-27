package com.github.nnh2.domainlayer.filters;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Zahar on 27.03.16.
 */
public class CombiFilter implements Filters {

	private ArrayList<Filters> list = new ArrayList<>();

	public CombiFilter(Filters... filters) {
		list.addAll(Arrays.asList(filters));
	}

	@Override
	public Bitmap transform(Bitmap bitmap) {

		for (Filters filters : list) {
			bitmap = filters.transform(bitmap);
		}

		return bitmap;
	}
}
