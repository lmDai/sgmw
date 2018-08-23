package com.uiho.module_palm.contract;

import com.uiho.sgmw.common.base.BasePresenter;
import com.uiho.sgmw.common.base.IBaseView;
import com.uiho.sgmw.common.model.ParksModel;

import java.util.List;

/**
 * 作者：uiho_mac
 * 时间：2018/8/8
 * 描述：停车场
 * 版本：1.0
 * 修订历史：
 */
public interface ParksContract {
    interface View extends IBaseView {
        void getParksSuccess(List<ParksModel> mList);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getParks(double longtitude, double latitude);
    }
}
