package com.github.nnh2.presentationlayer.fragments.hello;
/**
 * Created by root on 12.03.16.
 */

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.mrvilkaman.namegenerator.R;
import com.github.nnh2.presentationlayer.fragments.core.BaseFragment;
import com.github.nnh2.presentationlayer.utils.PhotoUtils;

import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.OnClick;

public class HelloScreenFragment extends BaseFragment<HelloScreenPresenter> implements HelloScreenView {


	@Bind(R.id.image)
	ImageView image;

	public static HelloScreenFragment open() {
		return new HelloScreenFragment();
	}

	@Override
	protected void daggerInject() {
		DaggerHelloComponent.builder()
				.appComponent(getAppComponent())
				.build()
				.inject(this);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.layout_helloscreen_fragment;
	}

	@Override
	protected void onCreateView(View view, Bundle savedInstanceState) {
	}

	@OnClick(R.id.open_photo_dialog)
	void onClick() {
		openPhotoDialog();
	}

	private void openPhotoDialog() {

		MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());
		builder.positiveText(R.string.dialog_take_photo)
				.negativeText(R.string.dialog_get_photo)
				.onPositive((dialog, which) -> PhotoUtils.openCamera(HelloScreenFragment.this, PhotoUtils.AVATAR_FILE_NAME))
				.onNegative((dialog, which) -> PhotoUtils.openGallery(HelloScreenFragment.this, PhotoUtils.AVATAR_FILE_NAME))
				.show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == PhotoUtils.CROP_PHOTO_REQUEST_CODE) {
			getPresenter().newPhotoTaken();
		}

		PhotoUtils.onActivityResult(this, requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public InputStream getAssetStreem() {
		AssetManager assets = getActivity().getAssets();
		try {
			String[] list = assets.list("myimages");
			return assets.open("myimages/" + list[(int) (Math.random() * (list.length - 1))]);
//			return assets.open(list[1]);
		} catch (IOException e) {
			return null;
		}
	}

	@NonNull
	@Override
	public String getLastPath() {
			return PhotoUtils.getPathToTempFiles(getActivity()) + PhotoUtils.AVATAR_FILE_NAME;
//		return null;
	}

	@Override
	public void setImage(Bitmap bm) {
		image.setImageBitmap(bm);
	}

}