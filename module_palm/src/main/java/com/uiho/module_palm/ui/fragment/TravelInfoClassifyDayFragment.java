package com.uiho.module_palm.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.uiho.module_palm.R;
import com.uiho.module_palm.R2;
import com.uiho.module_palm.contract.TravelInfoClassifyContract;
import com.uiho.module_palm.presenter.TravelInfoClassifyPresenter;
import com.uiho.module_palm.ui.activity.TravelPathActivity;
import com.uiho.module_palm.ui.adapter.TravelDayClassifyAdapter;
import com.uiho.sgmw.common.Constants;
import com.uiho.sgmw.common.base.BaseMvpFragment;
import com.uiho.sgmw.common.base.RouterPath;
import com.uiho.sgmw.common.model.CarTripDayModel;
import com.uiho.sgmw.common.model.CarTripDaysModel;
import com.uiho.sgmw.common.model.EchartsBarBean;
import com.uiho.sgmw.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.uiho.sgmw.common.utils.AESUtil;
import com.uiho.sgmw.common.utils.AdapterUtil;
import com.uiho.sgmw.common.utils.DateUtils;
import com.uiho.sgmw.common.utils.GsonUtils;
import com.uiho.sgmw.common.utils.IntentUtils;
import com.uiho.sgmw.common.utils.PageResultUtil;
import com.uiho.sgmw.common.utils.RecyclerViewUtils;
import com.uiho.sgmw.common.utils.SPUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：uiho_mac
 * 时间：2018/4/24
 * 描述：行驶信息分类
 * 年月日周
 * 版本：1.0
 * 修订历史：
 */
@CreatePresenterAnnotation(TravelInfoClassifyPresenter.class)
@Route(path = RouterPath.PALM_TRAVEL_INFO_DAY) // 路由地址，必须注明
public class TravelInfoClassifyDayFragment extends BaseMvpFragment<TravelInfoClassifyContract.View, TravelInfoClassifyPresenter> implements TravelInfoClassifyContract.View {
    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R2.id.smart_layout)
    SmartRefreshLayout smartLayout;
    @BindView(R2.id.echart_web)
    WebView eChartWebView;
    @BindView(R2.id.txt_date)
    TextView txtDate;
    List<CarTripDayModel.InfoArrayBean> mList = new ArrayList<>();
    TravelDayClassifyAdapter adapter;
    @BindView(R2.id.img_pre)
    ImageView imgPre;
    @BindView(R2.id.img_next)
    ImageView imgNext;
    @BindView(R2.id.txt_max_speed)
    TextView txtMaxSpeed;
    @BindView(R2.id.txt_speed)
    TextView txtSpeed;
    @BindView(R2.id.txt_total_mileage)
    TextView txtTotalMileage;
    @BindView(R2.id.txt_min_speed)
    TextView txtMinSpeed;
    @BindView(R2.id.txt_use_elec)
    TextView txtUseElec;
    private String vin;
    private String token;
    private String driveDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            vin = getArguments().getString("vin");
        }
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        txtDate.setText(DateUtils.getPreDayString());
        imgNext.setVisibility(View.INVISIBLE);
        token = (String) SPUtils.getParam(mContext, Constants.TOKEN, "");
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new TravelDayClassifyAdapter(mList);
        RecyclerViewUtils.getInstance().buildDefault(mContext, recyclerView);
        AdapterUtil.getInstance().buildDefault(adapter, recyclerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("vin", vin);
                bundle.putString("token", token);
                bundle.putSerializable("details", (Serializable) adapter.getData());
                bundle.putString("time", txtDate.getText().toString());
                bundle.putInt("position", position);
                IntentUtils.get().goActivity(mContext, TravelPathActivity.class, bundle);
            }
        });
        smartLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getMvpPresenter().getCarTripDay(AESUtil.encrypt(vin, Constants.DAY_TRAVEL_KEY), driveDate, token);
            }
        });
    }

    @Override
    protected void lazyFetchData() {
        driveDate = DateUtils.getPreDayString();
        getMvpPresenter().getCarTripDay(AESUtil.encrypt(vin, Constants.DAY_TRAVEL_KEY), driveDate, token);
        initWebView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initWebView() {
        //进行webwiev的一堆设置
        eChartWebView.getSettings().setAllowFileAccess(true);
        eChartWebView.getSettings().setJavaScriptEnabled(true);
        eChartWebView.addJavascriptInterface(new JS(), "android");
        eChartWebView.loadUrl("file:///android_asset/echarts/myechars.html");
    }

    class JS {
        @JavascriptInterface
        public void get(String p) {
            CarTripDayModel.InfoArrayBean bean = adapter.getItem(Integer.parseInt(p));
            txtMaxSpeed.setText("最高速度\n" + bean.getMaxSpeed() + "km/h");
            txtSpeed.setText("平均速度\n" + bean.getAvgSpeed() + "km/h");
            txtTotalMileage.setText("总里程\n" + bean.getDriveMile() + "km");
            txtUseElec.setText("耗电量\n" + bean.getCostPowerKWH());
            txtMinSpeed.setText("最低速度\n" + 0 + "km/h");
        }
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
        return R.layout.fragment_travel_info_classify;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setCarTripDay(CarTripDayModel model) {
        if (model != null) {
            if (model.getInfoArray() != null && model.getInfoArray().size() > 0) {
                showEchart("里程(km)", model.getInfoArray());
            }
            PageResultUtil.getInstance().handleResult(model.getInfoArray(), adapter, smartLayout, true, 1);
        }
    }

    @Override
    public void setCarTripDays(CarTripDaysModel model) {

    }

    @SuppressLint("SetJavaScriptEnabled")
    public void showEchart(String unit, List<CarTripDayModel.InfoArrayBean> mList) {
        eChartWebView.reload();
        if (mList != null && mList.size() > 0) {
            txtMaxSpeed.setText("最高速度\n" + mList.get(0).getMaxSpeed() + "km/h");
            txtSpeed.setText("平均速度\n" + mList.get(0).getAvgSpeed() + "km/h");
            txtTotalMileage.setText("总里程\n" + mList.get(0).getDriveMile() + "km");
            txtUseElec.setText("耗电量\n" + mList.get(0).getCostPowerKWH() + "°");
            txtMinSpeed.setText("最低速度\n" + 0 + "km/h");
            EchartsBarBean lineBean = new EchartsBarBean();
            lineBean.unit = unit;
            lineBean.type = "bar";
            lineBean.times = new String[mList.size()];
            lineBean.steps = new String[mList.size()];
            for (int i = 0; i < mList.size(); i++) {
                lineBean.times[i] = DateUtils.formatterDate(DateUtils.yyyyMMddHHmmss, DateUtils.HHmm, mList.get(i).getBeginTime());
                lineBean.steps[i] = mList.get(i).getDriveMile() + "";
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
                    if (eChartWebView != null) {
                        eChartWebView.loadUrl("javascript:createChart(" + GsonUtils.GsonString(lineBean) + ");");
                    }
                }
            });
        }
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
            driveDate = DateUtils.formatDate(DateUtils.getNextDay(DateUtils.stringToDate(txtDate.getText().toString(), DateUtils.yyyyMMDD), flag), DateUtils.yyyyMMDD);
            txtDate.setText(driveDate);
            if (DateUtils.compareDate(driveDate, DateUtils.getPreDayString())) {
                imgNext.setVisibility(View.VISIBLE);
            } else {
                imgNext.setVisibility(View.INVISIBLE);
            }
            getMvpPresenter().getCarTripDay(AESUtil.encrypt(vin, Constants.DAY_TRAVEL_KEY), driveDate, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static TravelInfoClassifyDayFragment getInstance(String vin) {
        TravelInfoClassifyDayFragment fragment = new TravelInfoClassifyDayFragment();
        Bundle bundle = new Bundle();
        bundle.putString("vin", vin);
        fragment.setArguments(bundle);
        return fragment;
    }
}
