package com.github.nnh2.presentationlayer.fragments.core;

import android.content.Context;

import rx.android.schedulers.AndroidSchedulers;

public abstract class BasePresenter<V extends BaseView> {

	private V view;

	protected void onViewBeforeDetached() {
	}

	protected void onViewAttached() {

	}

	protected void onViewDetached() {
	}

	public final V view() {
		return view;
	}

	public final void setView(V view) {
		if (view == null) {
						if (this.view != null) {
				onViewBeforeDetached();
			}
			this.view = null;
			onViewDetached();
		} else {
			this.view = view;
			onViewAttached();
		}
	}

	public final Context getContext() {
		return view == null ? null : view.getContext();
	}

	public void handleError(Throwable throwable) {

		view().hideProgress();
		// Use in Retrofit
//		if (throwable instanceof RetrofitError) {
//			RetrofitError error = (RetrofitError) throwable;
//			if (error.getKind() == RetrofitError.Kind.NETWORK) {
//				view().showToast(R.string.dialog_internet_error);
//			} else if (error.getKind() == RetrofitError.Kind.HTTP) {
//				Response response = error.getResponse();
//				if (response != null) {
//					handleHttpError(error, response.getStatus());
//				} else {
//					view().showMessage(error.getMessage());
//				}
//			}
//			if (error.getKind() == RetrofitError.Kind.UNEXPECTED ||
//					error.getKind() == RetrofitError.Kind.CONVERSION) {
//				view().showMessage(error.getMessage());
//			}
//		} else
		{
			view().showToast(throwable.getMessage());
		}

	}

//	protected void handleHttpError(RetrofitError error, int status) {
//		switch (status) {
//			case 401:
//				view().showMessage(R.string.dialog_invalid_token);
//				break;
//		}
//	}
}
