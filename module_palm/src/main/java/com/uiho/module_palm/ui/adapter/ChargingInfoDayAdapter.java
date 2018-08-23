package com.uiho.module_palm.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.uiho.module_palm.R;
import com.uiho.sgmw.common.model.ChargingDayModel;
import com.uiho.sgmw.common.utils.DateUtils;

import java.util.List;


/**
 * 行程信息按日
 * Created by uiho_mac on 2018/2/6.
 */

public class ChargingInfoDayAdapter extends BaseQuickAdapter<ChargingDayModel.InfoArrayBean, BaseViewHolder> {
    public ChargingInfoDayAdapter(@Nullable List<ChargingDayModel.InfoArrayBean> data) {
        super(R.layout.adapter_charging_day, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChargingDayModel.InfoArrayBean item) {
        helper.setText(R.id.txt_cost, item.getChargeCostTime()+"分钟")
                .setText(R.id.txt_start_date, DateUtils.formatterDate(DateUtils.yyyyMMddHHmmss, DateUtils.MMddHHmm, item.getBeginTime()))
                .setText(R.id.txt_end_date, DateUtils.formatterDate(DateUtils.yyyyMMddHHmmss, DateUtils.MMddHHmm,item.getEndTime()))
                .setText(R.id.txt_start_elec, item.getBeginPowerPercent() + "%")
                .setText(R.id.txt_end_elec, item.getEndPowerPercent() + "%")
                .setText(R.id.txt_charge_power, item.getChargePowerKWH() + "°");
    }
}
