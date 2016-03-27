package com.github.nnh2.presentationlayer.fragments.core;


import android.content.Context;


public interface BaseView {

    Context getContext();
    BasePresenter getPresenter();

    void showProgress();
    void hideProgress();

    void showError(Throwable throwable);

    void showMessage(int testId);
    void showMessage(String text);

    void showToast(int resId);
    void showToast(String message);
}
