package com.uiho.module_palm.contract;

import com.uiho.sgmw.common.base.BasePresenter;
import com.uiho.sgmw.common.base.IBaseView;
import com.uiho.sgmw.common.model.CarSecurityFenceModel;
import com.uiho.sgmw.common.model.CarStatusModel;

/**
 * 作者：uiho_mac
 * 时间：2018/8/8
 * 描述：电子围栏
 * 版本：1.0
 * 修订历史：
 */
public interface ElectricFenceContract {
    interface View extends IBaseView {
        void setStatus(CarStatusModel model);

        void setCarSecurityFence(CarSecurityFenceModel model);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getCarStatus(String vin, String token);

        public abstract void getCarSecurityFence(String vin, String token);

        public abstract void carSecurityFenceReset(String fenceData, String token);

        public abstract void carSecurityFenceSet(String fenceData, String token, String vin);
    }
}
