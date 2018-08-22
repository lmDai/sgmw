package com.uiho.sgmw.common.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * 作者：uiho_mac
 * 时间：2018/8/7
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] titles;
    private ArrayList<Fragment> views;

    public TabFragmentPagerAdapter(FragmentManager fm, String[] title, ArrayList<Fragment> views) {
        super(fm);
        this.titles = title;
        this.views = views;
    }

    @Override
    public Fragment getItem(int position) {
        return views.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return views.size();
    }
}
