package com.github.nnh2.presentationlayer.fragments.info;

import com.github.nnh2.datalayer.eventbus.ImageInfoEvent;
import com.github.nnh2.datalayer.eventbus.QueriesBus;
import com.github.nnh2.datalayer.providers.DefaultSubscriber;
import com.github.nnh2.domainlayer.providers.SchedulersProvider;
import com.github.nnh2.presentationlayer.fragments.core.BasePresenter;

import net.jokubasdargis.rxbus.Bus;

import javax.inject.Inject;

import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Zahar on 03.04.16.
 */

public class InfoPresenter extends BasePresenter<InfoView> {


	private Subscription sub;
	private Bus bus;
	private SchedulersProvider scheduler;

	@Inject
	public InfoPresenter(SchedulersProvider scheduler,Bus bus) {
		this.bus = bus;
		this.scheduler = scheduler;
	}

	@Override
	protected void onViewAttached() {
		sub = bus.subscribe(QueriesBus.TRACKING,new InfoSubscriber(view()), scheduler.mainThread());
	}

	@Override
	protected void onViewDetached() {
		sub.unsubscribe();
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
}
