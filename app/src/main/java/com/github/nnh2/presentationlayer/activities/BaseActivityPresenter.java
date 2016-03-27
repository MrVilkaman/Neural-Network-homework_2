package com.github.nnh2.presentationlayer.activities;

import com.github.nnh2.presentationlayer.fragments.core.BaseFragment;

public interface BaseActivityPresenter {

	void loadRootFragment(BaseFragment fragment, boolean addToBackStack, boolean isRoot, boolean forceLoad, boolean openIfcreated);
}
