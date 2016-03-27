package com.github.nnh2.presentationlayer.activities;

import com.github.nnh2.presentationlayer.fragments.core.BaseFragment;
import com.github.nnh2.presentationlayer.fragments.hello.HelloScreenFragment;
import com.github.nnh2.presentationlayer.utils.IToolbar;

/**
 * Created by root on 12.03.16.
 */
public class MainActivity extends BaseActivity {

	@Override
	protected void launchApi() {

	}

	@Override
	protected BaseFragment createStartFragment() {
		return HelloScreenFragment.open();
	}

	@Override
	public IToolbar getToolbar() {
		return null;
	}
}
