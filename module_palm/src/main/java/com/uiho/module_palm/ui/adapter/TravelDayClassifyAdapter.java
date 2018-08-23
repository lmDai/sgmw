package com.uiho.module_palm.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.uiho.module_palm.R;
import com.uiho.sgmw.common.model.CarTripDayModel;
import com.uiho.sgmw.common.utils.DateUtils;

import java.util.List;


/**
 * 行程信息按日
 * Created by uiho_mac on 2018/2/6.
 */

public class TravelDayClassifyAdapter extends BaseQuickAdapter<CarTripDayModel.InfoArrayBean, BaseViewHolder> {
    public TravelDayClassifyAdapter(@Nullable List<CarTripDayModel.InfoArrayBean> data) {
        super(R.layout.adapter_travel_info_classify, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarTripDayModel.InfoArrayBean item) {
        helper.setText(R.id.txt_start_elec, "开始电量：" + item.getBeginPowerPercent() + "%")
                .setText(R.id.txt_end_elec, "结束电量：" + item.getEndPowerPercent() + "%")
                .setText(R.id.txt_use_elec, "消耗电度：" + item.getCostPowerKWH() + "°")
                .setText(R.id.txt_speed, "平均速度：" + item.getAvgSpeed() + " km/h")
                .setText(R.id.txt_trip_mile, "行驶里程：" + item.getDriveMile() + " km")
                .setText(R.id.txt_max_speed, "最高速度：" + item.getMaxSpeed() + " km/h")
                .setText(R.id.txt_cost, item.getDriveCostTime() + "分钟")
                .setText(R.id.txt_start_date, DateUtils.formatterDate(DateUtils.yyyyMMddHHmmss, DateUtils.MMddHHmm, item.getBeginTime()) + "")
                .setText(R.id.txt_end_date, DateUtils.formatterDate(DateUtils.yyyyMMddHHmmss, DateUtils.MMddHHmm, item.getEndTime()) + "");
    }
}
