package com.github.nnh2.datalayer.eventbus;

import net.jokubasdargis.rxbus.Queue;

/**
 * Created by Zahar on 03.04.16.
 */
public class QueriesBus {
	public static final Queue<TrackingEvent> TRACKING  = Queue.of(TrackingEvent.class).build();
}
