package com.uiho.module_palm.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
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
import com.uiho.module_palm.contract.TravelInfoClassifyContract;
import com.uiho.module_palm.presenter.TravelInfoClassifyPresenter;
import com.uiho.module_palm.ui.adapter.ChargingDaysAdapter;
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
import com.uiho.sgmw.common.utils.PageResultUtil;
import com.uiho.sgmw.common.utils.RecyclerViewUtils;
import com.uiho.sgmw.common.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.uiho.sgmw.common.utils.DateUtils.MMdd;
import static com.uiho.sgmw.common.utils.DateUtils.yyyy_MM_dd;


/**
 * 作者：uiho_mac
 * 时间：2018/4/24
 * 描述：行驶信息分类
 * 年月日周
 * 版本：1.0
 * 修订历史：
 */
@CreatePresenterAnnotation(TravelInfoClassifyPresenter.class)
@Route(path = RouterPath.PALM_CHARGE_INFO_WEEK) // 路由地址，必须注明
public class ChargingInfoWeekFragment extends BaseMvpFragment<TravelInfoClassifyContract.View, TravelInfoClassifyPresenter> implements TravelInfoClassifyContract.View {
    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R2.id.smart_layout)
    SmartRefreshLayout smartLayout;
    @BindView(R2.id.echart_web)
    WebView eChartWebView;
    @BindView(R2.id.txt_date)
    TextView txtDate;
    @BindView(R2.id.img_pre)
    ImageView imgPre;
    @BindView(R2.id.img_next)
    ImageView imgNext;
    private String vin;
    private String token;
    private String startDate;
    private String endDate;
    List<CarTripDaysModel.InfoArrayBean> mList = new ArrayList<>();
    ChargingDaysAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            vin = getArguments().getString("vin");
        }
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        try {
            endDate = DateUtils.getPreDayString();
            startDate = DateUtils.getWeekFirst(DateUtils.getPreDayString());
            txtDate.setText(startDate.replace("-", "/") + " - " + endDate.replace("-", "/"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        imgNext.setVisibility(View.INVISIBLE);
        token = (String) SPUtils.getParam(mContext, Constants.TOKEN, "");
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new ChargingDaysAdapter(mList);
        RecyclerViewUtils.getInstance().buildDefault(mContext, recyclerView);
        AdapterUtil.getInstance().buildDefault(adapter, recyclerView);
        smartLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getMvpPresenter().getCarTripDays(AESUtil.encrypt(vin, Constants.DAYS_TRIP_KEY), startDate, endDate, token);
            }
        });
    }

    @Override
    protected void lazyFetchData() {
        endDate = DateUtils.getPreDayString();
        try {
            startDate = DateUtils.getWeekFirst(endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getMvpPresenter().getCarTripDays(AESUtil.encrypt(vin, Constants.DAYS_TRIP_KEY), startDate, endDate, token);
        initWebView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initWebView() {
        //进行webwiev的一堆设置
        eChartWebView.getSettings().setAllowFileAccess(true);
        eChartWebView.getSettings().setJavaScriptEnabled(true);
        eChartWebView.addJavascriptInterface(new JS(), "android");
        eChartWebView.loadUrl("file:///android_asset/echarts/myechars.html");
//        final DisplayMetrics dm = new DisplayMetrics();
//        WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
//        manager.getDefaultDisplay().getMetrics(dm);
//        eChartWebView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        int point = (int) event.getX();
//                        if (point > 0 && point < 50 || point > dm.widthPixels - 50 && point < dm.widthPixels) {
//                            eChartWebView.requestDisallowInterceptTouchEvent(false);
//                        } else {
//                            eChartWebView.requestDisallowInterceptTouchEvent(true);
//                        }
//                        break;
//                }
//                return false;
//            }
//        });
    }

    class JS {
        @JavascriptInterface
        public void get(String p) {
            Log.i("single", p);
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
        return R.layout.fragment_charging_info_classify;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void setCarTripDay(CarTripDayModel model) {

    }

    @Override
    public void setCarTripDays(CarTripDaysModel model) {
        smartLayout.finishRefresh();
        if (model != null) {
            if (model.getInfoArray() != null && model.getInfoArray().size() > 0) {
                showEchart("充电(度)", model.getInfoArray());
            }
            PageResultUtil.getInstance().handleResult(model.getInfoArray(), adapter, smartLayout, true, 1);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void showEchart(String unit, List<CarTripDaysModel.InfoArrayBean> mList) {
        eChartWebView.reload();
        EchartsBarBean lineBean = new EchartsBarBean();
        lineBean.unit = unit;
        lineBean.type = "bar";
        lineBean.times = new String[mList.size()];
        lineBean.steps = new String[mList.size()];
        for (int i = 0; i < mList.size(); i++) {
            lineBean.times[i] = DateUtils.formatterDate(yyyy_MM_dd, MMdd, mList.get(i).getTravel_day());
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
            click(-7);
        } else if (i == R.id.img_next) {
            click(7);
        }
    }

    /**
     * 左右切换 选择日期
     *
     * @param flag
     */
    public void click(int flag) {
        try {
            startDate = DateUtils.getNextDay(startDate, flag);
            endDate = DateUtils.getWeekLast(startDate);
            if (DateUtils.compareDate(endDate, DateUtils.getPreDayString())) {
                imgNext.setVisibility(View.VISIBLE);
            } else {
                endDate = DateUtils.getPreDayString();
                imgNext.setVisibility(View.INVISIBLE);
            }
            txtDate.setText(startDate + " - " + endDate);
            getMvpPresenter().getCarTripDays(AESUtil.encrypt(vin, Constants.DAYS_TRIP_KEY), startDate, endDate, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ChargingInfoWeekFragment getInstance(String vin) {
        ChargingInfoWeekFragment fragment = new ChargingInfoWeekFragment();
        Bundle bundle = new Bundle();
        bundle.putString("vin", vin);
        fragment.setArguments(bundle);
        return fragment;
    }
}
