package com.github.nnh2.presentationlayer.fragments.other;

import android.graphics.Bitmap;

import com.github.nnh2.presentationlayer.fragments.core.BasePresenter;
import com.github.nnh2.presentationlayer.utils.PhotoUtils;

import java.io.File;

/**
 * Created by Zahar on 27.03.16.
 */
public class CropImagePresenter extends BasePresenter<CropImageScreenView>{



	public void savePhoto(){
		Bitmap croppedBitmap = view().getCroppedBitmap();
		if (croppedBitmap == null) {
			return;
		}
		File file = new File(view().getOutPath());
		PhotoUtils.saveToFile(croppedBitmap, file, Bitmap.CompressFormat.JPEG);

		view().sendResults();
	}


}
