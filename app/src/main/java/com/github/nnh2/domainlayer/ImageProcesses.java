package com.github.nnh2.domainlayer;

import com.github.nnh2.datalayer.eventbus.ImageContentEvent;
import com.github.nnh2.datalayer.eventbus.ImageProcessResponse;
import com.github.nnh2.datalayer.eventbus.QueriesBus;
import com.github.nnh2.datalayer.providers.DefaultSubscriber;
import com.github.nnh2.domainlayer.providers.SchedulersProvider;

import net.jokubasdargis.rxbus.Bus;

import rx.Observable;

import static rx.Observable.just;

/**
 * Created by Zahar on 03.04.16.
 */
public class ImageProcesses {

	private Bus bus;
	private SchedulersProvider scheduler;

	public ImageProcesses(Bus bus, SchedulersProvider scheduler) {
		this.bus = bus;
		this.scheduler = scheduler;
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

			int checks = 0;

			for (int i = 0; i < height; i++) {
				final int current = i * width;
				for (int j = 0; j < width; j++) {
					int index = current + j;
					int pixel = pixels[index];

					checks++;

				}
			}

			float full = height*width;

			float total = checks/full*100;

			bus.publish(QueriesBus.IMAGE_HANDLE_RESPONSE,new ImageProcessResponse(imageContentEvent.getName(),total));
		}
	}
}
