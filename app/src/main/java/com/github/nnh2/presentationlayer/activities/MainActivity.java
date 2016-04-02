package com.github.nnh2.presentationlayer.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.github.mrvilkaman.namegenerator.R;
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		launchRightPanel();
	}

	private void launchRightPanel() {
		int content_right = R.id.content_right;
		FragmentManager supportFragmentManager = getSupportFragmentManager();
		Fragment contentFragment = supportFragmentManager.findFragmentById(getContainerID());
		if (contentFragment == null) {

			FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
			fragmentTransaction.add(content_right, getRigthFragment());
			fragmentTransaction.commit();
		}
	}

	private Fragment getRigthFragment() {
		return HelloScreenFragment.open();
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
