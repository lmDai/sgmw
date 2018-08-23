package com.uiho.module_palm.ui.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.uiho.module_palm.R;
import com.uiho.module_palm.R2;
import com.uiho.module_palm.ui.adapter.InputtipsAdapter;
import com.uiho.sgmw.common.base.BaseActivity;
import com.uiho.sgmw.common.utils.AdapterUtil;
import com.uiho.sgmw.common.utils.IntentUtils;
import com.uiho.sgmw.common.utils.RecyclerViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：uiho_mac
 * 时间：2018/8/23
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public class InputtipsActivity extends BaseActivity implements TextWatcher, Inputtips.InputtipsListener {
    @BindView(R2.id.bt_tv_title)
    TextView btTvTitle;
    @BindView(R2.id.input_edit)
    AutoCompleteTextView inputEdit;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.inputlist)
    RecyclerView inputlist;
    private String city;
    private InputtipsAdapter adapter;
    private List<Tip> mList = new ArrayList<>();

    @Override
    protected int getLayout() {
        showBack = false;
        return R.layout.activity_inputtip;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        final Drawable upArrow = ContextCompat.getDrawable(mContext, R.drawable.ic_black_back);
        upArrow.setColorFilter(ContextCompat.getColor(mContext, R.color.color_black), PorterDuff.Mode.SRC_ATOP);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        adapter = new InputtipsAdapter(mList);
        RecyclerViewUtils.getInstance().buildDefault(mContext, inputlist);
        AdapterUtil.getInstance().buildDefault(adapter, inputlist);
    }

    @Override
    protected void initEvent() {
        inputEdit.addTextChangedListener(this);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Tip tip = (Tip) adapter.getData().get(position);
                Intent intent = new Intent();
                intent.putExtra(IntentUtils.OPEN_ACTIVITY_KEY, tip); //将计算的值回传回去
                setResult(0x002, intent);
                finish();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String newText = s.toString().trim();
        InputtipsQuery inputquery = new InputtipsQuery(newText, city);
        inputquery.setCityLimit(true);
        Inputtips inputTips = new Inputtips(InputtipsActivity.this, inputquery);
        inputTips.setInputtipsListener(this);
        inputTips.requestInputtipsAsyn();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onGetInputtips(List<Tip> tipList, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            adapter.setNewData(tipList);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
