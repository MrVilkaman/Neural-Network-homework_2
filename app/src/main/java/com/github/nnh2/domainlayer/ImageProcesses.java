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
			int[] pixels = imageContentEvent.getPixels();
			int height = imageContentEvent.getHeight();
			int width = imageContentEvent.getWidth();


			List<PixelWrapper> images =  imageStoreProvider.getImages();
			List<ImageProcessData> events = new ArrayList<>();

			for (PixelWrapper image : images) {
				int checks = getChecks(image.getPixels(), pixels, height, width);
				float full = height * width;
				float total = checks / full * 100;
				events.add(new ImageProcessData(image.getName(), total));
			}


			bus.publish(QueriesBus.IMAGE_HANDLE_RESPONSE, new ImageProcessResponse(events));
		}
	}

	private int getChecks(int[] imagePixels, int[] pixels, int height, int width) {
		int checks = 0;
		for (int i = 0; i < height; i++) {
			final int current = i * width;
			for (int j = 0; j < width; j++) {
				int index = current + j;
				int pixel = pixels[index];
				int pixelImage = imagePixels[index];

				if (pixelImage == Color.BLACK && pixel == pixelImage) {
					checks++;
				}

			}
		}
		return checks;
	}
}
