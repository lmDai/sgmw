package com.uiho.sgmw.common.mvp_senior.factroy;


import com.uiho.sgmw.common.base.BasePresenter;
import com.uiho.sgmw.common.base.IBaseView;

/**
 * 作者：uiho_mac
 * 时间：2018/8/6
 * 描述：目的为了扩展，可以自定义工厂，创建不同的presenter
 * <p>
 * 版本：1.0
 * 修订历史：
 */

public interface IPresenterProxyFactroy<V extends IBaseView, P extends BasePresenter<V>> {

    /**
     * 设置创建Presenter的工厂
     *
     * @param presenterFactory PresenterFactory类型
     */
    void setPresenterFactory(IMvpPresenterFactroy<V, P> presenterFactory);

    /**
     * 获取Presenter的工厂类
     *
     * @return 返回PresenterMvpFactory类型
     */
    IMvpPresenterFactroy<V, P> getPresenterFactory();

    /**
     * 获取创建的Presenter
     *
     * @return 指定类型的Presenter
     */
    P getMvpPresenter();
}
