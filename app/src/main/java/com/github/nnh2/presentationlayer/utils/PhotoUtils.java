package com.github.nnh2.presentationlayer.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.github.nnh2.presentationlayer.fragments.core.BaseFragment;
import com.github.nnh2.presentationlayer.fragments.other.CropImageFragment;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;


public class PhotoUtils {

    public static final int SELECT_PICTURE_REQUEST_CODE = 9000;
    public static final int TAKE_PHOTO_REQUEST_CODE = 9001;
    public static final int CROP_PHOTO_REQUEST_CODE = 9002;
    public static final int CROP_ERROR = 404;


    public static final String IMAGE_TEMP_FILE_NAME = "cameraTemp.jpg";
    public static final String IMAGE_TEMP = "photos";

	private static final String TAG = "PhotoUtils";
	public static final String AVATAR_FILE_NAME = "temp";

	private static String lastFileName = "";

    private static CropImageFragment.MODE mode = CropImageFragment.MODE.FREE ;

    private PhotoUtils() {
    }

    public static String getLastFileName() {
        return lastFileName;
    }

    public static void openGallery(Fragment fragment, String fileName) {
        System.gc();
        Intent intent = new Intent(Intent.ACTION_PICK);
        lastFileName = fileName;
        intent.setType("image/*");
        fragment.startActivityForResult(intent, SELECT_PICTURE_REQUEST_CODE);
    }

    public static void openCamera(Fragment frag, String fileName) {
        System.gc();
        File dir = new File(getPathToTempFiles(frag.getActivity()));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        lastFileName = fileName;
        String imageFilePath = dir + File.separator + IMAGE_TEMP_FILE_NAME;
        File originalFile = new File(imageFilePath);
        Uri imageFileUri = Uri.fromFile(originalFile);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
        frag.startActivityForResult(cameraIntent, TAKE_PHOTO_REQUEST_CODE);
    }

    public static String getPathToTempFiles(Context context) {
        return StorageUtils.getStoragePath(context) + File.separator + IMAGE_TEMP + File.separator;
    }

    public static void onActivityResult(BaseFragment fragment, int requestCode, Intent data) {
		Context context = fragment.getActivity();
		if (requestCode == SELECT_PICTURE_REQUEST_CODE) {
            final Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = fragment.getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    showCrop(fragment, filePath, PhotoUtils.getPathToTempFiles(context) + lastFileName, mode);
                }
                cursor.close();
            }

        } else if (requestCode == PhotoUtils.TAKE_PHOTO_REQUEST_CODE) {
			String path = PhotoUtils.getPathToTempFiles(context) + PhotoUtils.IMAGE_TEMP_FILE_NAME;
            showCrop(fragment, path, PhotoUtils.getPathToTempFiles(context) + lastFileName, mode);
        } else if (requestCode == PhotoUtils.CROP_PHOTO_REQUEST_CODE) {
            clear(context, IMAGE_TEMP_FILE_NAME);
        }
    }


    private static void showCrop(final BaseFragment fragment, final String path, String pathTo, CropImageFragment.MODE mode) {
        File from = new File(path);
        File to = new File(pathTo);
        try {
            PhotoUtils.copy(from, to);
        } catch (IOException e) {
            Log.d(TAG,"copy file error", e);
        }

        fragment.showFragment(CropImageFragment.newInstance(fragment, from.getAbsolutePath(), to.getAbsolutePath(), mode));
    }

    public static void clear(Context context, String avatarFileName) {
        File file = new File(getPathToTempFiles(context), avatarFileName);
        file.delete();
    }

    public static void setMode(final CropImageFragment.MODE mode) {
        PhotoUtils.mode = mode;
    }

    public static void saveToFile(Bitmap bmp, File filename, Bitmap.CompressFormat format) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filename);
            bmp.compress(format, 90, out); // bmp is your Bitmap instance
        } catch (Exception e) {
            Log.d(TAG,"saveToFile error", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
				Log.d(TAG,"saveToFile error", e);
            }
        }
    }

    public static void createPreviewCrop(BaseFragment fragment, final String inputPath, final String outputPath) {
        showCrop(fragment,inputPath,outputPath, CropImageFragment.MODE.PREVIEW);
    }

	public static void normalizeImageForUri(Context context, Uri uri) {
		try {
			ExifInterface exif = new ExifInterface(uri.getPath());
			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
			Bitmap rotatedBitmap = rotateBitmap(bitmap, orientation);
			if (!bitmap.equals(rotatedBitmap)) {
				saveBitmapToFile(context, rotatedBitmap, uri);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
		Matrix matrix = new Matrix();
		switch (orientation) {
			case ExifInterface.ORIENTATION_NORMAL:
				return bitmap;
			case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
				matrix.setScale(-1, 1);
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				matrix.setRotate(180);
				break;
			case ExifInterface.ORIENTATION_FLIP_VERTICAL:
				matrix.setRotate(180);
				matrix.postScale(-1, 1);
				break;
			case ExifInterface.ORIENTATION_TRANSPOSE:
				matrix.setRotate(90);
				matrix.postScale(-1, 1);
				break;
			case ExifInterface.ORIENTATION_ROTATE_90:
				matrix.setRotate(90);
				break;
			case ExifInterface.ORIENTATION_TRANSVERSE:
				matrix.setRotate(-90);
				matrix.postScale(-1, 1);
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				matrix.setRotate(-90);
				break;
			default:
				return bitmap;
		}
		try {
			Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
			bitmap.recycle();

			return bmRotated;
		}
		catch (OutOfMemoryError e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void saveBitmapToFile(Context context, Bitmap croppedImage, Uri saveUri) {
		if (saveUri != null) {
			OutputStream outputStream = null;
			try {
				outputStream = context.getContentResolver().openOutputStream(saveUri);
				if (outputStream != null) {
					croppedImage.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
				}
			} catch (IOException e) {
				Log.e(TAG,"Cannot open file: " + saveUri.toString()+" | "+ e.getMessage());
			} finally {
				closeSilently(outputStream);
				croppedImage.recycle();
			}
		}
	}

	private static void closeSilently(@Nullable Closeable c) {
		if (c == null) return;
		try {
			c.close();
		} catch (Throwable t) {
			// Do nothing
		}
	}

	public static void copy(File src, File dst) throws IOException {
		FileInputStream inStream = new FileInputStream(src);
		FileOutputStream outStream = new FileOutputStream(dst);
		FileChannel inChannel = inStream.getChannel();
		FileChannel outChannel = outStream.getChannel();
		inChannel.transferTo(0, inChannel.size(), outChannel);
		inStream.close();
		outStream.close();
	}

}
