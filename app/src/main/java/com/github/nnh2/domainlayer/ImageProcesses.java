package com.github.nnh2.domainlayer;

import android.graphics.Color;

import com.github.nnh2.datalayer.entity.ImageProcessData;
import com.github.nnh2.datalayer.entity.PixelWrapper;
import com.github.nnh2.datalayer.eventbus.ImageContentEvent;
import com.github.nnh2.datalayer.eventbus.ImageProcessResponse;
import com.github.nnh2.datalayer.eventbus.QueriesBus;
import com.github.nnh2.datalayer.providers.DefaultSubscriber;
import com.github.nnh2.domainlayer.providers.ImageStoreProvider;
import com.github.nnh2.domainlayer.providers.SchedulersProvider;

import net.jokubasdargis.rxbus.Bus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zahar on 03.04.16.
 */
public class ImageProcesses {

	private Bus bus;
	private SchedulersProvider scheduler;
	private ImageStoreProvider imageStoreProvider;

	public ImageProcesses(Bus bus, SchedulersProvider scheduler, ImageStoreProvider imageStoreProvider) {
		this.bus = bus;
		this.scheduler = scheduler;
		this.imageStoreProvider = imageStoreProvider;
		init();
	}

	private void init() {
		bus.subscribe(QueriesBus.IMAGE_HANDLE, new ImageHandleObs(), scheduler.computation());
	}

	private class ImageHandleObs extends DefaultSubscriber<ImageContentEvent> {

		@Override
		public void onNext(ImageContentEvent imageContentEvent) {
			if (imageContentEvent.hasError()) {
				return;
			}

			int[] pixels = imageContentEvent.getPixels();
			int height = imageContentEvent.getHeight();
			int width = imageContentEvent.getWidth();


			List<PixelWrapper> images = imageStoreProvider.getImages();
			List<ImageProcessData> events = new ArrayList<>();

			float full = getChecks(pixels, pixels, height, width).black;

			for (PixelWrapper image : images) {
				Values values = getChecks(image.getPixels(), pixels, height, width);
				float total = values.black / full;
				float v = height * width ;
				float total2 = values.white2; // v * 100;

				total = normalise(total);
				total *= 100;

				float total1 = normalise(values.white / v);

				total1 *= 100;

				ImageProcessData object = new ImageProcessData(image.getName(), total, total1, total2);

				object.setNewParam(total*.75f - (1f - total1)*.25f);
//				object.setNewParam(total - total1);
//				object.setNewParam((total + 1f-total1));

				events.add(object);
			}


			bus.publish(QueriesBus.IMAGE_HANDLE_RESPONSE, new ImageProcessResponse(events));
		}

		private float normalise(float total) {
			total = Math.abs(1f - total);
			total = 1 < total ? 1f : total;

			total = 1f - total;
			return total;
		}
	}

	private Values getChecks(int[] imagePixels, int[] pixels, int height, int width) {
		Values values = new Values();
		for (int i = 0; i < height; i++) {
			final int current = i * width;
			for (int j = 0; j < width; j++) {
				int index = current + j;
				int pixel = pixels[index];
				int pixelImage = imagePixels[index];

				if (pixelImage == Color.BLACK) {
					if (pixel == pixelImage) {
						values.black++;
					}else{
						values.white++;
					}
				} else {
//				if (pixelImage == Color.WHITE) {
					if (pixel == Color.BLACK) {
						values.white2++;
					}
				}

			}
		}
		return values;
	}

	public class Values {
		public int black;
		public int white;
		public int white2;
	}
}
