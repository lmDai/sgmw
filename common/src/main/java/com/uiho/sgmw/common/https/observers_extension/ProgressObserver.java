package com.uiho.sgmw.common.https.observers_extension;

import android.app.Dialog;
import android.support.annotation.NonNull;

import com.uiho.sgmw.common.base.BasePresenter;
import com.uiho.sgmw.common.widget.dialog.IProgressDialog;

import io.reactivex.disposables.Disposable;

/**
 * 作者：uiho_mac
 * 时间：2018/8/6
 * 描述：扩展类，用来调用请求网络时的进度条
 * 版本：1.0
 * 修订历史：
 */
public abstract class ProgressObserver<T> extends BaseObserver<T> {
    private BasePresenter mBasePresenter;
    private IProgressDialog progressDialog;
    private Dialog mDialog;
    private boolean isShowProgress = false;

    protected ProgressObserver(BasePresenter basePresenter) {
        this.mBasePresenter = basePresenter;
        init();
    }

    protected ProgressObserver(BasePresenter mBasePresenter, IProgressDialog progressDialog, boolean isShowProgress) {
        this.mBasePresenter = mBasePresenter;
        this.progressDialog = progressDialog;
        this.isShowProgress = isShowProgress;
        init();
    }

    private void init() {
        if (progressDialog == null) return;
        mDialog = progressDialog.getDialog();
        if (mDialog == null) return;
        mDialog.setCancelable(false);
    }

    /**
     * 展示进度框
     */
    private void showProgress() {
        if (!isShowProgress) {
            mBasePresenter.getView().showProgress();
            return;
        }
        if (mDialog != null) {
            if (!mDialog.isShowing()) {
                mDialog.show();
            }
        }
    }

    /**
     * 取消进度框
     */
    private void dismissProgress() {
        if (!isShowProgress) {
            mBasePresenter.getView().hideProgress();
            return;
        }
        if (mDialog != null) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        if (!d.isDisposed()) {
            showProgress();
        }
    }

    @Override
    public void onComplete() {
        if (mBasePresenter.isViewAttached()) {
            dismissProgress();
        }
    }


    @Override
    public void onError(@NonNull Throwable e) {
        super.onError(e);
        if (mBasePresenter.isViewAttached()) {
            dismissProgress();
        }
    }
}

