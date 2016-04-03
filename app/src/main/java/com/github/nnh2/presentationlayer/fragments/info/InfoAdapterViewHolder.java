package com.github.nnh2.presentationlayer.fragments.info;

import android.view.View;
import android.widget.TextView;

import com.github.mrvilkaman.namegenerator.R;
import com.github.nnh2.datalayer.entity.ImageProcessData;
import com.github.nnh2.presentationlayer.fragments.core.AdapterViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Zahar on 01.04.16.
 */
public class InfoAdapterViewHolder extends AdapterViewHolder<ImageProcessData> {

	@Bind(R.id.info_count_name) TextView title;
	@Bind(R.id.info_count) TextView total;

	public InfoAdapterViewHolder(View itemView) {
		super(itemView);
		ButterKnife.bind(this, itemView);
	}

	@Override
	public void bind(ImageProcessData item) {
		title.setText(item.getTitle());
		total.setText(String.format(" %.1f%%", item.getTotal()));
	}
}
