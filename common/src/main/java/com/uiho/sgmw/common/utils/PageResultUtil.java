package com.uiho.sgmw.common.utils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

/**
 * Created by parcool on 2017/8/20.
 */

public class PageResultUtil {
    private static final PageResultUtil ourInstance = new PageResultUtil();

    public static PageResultUtil getInstance() {
        return ourInstance;
    }

    private PageResultUtil() {
    }

    public <T> int handleResult(List<T> list, BaseQuickAdapter<T, BaseViewHolder> baseQuickAdapter, SmartRefreshLayout smartRefreshLayout, boolean isRefresh, int page) {
        //设置状态
        if (isRefresh) {
            //刷新
            baseQuickAdapter.setNewData(list);
            smartRefreshLayout.finishRefresh(true);
            page++;
        } else {
            //加载更多
            //加载出错在RxJava的onError里
            if (list == null || list.size() <= 0) {
                baseQuickAdapter.loadMoreEnd(false);
            } else {
                baseQuickAdapter.addData(list);
                page++;
            }
            smartRefreshLayout.finishLoadMore();
            baseQuickAdapter.loadMoreComplete();
        }

        return page;
    }

    public <T> void handleError(BaseQuickAdapter<T, BaseViewHolder> baseQuickAdapter, SmartRefreshLayout smartRefreshLayout, boolean isRefresh) {
        if (isRefresh) {
            baseQuickAdapter.setNewData(null);
            smartRefreshLayout.finishRefresh();
        } else {
            baseQuickAdapter.loadMoreFail();
        }
    }
}
