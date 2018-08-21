package com.uiho.sgmw.common.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 作者：uiho_mac
 * 时间：2018/8/7
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public class TabPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> views;
    private int currentPageIndex = 0; // 当前page索引（切换之前）
    private FragmentManager fragmentManager;

    public TabPagerAdapter(FragmentManager fm, ArrayList<Fragment> views) {
        super(fm);
        this.fragmentManager = fm;
        this.views = views;
    }
    @Override
    public Fragment getItem(int position) {
        return views.get(position);
    }

    @Override
    public int getCount() {
        return views.size();
    }


    @Override
    public void finishUpdate(ViewGroup container) {
        try {
            super.finishUpdate(container);
        } catch (NullPointerException nullPointerException) {
            Log.d("single1", "Catch the NullPointerException in FragmentPagerAdapter.finishUpdate");
        }
    }
}

