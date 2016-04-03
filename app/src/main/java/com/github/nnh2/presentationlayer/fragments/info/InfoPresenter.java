package com.github.nnh2.presentationlayer.fragments.info;

import com.github.nnh2.datalayer.eventbus.ImageInfoEvent;
import com.github.nnh2.datalayer.eventbus.ImageProcessResponse;
import com.github.nnh2.datalayer.eventbus.QueriesBus;
import com.github.nnh2.datalayer.providers.DefaultSubscriber;
import com.github.nnh2.domainlayer.providers.SchedulersProvider;
import com.github.nnh2.presentationlayer.fragments.core.BasePresenter;

import net.jokubasdargis.rxbus.Bus;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by Zahar on 03.04.16.
 */

public class InfoPresenter extends BasePresenter<InfoView> {


	private Subscription sub;
	private Subscription sub2;
	private Bus bus;
	private SchedulersProvider scheduler;

	@Inject
	public InfoPresenter(SchedulersProvider scheduler,Bus bus) {
		this.bus = bus;
		this.scheduler = scheduler;
	}

	@Override
	protected void onViewAttached() {
		sub = bus.subscribe(QueriesBus.IMAGE_INFO,new InfoSubscriber(view()), scheduler.mainThread());
		sub2 = bus.subscribe(QueriesBus.IMAGE_HANDLE_RESPONSE,new HandleImageInfoSubscriber(view()), scheduler.mainThread());
	}

	@Override
	protected void onViewDetached() {
		sub.unsubscribe();
		sub2.unsubscribe();
	}

	public static class InfoSubscriber extends DefaultSubscriber<ImageInfoEvent>{
		private final InfoView view;

		public InfoSubscriber(InfoView view) {
			this.view = view;
		}

		@Override
		public void onNext(ImageInfoEvent imageInfoEvent) {
			view.setViewCount(imageInfoEvent);
		}
	}

	public static class HandleImageInfoSubscriber extends DefaultSubscriber<ImageProcessResponse>{
		private final InfoView view;

		public HandleImageInfoSubscriber(InfoView view) {
			this.view = view;
		}

		@Override
		public void onNext(ImageProcessResponse imageInfoEvent) {
			view.setResponse(imageInfoEvent.getDatas());
		}
	}
}
