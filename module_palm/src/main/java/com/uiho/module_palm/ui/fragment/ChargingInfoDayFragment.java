package com.uiho.module_palm.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.uiho.module_palm.R;
import com.uiho.module_palm.R2;
import com.uiho.module_palm.contract.ChargingInfoDayContract;
import com.uiho.module_palm.presenter.ChargingInfoDayPresenter;
import com.uiho.module_palm.ui.adapter.ChargingInfoDayAdapter;
import com.uiho.sgmw.common.Constants;
import com.uiho.sgmw.common.base.BaseMvpFragment;
import com.uiho.sgmw.common.base.RouterPath;
import com.uiho.sgmw.common.model.ChargingDayModel;
import com.uiho.sgmw.common.model.EchartsBarBean;
import com.uiho.sgmw.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.uiho.sgmw.common.utils.AESUtil;
import com.uiho.sgmw.common.utils.AdapterUtil;
import com.uiho.sgmw.common.utils.DateUtils;
import com.uiho.sgmw.common.utils.GsonUtils;
import com.uiho.sgmw.common.utils.PageResultUtil;
import com.uiho.sgmw.common.utils.RecyclerViewUtils;
import com.uiho.sgmw.common.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：uiho_mac
 * 时间：2018/4/24
 * 描述：充电信息分类 日
 * 年月日周
 * 版本：1.0
 * 修订历史：
 */
@CreatePresenterAnnotation(ChargingInfoDayPresenter.class)
@Route(path = RouterPath.PALM_CHARGE_INFO_DAY) // 路由地址，必须注明
public class ChargingInfoDayFragment extends BaseMvpFragment<ChargingInfoDayContract.View, ChargingInfoDayPresenter> implements ChargingInfoDayContract.View {
    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R2.id.smart_layout)
    SmartRefreshLayout smartLayout;
    @BindView(R2.id.echart_web)
    WebView eChartWebView;
    @BindView(R2.id.txt_date)
    TextView txtDate;
    List<ChargingDayModel.InfoArrayBean> mList = new ArrayList<>();
    ChargingInfoDayAdapter adapter;
    @BindView(R2.id.img_pre)
    ImageView imgPre;
    @BindView(R2.id.img_next)
    ImageView imgNext;
    private String vin;
    private String token;
    private String chargeDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            vin = getArguments().getString("vin");
        }
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        imgNext.setVisibility(View.INVISIBLE);
        txtDate.setText(DateUtils.getPreDayString());
        token = (String) SPUtils.getParam(mContext, Constants.TOKEN, "");
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new ChargingInfoDayAdapter(mList);
        RecyclerViewUtils.getInstance().buildDefault(mContext, recyclerView);
        AdapterUtil.getInstance().buildDefault(adapter, recyclerView);
        smartLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getMvpPresenter().getCarTripDayCharge(AESUtil.encrypt(vin, Constants.DAY_ELEC_KEY), chargeDate, token);
            }
        });
    }

    @Override
    protected void lazyFetchData() {
        chargeDate = DateUtils.getPreDayString();
        getMvpPresenter().getCarTripDayCharge(AESUtil.encrypt(vin, Constants.DAY_ELEC_KEY), chargeDate, token);
        initWebView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initWebView() {
        //进行webwiev的一堆设置
        eChartWebView.getSettings().setAllowFileAccess(true);
        eChartWebView.getSettings().setJavaScriptEnabled(true);
        eChartWebView.loadUrl("file:///android_asset/echarts/myechars.html");
    }

    @Override
    public void showError(String msg) {
        eChartWebView.reload();
        eChartWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //最好在这里调用js代码 以免网页未加载完成
                if (eChartWebView != null)
                    eChartWebView.loadUrl("javascript:createChart(" + GsonUtils.GsonString(null) + ");");
            }
        });
        PageResultUtil.getInstance().handleError(adapter, smartLayout, true);
    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_charging_info_classify;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void setCarChargeDay(ChargingDayModel model) {
        if (model != null) {
            if (model.getInfoArray() != null && model.getInfoArray().size() > 0) {
                showEchart("充电(度)", model.getInfoArray());
            }
            PageResultUtil.getInstance().handleResult(model.getInfoArray(), adapter, smartLayout, true, 1);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void showEchart(String unit, List<ChargingDayModel.InfoArrayBean> mList) {
        eChartWebView.reload();
        EchartsBarBean lineBean = new EchartsBarBean();
        lineBean.unit = unit;
        lineBean.type = "bar";
        lineBean.times = new String[mList.size()];
        lineBean.steps = new String[mList.size()];
        for (int i = 0; i < mList.size(); i++) {
            lineBean.times[i] = DateUtils.formatterDate(DateUtils.yyyyMMddHHmmss, DateUtils.HHmm, mList.get(i).getBeginTime());
            lineBean.steps[i] = mList.get(i).getChargePowerKWH() + "";
        }
        eChartWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //最好在这里调用js代码 以免网页未加载完成
                if (eChartWebView != null)
                    eChartWebView.loadUrl("javascript:createChart(" + GsonUtils.GsonString(lineBean) + ");");
            }
        });
    }

    @OnClick({R2.id.img_pre, R2.id.img_next})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.img_pre) {
            click(-1);
        } else if (i == R.id.img_next) {
            click(1);
        }
    }

    public void click(int flag) {
        try {
            chargeDate = DateUtils.formatDate(DateUtils.getNextDay(DateUtils.stringToDate(txtDate.getText().toString(), DateUtils.yyyyMMDD), flag), DateUtils.yyyyMMDD);
            txtDate.setText(chargeDate);
            if (DateUtils.compareDate(chargeDate, DateUtils.getPreDayString())) {
                imgNext.setVisibility(View.VISIBLE);
            } else {
                imgNext.setVisibility(View.INVISIBLE);
            }
            getMvpPresenter().getCarTripDayCharge(AESUtil.encrypt(vin, Constants.DAY_ELEC_KEY), chargeDate, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ChargingInfoDayFragment getInstance(String vin) {
        ChargingInfoDayFragment fragment = new ChargingInfoDayFragment();
        Bundle bundle = new Bundle();
        bundle.putString("vin", vin);
        fragment.setArguments(bundle);
        return fragment;
    }
}
