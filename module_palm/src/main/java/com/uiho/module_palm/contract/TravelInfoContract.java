package com.uiho.module_palm.contract;

import com.uiho.sgmw.common.base.BasePresenter;
import com.uiho.sgmw.common.base.IBaseView;
import com.uiho.sgmw.common.model.CarStatusModel;

/**
 * 作者：uiho_mac
 * 时间：2018/8/8
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public interface TravelInfoContract {
    interface View extends IBaseView {
        void setStatus(CarStatusModel model);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getCarStatus(String vin, String token);
    }
}
