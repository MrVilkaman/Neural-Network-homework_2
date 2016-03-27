package com.github.nnh2.presentationlayer.fragments.hello;
/**
 * Created by root on 12.03.16.
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.mrvilkaman.namegenerator.R;
import com.github.nnh2.presentationlayer.fragments.core.BaseFragment;
import com.github.nnh2.presentationlayer.utils.PhotoUtils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

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
				.onPositive((dialog, which) -> PhotoUtils.openCamera(HelloScreenFragment.this,PhotoUtils.AVATAR_FILE_NAME))
				.onNegative((dialog, which) -> PhotoUtils.openGallery(HelloScreenFragment.this,PhotoUtils.AVATAR_FILE_NAME))
				.show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == PhotoUtils.CROP_PHOTO_REQUEST_CODE) {

		String path = PhotoUtils.getPathToTempFiles(getActivity()) + PhotoUtils.AVATAR_FILE_NAME;
		Uri uri = Uri.fromFile(new File(path));
		PhotoUtils.normalizeImageForUri(getActivity(), uri);
		Picasso.with(getActivity())
				.load(uri)
				.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
//				.placeholder(R.drawable.md_btn_shape)
//				.transform(new CropSquareTransformation())
				.into(image);
		}

		PhotoUtils.onActivityResult(this,requestCode,data);
		super.onActivityResult(requestCode, resultCode, data);
	}
}