package com.uiho.sgmw.module_login.contract;

import com.uiho.sgmw.common.base.BasePresenter;
import com.uiho.sgmw.common.base.IBaseView;
import com.uiho.sgmw.common.model.UpdateModel;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 作者：uiho_mac
 * 时间：2018/8/7
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public interface MainContract {
    interface View extends IBaseView {

        void subScribeEvent(String apkPath);

        void showUpdateDialog(UpdateModel updateModel);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void checkedAppVersion();

        public abstract void updateApp(String url, final String apkPath, CompositeDisposable cd);
    }
}
