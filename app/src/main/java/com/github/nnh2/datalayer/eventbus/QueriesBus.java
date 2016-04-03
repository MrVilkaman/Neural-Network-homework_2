package com.github.nnh2.datalayer.eventbus;

import com.github.nnh2.datalayer.entity.ImageProcessData;

import net.jokubasdargis.rxbus.Queue;

/**
 * Created by Zahar on 03.04.16.
 */
public class QueriesBus {
	public static final Queue<ImageInfoEvent> IMAGE_INFO = Queue.of(ImageInfoEvent.class).build();
	public static final Queue<String> SHOW_IMAGE = Queue.of(String.class).build();
	public static final Queue<ImageContentEvent> IMAGE_HANDLE = Queue.of(ImageContentEvent.class).build();
	public static final Queue<ImageProcessResponse> IMAGE_HANDLE_RESPONSE = Queue.of(ImageProcessResponse.class).build();
}
