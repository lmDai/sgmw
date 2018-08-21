package com.uiho.sgmw.common.base;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trello.rxlifecycle2.components.support.RxFragment;
import com.uiho.sgmw.common.R;
import com.uiho.sgmw.common.utils.StatusBarCompatUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：uiho_mac
 * 时间：2018/8/6
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
@Keep
public abstract class BaseFragment extends RxFragment {
    protected BaseActivity mActivity;
    private final String TAG = getClass().getSimpleName();
    protected Context mContext;
    protected View rootView;
    protected Unbinder unbinder;
    private boolean isViewPrepared; // 标识fragment视图已经初始化完毕
    private boolean hasFetchData; // 标识已经触发过懒加载数据
    protected boolean showBack = true;
    protected Toolbar toolbar;
    protected TextView textCancel;
    protected TextView textRight;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (BaseActivity) context;
        if (mContext != null) {
            this.mContext = context;
        } else {
            this.mContext = getActivity();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayout(), container, false);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        unbinder = ButterKnife.bind(this, rootView);
        initView(inflater);
        initStatusBar();
        toolbar = rootView.findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("");
            textCancel = toolbar.findViewById(R.id.btn_left);
            if (textCancel != null) {
                textCancel.setOnClickListener(v -> onBackListener());
            }
            ((AppCompatActivity) mContext).setSupportActionBar(toolbar);
            if (showBack) {
                final Drawable upArrow = ContextCompat.getDrawable(mContext, R.drawable.ic_back);
                upArrow.setColorFilter(ContextCompat.getColor(mContext, R.color.color_white), PorterDuff.Mode.SRC_ATOP);
                ActionBar actionBar = ((AppCompatActivity) mContext).getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setHomeAsUpIndicator(upArrow);
                    actionBar.setDisplayHomeAsUpEnabled(true);
                }
            }
        }
        return rootView;
    }

    protected void initStatusBar() {
        StatusBarCompatUtils.setColor(mActivity, mActivity.getResources().getColor(R.color.colorPrimaryDark));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //重写ToolBar返回按钮的行为，防止重新打开父Activity重走生命周期方法
            case android.R.id.home:
                ((AppCompatActivity) mContext).finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onBackListener() {
        ((AppCompatActivity) mContext).finish();
    }

    public void setTopTitle(String str) {
        TextView title = rootView.findViewById(R.id.bt_tv_title);
        title.setText(str);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initEvent();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewPrepared = true;
        lazyFetchDataIfPrepared();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // view被销毁后，将可以重新触发数据懒加载，因为在viewpager下，fragment不会再次新建并走onCreate的生命周期流程，将从onCreateView开始
        hasFetchData = false;
        isViewPrepared = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.v(TAG, getClass().getName() + "------>isVisibleToUser = " + isVisibleToUser);
        if (isVisibleToUser) {
            lazyFetchDataIfPrepared();
        }
    }

    private void lazyFetchDataIfPrepared() {
        // 用户可见fragment && 没有加载过数据 && 视图已经准备完毕
        if (getUserVisibleHint() && !hasFetchData && isViewPrepared) {
            hasFetchData = true;
            lazyFetchData();
        }

    }

    /**
     * 懒加载的方式获取数据，仅在满足fragment可见和视图已经准备好的时候调用一次
     */
    protected void lazyFetchData() {
        Log.v(TAG, getClass().getName() + "------>lazyFetchData");
    }

    public String getName() {
        return BaseFragment.class.getName();
    }

    protected abstract int getLayout();

    protected void initView(LayoutInflater inflater) {
    }

    protected void initEvent() {
    }

}
