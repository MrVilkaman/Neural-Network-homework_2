package com.github.nnh2.presentationlayer.fragments.info;

import com.github.nnh2.datalayer.eventbus.QueriesBus;
import com.github.nnh2.datalayer.eventbus.TrackingEvent;
import com.github.nnh2.datalayer.providers.DefaultSubscriber;
import com.github.nnh2.presentationlayer.fragments.core.BasePresenter;

import net.jokubasdargis.rxbus.Bus;
import net.jokubasdargis.rxbus.RxBus;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by Zahar on 03.04.16.
 */

public class InfoPresenter extends BasePresenter<InfoView> {


	private Subscription sub;
	private Bus bus;

	@Inject
	public InfoPresenter(Bus bus) {
		this.bus = bus;
	}

	@Override
	protected void onViewAttached() {
		sub = bus.subscribe(QueriesBus.TRACKING,new InfoSubscriber(view()));
	}

	@Override
	protected void onViewDetached() {
		sub.unsubscribe();
	}

	public static class InfoSubscriber extends DefaultSubscriber<TrackingEvent>{
		private final InfoView view;

		public InfoSubscriber(InfoView view) {
			this.view = view;
		}

		@Override
		public void onNext(TrackingEvent trackingEvent) {
			view.setViewCount(trackingEvent.getCount());
		}
	}
}
