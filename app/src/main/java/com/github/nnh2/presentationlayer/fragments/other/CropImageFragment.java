package com.github.nnh2.presentationlayer.fragments.other;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.github.mrvilkaman.namegenerator.R;
import com.github.nnh2.presentationlayer.fragments.core.BaseFragment;
import com.github.nnh2.presentationlayer.utils.IToolbar;
import com.github.nnh2.presentationlayer.utils.PhotoUtils;
import com.isseiaoki.simplecropview.CropImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;


public class CropImageFragment extends BaseFragment<CropImagePresenter>  implements CropImageScreenView {

	public enum MODE{
		FREE,
		SQUARE,
		PREVIEW
	}


	private static final String EXTRA_INPUT = "input";
	private static final String EXTRA_OUTPUT = "output";
	private static final String EXTRA_AS_SQUARE = "as_square";

	@Bind(R.id.cropImageView)
	CropImageView cropImageView;

	public static CropImageFragment newInstance(Fragment target, String imageFile, String resultFile, MODE mode) {
		Bundle args = new Bundle();
		args.putString(EXTRA_INPUT,imageFile);
		args.putString(EXTRA_OUTPUT,resultFile);
		args.putString(EXTRA_AS_SQUARE, mode.name());
		CropImageFragment fragment = new CropImageFragment();
		fragment.setArguments(args);
		fragment.setTargetFragment(target,PhotoUtils.CROP_PHOTO_REQUEST_CODE);
		return fragment;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_crop_fragment;
	}

	@Override
	protected void onCreateView(View view, Bundle savedInstanceState) {

//		cropImageView.setMinFrameSizeInPx(Constants.MIN_IMAGE_SIZE_PX);
//		cropImageView.fra

		File uri = new File(getArguments().getString(EXTRA_INPUT));

//		if (Constants.MAX_IMAGE_LENGTH < uri.length()) {
//			toast(R.string.big_image_size);
//			needBack = true;
//			return;
//		}

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(uri.getPath(), options);
		int imageHeight = options.outHeight;
		int imageWidth = options.outWidth;

		MODE mode = MODE.valueOf(getArguments().getString(EXTRA_AS_SQUARE));


//		if (!mode.equals(MODE.PREVIEW) && (imageHeight < Constants.MIN_IMAGE_SIZE_PX || imageWidth < Constants.MIN_IMAGE_SIZE_PX)) {
//			toast(R.string.little_image_size);
//			needBack = true;
//			return;
//		}


		switch (mode) {
			case FREE:
				cropImageView.setCropMode(CropImageView.CropMode.RATIO_FREE);
				break;
			case SQUARE:
				cropImageView.setCropMode(CropImageView.CropMode.RATIO_1_1);
				break;
//			case PREVIEW:
//				cropImageView.setCropMode(CropImageView.CropMode.RATIO_CUSTOM);
//				cropImageView.setCustomRatio((int) Constants.PREVIEW_RATIO_X, (int) Constants.PREVIEW_RATIO_Y);
//				cropImageView.setInitialFrameScale(1f);
//				DisplayMetrics displayMetrics = new DisplayMetrics();
//				getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//				cropImageView.setMinFrameSizeInPx(displayMetrics.widthPixels);
//				break;
			default:
		}
		loadImage(uri);
	}

	private void loadImage(File uri) {
		showProgress();
		Picasso.with(getContext())
				.load(uri)
//				.transform(new Resize())
				.memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
				.into(cropImageView, new Callback() {
					@Override
					public void onSuccess() {
						hideProgress();
					}

					@Override
					public void onError() {
						hideProgress();
						back();
					}
				});
	}

	@Override
	protected void daggerInject() {
		setPresenter(new CropImagePresenter());
	}

	@Override
	public boolean onBackPressed() {
		Fragment targetFragment = getTargetFragment();
		int targetRequestCode = getTargetRequestCode();
		if (targetFragment != null) {
			setTargetFragment(null,0);
			targetFragment.onActivityResult(targetRequestCode, Activity.RESULT_CANCELED, null);
		}

		return super.onBackPressed();
	}

	@Override
	public void onResume() {
		super.onResume();
//		if (needBack) {
//			back();
//		}
	}

	@Override
	public Bitmap getCroppedBitmap() {
		return cropImageView.getCroppedBitmap();
	}

	@Override
	public String getOutPath() {
		return getArguments().getString(EXTRA_OUTPUT);
	}

	@Override
	public void sendResults() {
		Fragment targetFragment = getTargetFragment();
		int targetRequestCode = getTargetRequestCode();
		if (targetFragment != null) {
			setTargetFragment(null,0);
			back();
			targetFragment.onActivityResult(targetRequestCode, Activity.RESULT_OK, null);
		}
	}

	@OnClick(R.id.ready)
	void onClick(){
		getPresenter().savePhoto();
	}

}
