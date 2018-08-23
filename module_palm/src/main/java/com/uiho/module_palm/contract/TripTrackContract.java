package com.uiho.module_palm.contract;

import com.uiho.sgmw.common.base.BasePresenter;
import com.uiho.sgmw.common.base.IBaseView;
import com.uiho.sgmw.common.model.TripTrackModel;

/**
 * 作者：uiho_mac
 * 时间：2018/8/8
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public interface TripTrackContract {
    interface View extends IBaseView {
        void setTripTrack(TripTrackModel model);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getCarTripTrack(String vin, String token, String beginTime, String endTime);
    }
}
