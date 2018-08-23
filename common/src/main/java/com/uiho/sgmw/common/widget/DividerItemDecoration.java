package com.uiho.sgmw.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 作者：uiho_mac
 * 时间：2018/4/23
 * 描述：DividerItemDecoration
 * 版本：1.0
 * 修订历史：
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;

    private int mOrientation;
    private boolean drawFirstDecoration = false;

    public DividerItemDecoration(Context context, int orientation) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);
    }

    public Drawable getDivider() {
        return mDivider;
    }

    public void setDivider(Drawable mDivider) {
        this.mDivider = mDivider;
    }

    public boolean isDrawFirstDecoration() {
        return drawFirstDecoration;
    }

    public void setDrawFirstDecoration(boolean drawFirstDecoration) {
        this.drawFirstDecoration = drawFirstDecoration;
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }

    }


    private void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - footerCountWithRefreshView; i++) {
            final View child = parent.getChildAt(i);
            if (!drawFirstDecoration && parent.getChildAdapterPosition(child) < headerCountWithRefreshView) {//不画headers的下面的那个线
                continue;
            }
//            android.support.v7.widget.RecyclerView v = new android.support.v7.widget.RecyclerView(parent.getContext());
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - footerCountWithRefreshView; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }


    private int headerCountWithRefreshView = 0, footerCountWithRefreshView = 0;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //如果是headView，直接设置大小为0
        //注意，这里没考虑横向的，因为暂时不需要，需要的时候再说吧。。。
        if (parent.getChildAdapterPosition(view) < headerCountWithRefreshView || parent.getChildAdapterPosition(view) > parent.getAdapter().getItemCount() - headerCountWithRefreshView - footerCountWithRefreshView) {
            outRect.set(0, 0, 0, isDrawFirstDecoration() ? mDivider.getIntrinsicHeight() : 0);
            return;
        }
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
}
