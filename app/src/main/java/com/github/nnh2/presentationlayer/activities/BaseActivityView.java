package com.github.nnh2.presentationlayer.activities;

import com.github.nnh2.presentationlayer.utils.IToolbar;

public interface BaseActivityView {

	void showProgress();

	void hideProgress();

	void clearProgress();

	void back();

	void hideKeyboard();

	IToolbar getToolbar();
}
