package com.uiho.module_palm.ui.adapter;

import android.support.annotation.Nullable;

import com.amap.api.services.help.Tip;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.uiho.module_palm.R;

import java.util.List;

/**
 * 输入提示
 * Created by uiho_mac on 2018/2/6.
 */

public class InputtipsAdapter extends BaseQuickAdapter<Tip, BaseViewHolder> {
    public InputtipsAdapter(@Nullable List<Tip> data) {
        super(R.layout.item_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Tip item) {
        helper.setText(R.id.poi_field_id, item.getName());
        helper.setText(R.id.poi_value_id, item.getAddress());
    }
}
