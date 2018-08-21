package com.uiho.sgmw.module_login.contract;

import com.uiho.sgmw.common.base.BasePresenter;
import com.uiho.sgmw.common.base.IBaseView;

/**
 * 作者：uiho_mac
 * 时间：2018/8/6
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public interface SplashContract {
    interface View extends IBaseView {
        void verifyLogin(boolean isLogin);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void verifyLogin(String token);
    }
}
