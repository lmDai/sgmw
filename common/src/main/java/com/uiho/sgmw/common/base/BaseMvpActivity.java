package com.uiho.sgmw.common.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.uiho.sgmw.common.mvp_senior.factroy.IMvpPresenterFactroy;
import com.uiho.sgmw.common.mvp_senior.factroy.IPresenterProxyFactroy;
import com.uiho.sgmw.common.mvp_senior.factroy.MvpPresenterFactroyImpl;
import com.uiho.sgmw.common.mvp_senior.factroy.PresenterProxyFactroyImpl;
import com.uiho.sgmw.common.utils.DialogUtils;

import io.reactivex.ObservableTransformer;

/**
 * 作者：uiho_mac
 * 时间：2018/8/6
 * 描述： * <p>
 * 想要使用mvp，需要继承此BaseMvpActivity
 * <p>
 * TODO:注意  需要创建 presenter，必需要添加注解 @CreatePresenterAnnotation(xxx.class)
 * 版本：1.0
 * 修订历史：
 */

public abstract class BaseMvpActivity<V extends IBaseView, P extends BasePresenter<V>> extends BaseActivity
        implements IPresenterProxyFactroy<V, P>, IBaseView {
    private static final String PRESENTER_SAVE_KEY = "presenter_save_key";


    /**
     * 绑定生命周期
     */
    @Override
    public <T> ObservableTransformer<T, T> bindLifeycle() {
        return this.bindToLifecycle();
    }


    /**
     * 创建被代理对象,传入默认Presenter的工厂
     */
    private PresenterProxyFactroyImpl<V, P> mProxy = new PresenterProxyFactroyImpl<>(MvpPresenterFactroyImpl.<V, P>creater(getClass()));
    private Dialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mProxy.onCreate((V) this);
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mProxy.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_SAVE_KEY));
        }
        mLoadingDialog = DialogUtils.getmProgressDialog(mContext).getDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("perfect-mvp", "V onResume");
//        mProxy.onResume((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("perfect-mvp", "V onDestroy = ");
        mProxy.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("perfect-mvp", "V onSaveInstanceState");
        outState.putBundle(PRESENTER_SAVE_KEY, mProxy.onSaveInstanceState());
    }

    @Override
    public void setPresenterFactory(IMvpPresenterFactroy<V, P> presenterFactory) {
        Log.e("perfect-mvp", "V setPresenterFactory");
        mProxy.setPresenterFactory(presenterFactory);
    }

    @Override
    public IMvpPresenterFactroy<V, P> getPresenterFactory() {
        Log.e("perfect-mvp", "V getPresenterFactory");
        return mProxy.getPresenterFactory();
    }

    @Override
    public P getMvpPresenter() {
        Log.e("perfect-mvp", "V getMvpPresenter");
        return mProxy.getMvpPresenter();
    }


    @Override
    public void showProgress() {
        Log.e("single", "showProgress");
        if (mLoadingDialog != null) {
            mLoadingDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

}
