package com.github.nnh2.presentationlayer.fragments.info;
/**
 * Created by Zahar on 03.04.16.
 */

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.mrvilkaman.namegenerator.R;
import com.github.nnh2.datalayer.eventbus.QueriesBus;
import com.github.nnh2.presentationlayer.fragments.core.BaseFragment;
import com.github.nnh2.presentationlayer.fragments.info.InfoView;

import net.jokubasdargis.rxbus.RxBus;

import org.w3c.dom.Text;

import butterknife.Bind;

public class InfoScreenFragment extends BaseFragment<InfoPresenter> implements InfoView {

	@Bind(R.id.info_count)
	TextView countView;

	public static InfoScreenFragment open() {
		return new InfoScreenFragment();
	}

	@Override
	protected int getLayoutId() {
		return R.layout.layout_infoscreen_fragment;
	}

	@Override
	protected void onCreateView(View view, Bundle savedInstanceState) {
	}

	@Override
	protected void daggerInject() {
		DaggerInfoScreenComponent.builder()
				.appComponent(getAppComponent())
				.build()
				.inject(this);
	}

	@Override
	public void setViewCount(int count){
		countView.setText(String.valueOf(count));
	}

}