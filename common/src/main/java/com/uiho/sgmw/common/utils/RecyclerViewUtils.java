package com.uiho.sgmw.common.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.uiho.sgmw.common.R;
import com.uiho.sgmw.common.widget.DividerItemDecoration;


/**
 * 作者：uiho_mac
 * 时间：2018/4/23
 * 描述：
 * 版本：1.0
 * 修订历史：
 */

public class RecyclerViewUtils {
    private static final RecyclerViewUtils ourInstance = new RecyclerViewUtils();

    public static RecyclerViewUtils getInstance() {
        return ourInstance;
    }

    private RecyclerViewUtils() {
    }

    public RecyclerView buildDefault(Context context, RecyclerView recyclerView) {
        return buildDefault(context, recyclerView, 0);
    }

    public RecyclerView buildDefault(Context context, RecyclerView recyclerView, int dividerRes) {
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST);
        if (dividerRes != 0) {
            dividerItemDecoration.setDivider(ContextCompat.getDrawable(context, dividerRes));
        } else {
            dividerItemDecoration.setDivider(ContextCompat.getDrawable(context, R.drawable.order_divider));
        }
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(new MyLinearLayoutManagerWrapper(context));
        return recyclerView;
    }

    private class MyLinearLayoutManagerWrapper extends LinearLayoutManager {
        public MyLinearLayoutManagerWrapper(Context context) {
            super(context);
        }

        public MyLinearLayoutManagerWrapper(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public MyLinearLayoutManagerWrapper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

//
//    public <T> RecyclerView buildDefaultWithStickyRecyclerHeadersDecoration(Context context, RecyclerView recyclerView, BaseQuickAdapter<T, BaseViewHolder> baseViewHolderBaseQuickAdapter) {
//        recyclerView = buildDefault(context, recyclerView);
//        baseViewHolderBaseQuickAdapter.setEmptyView(R.layout.layout_empty_view);
//        StickyRecyclerHeadersDecoration stickyRecyclerHeadersDecoration;
//        try {
//            stickyRecyclerHeadersDecoration = new StickyRecyclerHeadersDecoration((StickyRecyclerHeadersAdapter) baseViewHolderBaseQuickAdapter);
//        } catch (ClassCastException e) {
//            e.printStackTrace();
//            return recyclerView;
//        }
//        recyclerView.addItemDecoration(stickyRecyclerHeadersDecoration);//添加StickyHeader的Decoration
//        baseViewHolderBaseQuickAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
//            @Override
//            public void onChanged() {
//                super.onChanged();
//                stickyRecyclerHeadersDecoration.invalidateHeaders();
//            }
//        });
//        return recyclerView;
//    }
}
