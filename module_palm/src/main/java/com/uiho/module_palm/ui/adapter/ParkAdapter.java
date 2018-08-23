package com.uiho.module_palm.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.uiho.module_palm.R;
import com.uiho.sgmw.common.model.ParksModel;
import com.uiho.sgmw.common.utils.StringUtils;

import java.util.List;


/**
 * 停车场
 * Created by uiho_mac on 2018/2/6.
 */

public class ParkAdapter extends BaseQuickAdapter<ParksModel, BaseViewHolder> {

    public ParkAdapter(@Nullable List<ParksModel> data) {
        super(R.layout.adapter_parks, data);
    }

    @Override
    public void setOnItemChildClickListener(OnItemChildClickListener listener) {
        super.setOnItemChildClickListener(listener);
    }

    @Override
    protected void convert(BaseViewHolder helper, ParksModel item) {
        helper.addOnClickListener(R.id.txt_go);
        helper.setText(R.id.txt_name, item.getName());
        helper.setText(R.id.txt_address, StringUtils.double2String(item.getDistance() * 1000, 2) + "米" +
                "  " + StringUtils.filterEmpty(item.getAddress()));
        if (item.isSelect()) {
            helper.getView(R.id.rl_item).setBackground(ContextCompat.getDrawable(mContext, R.drawable.normal_bg_selected));
        } else {
            helper.getView(R.id.rl_item).setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_white));
        }
        TextView unUsed = helper.getView(R.id.txt_unused);
        int unUseNum = (item.getTotalCount() - item.getUsed()) < 0 ? 0 : (item.getTotalCount() - item.getUsed());
        int color;
        if (item.getUsed() >= item.getTotalCount()) {
            color = ContextCompat.getColor(mContext, R.color.park_empty);
        } else if (item.getUsed() < item.getTotalCount() / 2) {
            color = ContextCompat.getColor(mContext, R.color.park_enough);
        } else {
            color = ContextCompat.getColor(mContext, R.color.park_normal);
        }
        SpannableStringBuilder style = new SpannableStringBuilder("剩余车位：" + unUseNum + "个");
        style.setSpan(new ForegroundColorSpan(color), 5, 5 + String.valueOf(unUseNum).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        unUsed.setText(style);
        helper.setText(R.id.txt_total, "总车位：" + item.getTotalCount() + "个");
    }

    public void setSelected(int position) {
        for (ParksModel parksModel : mData) {
            parksModel.setSelect(false);
        }
        mData.get(position).setSelect(true);
        this.notifyDataSetChanged();
    }

    public void setSelectedItem(ParksModel model) {
        for (ParksModel parksModel : mData) {
            parksModel.setSelect(false);
            if (parksModel == model) {
                model.setSelect(true);
            }
        }
        this.notifyDataSetChanged();
    }
}
