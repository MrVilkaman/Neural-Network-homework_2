package com.github.nnh2.presentationlayer.fragments.core;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Zahar on 03.04.16.
 */
public abstract class AdapterViewHolder<I> extends RecyclerView.ViewHolder{
	public AdapterViewHolder(View itemView) {
		super(itemView);
	}

	public abstract void bind(I item);
}
