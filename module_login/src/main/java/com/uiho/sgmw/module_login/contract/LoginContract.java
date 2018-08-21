package com.uiho.sgmw.module_login.contract;

import com.uiho.sgmw.common.base.BasePresenter;
import com.uiho.sgmw.common.base.IBaseView;

/**
 * 作者：uiho_mac
 * 时间：2018/8/7
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public interface LoginContract {
    interface View extends IBaseView {
        void loginResult(String result);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void login(String mobile, String password);
    }
}
