package com.uiho.sgmw.common.utils;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.uiho.sgmw.common.R;


/**
 * Created by parcool on 2017/8/20.
 */

public class AdapterUtil {
    private static final AdapterUtil ourInstance = new AdapterUtil();

    public static AdapterUtil getInstance() {
        return ourInstance;
    }

    private AdapterUtil() {
    }

    public <T extends BaseQuickAdapter> T buildDefault(T adapter, RecyclerView recyclerView) {
        adapter.bindToRecyclerView(recyclerView);
        adapter.setEnableLoadMore(true);
        if (recyclerView.getLayoutManager() != null) {
            adapter.setEmptyView(R.layout.layout_empty_view);
        }
        return adapter;
    }
}
