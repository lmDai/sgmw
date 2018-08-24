package com.uiho.module_person.contract;

import com.uiho.sgmw.common.base.BasePresenter;
import com.uiho.sgmw.common.base.IBaseView;

/**
 * 作者：uiho_mac
 * 时间：2018/8/8
 * 描述：个人中心
 * 版本：1.0
 * 修订历史：
 */
public interface PersonCenterContract {
    interface View extends IBaseView {

    }

    abstract class Presenter extends BasePresenter<View> {

    }
}
