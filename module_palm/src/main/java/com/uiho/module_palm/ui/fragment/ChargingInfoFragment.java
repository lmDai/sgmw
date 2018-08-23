package com.uiho.module_palm.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.uiho.module_palm.R;
import com.uiho.module_palm.R2;
import com.uiho.sgmw.common.base.BaseFragment;
import com.uiho.sgmw.common.utils.TabFragmentPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 作者：uiho_mac
 * 时间：2018/8/23
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public class ChargingInfoFragment extends BaseFragment {
    @BindView(R2.id.tab_layout)
    SegmentTabLayout tabLayout;
    @BindView(R2.id.view_pager)
    ViewPager viewPager;
    @BindView(R2.id.txt_speed)
    TextView txtSpeed;
    @BindView(R2.id.txt_total)
    TextView txtTotal;
    private String vin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            vin = getArguments().getString("vin");
        }
    }

    @Override
    protected void initView(LayoutInflater inflater) {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_charging_info;
    }

    @Override
    protected void lazyFetchData() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        String[] mTitles_2 = {getResources().getString(R.string.day), getResources().getString(R.string.week),
                getResources().getString(R.string.month), getResources().getString(R.string.year)};
        fragments.add(ChargingInfoDayFragment.getInstance(vin));//日
        fragments.add(ChargingInfoWeekFragment.getInstance(vin));//周
        fragments.add(ChargingInfoMonthFragment.getInstance(vin));//月
        fragments.add(ChargingInfoYearFragment.getInstance(vin));//年
        setViewPage(mTitles_2, fragments);
    }
    /**
     * 设置viewpager
     *
     * @param titles
     * @param xFragment
     */
    public void setViewPage(String[] titles, final ArrayList<Fragment> xFragment) {
        //设置TabLayout的样式
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        //添加数据
        final TabFragmentPagerAdapter xFragmentPagerAdapter = new TabFragmentPagerAdapter(getChildFragmentManager(), titles, xFragment);
        viewPager.setAdapter(xFragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(xFragment.size());
        tabLayout.setTabData(titles);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);
    }
    public static ChargingInfoFragment getInstance(String vin) {
        ChargingInfoFragment fragment = new ChargingInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("vin", vin);
        fragment.setArguments(bundle);
        return fragment;
    }
}
