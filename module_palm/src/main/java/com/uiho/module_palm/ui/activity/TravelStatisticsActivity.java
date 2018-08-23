package com.uiho.module_palm.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.flyco.tablayout.SlidingTabLayout;
import com.uiho.module_palm.R;
import com.uiho.module_palm.R2;
import com.uiho.module_palm.ui.fragment.ChargingInfoFragment;
import com.uiho.module_palm.ui.fragment.TravelInfoFragment;
import com.uiho.sgmw.common.base.BaseActivity;
import com.uiho.sgmw.common.utils.TabFragmentPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 作者：uiho_mac
 * 时间：2018/3/2
 * 描述：行程统计
 * 版本：1.0
 * 修订历史：
 */
public class TravelStatisticsActivity extends BaseActivity {
    @BindView(R2.id.slide_layout)
    SlidingTabLayout slideLayout;
    @BindView(R2.id.view_pager)
    ViewPager viewPager;
    @Override
    protected int getLayout() {
        return R.layout.activity_travel_statistics;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTopTitle(getResources().getString(R.string.text_travel_statistics));
        Bundle bundle = getIntent().getBundleExtra("bundle");
        String vin = bundle.getString("vin");
        String[] titles = new String[]{getResources().getString(R.string.travel_info),getResources().getString(R.string.charging_info)};
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(TravelInfoFragment.getInstance(vin));//行驶信息
        fragments.add(ChargingInfoFragment.getInstance(vin));//充电信息
        setViewPage(titles, fragments);
        showBack = true;
    }
    /**
     * 设置viewpager
     *
     * @param titles
     * @param xFragment
     */
    public void setViewPage(String[] titles, final ArrayList<Fragment> xFragment) {
        //设置TabLayout的样式
        LinearLayout linearLayout = (LinearLayout) slideLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        //添加数据
        final TabFragmentPagerAdapter xFragmentPagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), titles, xFragment);
        viewPager.setAdapter(xFragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(xFragment.size());
        slideLayout.setViewPager(viewPager);
    }
}
