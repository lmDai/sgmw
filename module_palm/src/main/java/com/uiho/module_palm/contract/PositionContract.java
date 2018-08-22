package com.uiho.module_palm.contract;

import com.uiho.sgmw.common.base.BasePresenter;
import com.uiho.sgmw.common.base.IBaseView;
import com.uiho.sgmw.common.model.CarSecurityFenceModel;
import com.uiho.sgmw.common.model.CarStatusModel;

/**
 * 作者：uiho_mac
 * 时间：2018/8/8
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public interface PositionContract {
    interface View extends IBaseView {
        void setCarSecurityFence(CarSecurityFenceModel model);

        void setStatus(CarStatusModel model);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getCarSecurityFence(String vin, String token);

        public abstract void getCarStatus(String vin, String token);
    }
}
