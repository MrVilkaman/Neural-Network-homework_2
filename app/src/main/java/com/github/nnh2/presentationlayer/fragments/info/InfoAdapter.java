package com.github.nnh2.presentationlayer.fragments.info;

import android.view.View;

import com.github.mrvilkaman.namegenerator.R;
import com.github.nnh2.datalayer.entity.ImageProcessData;
import com.github.nnh2.presentationlayer.fragments.core.MySimpleBaseAdapter;

/**
 * Created by Zahar on 03.04.16.
 */
public class InfoAdapter extends MySimpleBaseAdapter<ImageProcessData,InfoAdapterViewHolder> {

	@Override
	protected InfoAdapterViewHolder getHolder(View view) {
		return new InfoAdapterViewHolder(view);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.item_adapter_info;
	}
}
