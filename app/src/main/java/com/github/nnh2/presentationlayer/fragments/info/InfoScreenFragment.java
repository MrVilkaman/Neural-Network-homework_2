package com.github.nnh2.presentationlayer.fragments.info;
/**
 * Created by Zahar on 03.04.16.
 */

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.mrvilkaman.namegenerator.R;
import com.github.nnh2.datalayer.entity.ImageProcessData;
import com.github.nnh2.datalayer.eventbus.ImageInfoEvent;
import com.github.nnh2.presentationlayer.fragments.core.BaseFragment;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;

public class InfoScreenFragment extends BaseFragment<InfoPresenter> implements InfoView {

	//	@Bind(R.id.info_count)
	TextView countView;
	@Bind(R.id.list)
	RecyclerView recyclerView;
	private InfoAdapter adapter;

	public static InfoScreenFragment open() {
		return new InfoScreenFragment();
	}

	@Override
	protected int getLayoutId() {
		return R.layout.layout_infoscreen_fragment;
	}

	@Override
	protected void onCreateView(View view, Bundle savedInstanceState) {
		setupRecyclerView();
	}

	@Override
	protected void daggerInject() {
		DaggerInfoScreenComponent.builder()
				.appComponent(getAppComponent())
				.build()
				.inject(this);
	}

	@Override
	public void setViewCount(ImageInfoEvent event) {
//		countView.setText(String.format("%s/%d", event.getCount(), event.getSize()));
	}

	@Override
	public void setResponse(List<ImageProcessData> imageInfoEvent) {
		Collections.sort(imageInfoEvent, (lhs, rhs) -> lhs.getNewParam() < rhs.getNewParam() ? 1 : -1);
		adapter.setItems(imageInfoEvent);
	}

	private void setupRecyclerView() {
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.setHasFixedSize(true);
		if (adapter == null) {
			adapter = new InfoAdapter();
		}
		adapter.setOnClick(this::showImage);
		recyclerView.setAdapter(adapter);
	}

	private void showImage(ImageProcessData imageProcessData) {
		getPresenter().showImage(imageProcessData);
	}
}