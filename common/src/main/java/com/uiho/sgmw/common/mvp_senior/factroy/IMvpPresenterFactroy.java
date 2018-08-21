package com.uiho.sgmw.common.mvp_senior.factroy;


import com.uiho.sgmw.common.base.BasePresenter;
import com.uiho.sgmw.common.base.IBaseView;

/**
 * 作者：uiho_mac
 * 时间：2018/8/6
 * 描述：创建Presenter工厂接口
 * <p>
 * 版本：1.0
 * 修订历史：
 */

public interface IMvpPresenterFactroy<V extends IBaseView, P extends BasePresenter<V>> {
    /**
     * 创建Presenter的接口方法
     *
     * @return 需要创建的Presenter
     */
    P createMvpPresenter();
}
