package com.github.nnh2.datalayer.providers;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.github.nnh2.datalayer.entity.BitmapWrapper;
import com.github.nnh2.datalayer.entity.PixelWrapper;
import com.github.nnh2.domainlayer.filters.Filters;
import com.github.nnh2.domainlayer.providers.ImageStoreProvider;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Zahar on 03.04.16.
 */
public class ImageStoreProviderImpl implements ImageStoreProvider {

	private Context context;
	private Filters filters;
	private ArrayList<PixelWrapper> pixelWrappers;

	@Inject
	public ImageStoreProviderImpl(Context context, Filters filters) {
		this.context = context;
		this.filters = filters;
	}

	@Override
	public List<PixelWrapper> getImages() {

		if (pixelWrappers != null) {
			return pixelWrappers;
		}

		pixelWrappers = new ArrayList<>();

		AssetManager assets = context.getAssets();
		try {
			String[] list = assets.list("myimages");
			for (String path : list) {
				Bitmap bitmap = filters.transform(readImageFromAsset(assets.open("myimages/"+path)));

				int height = bitmap.getHeight();
				int width = bitmap.getWidth();
				int[] pixels = new int[height * width];

				bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

				String filename = path.substring(path.lastIndexOf("/")+1);

				pixelWrappers.add(new PixelWrapper(pixels,filename));
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return pixelWrappers;

	}

	private Bitmap readImageFromAsset(InputStream streem) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		options.inMutable = true;
		Bitmap bitmap = BitmapFactory.decodeStream(streem);
		return bitmap.copy(bitmap.getConfig(), true);
	}
}
