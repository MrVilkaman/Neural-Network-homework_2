package com.github.nnh2.datalayer.eventbus;

/**
 * Created by Zahar on 03.04.16.
 */
public class TrackingEvent extends Event {
	private int count;

	public TrackingEvent(int count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	@Override
	public String toString() {
		return "TrackingEvent{" +
				"count=" + count +
				'}';
	}
}
