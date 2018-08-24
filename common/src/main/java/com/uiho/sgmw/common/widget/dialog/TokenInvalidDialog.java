package com.uiho.sgmw.common.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.uiho.sgmw.common.R;


/**
 * 作者：uiho_mac
 * 时间：2018/4/18
 * 描述：token 失效弹出框
 * 版本：1.0
 * 修订历史：
 */

public class TokenInvalidDialog extends Dialog implements View.OnClickListener {
    private TextView contentTxt;
    private TextView titleTxt;
    private TextView submitTxt;
    private TextView cancelTxt;

    private Context mContext;
    private String content;
    private OnCloseListener listener;
    private String positiveName;
    private String negativeName;
    private String title;
    private View view;
    private boolean showNegativeButton = true;

    public TokenInvalidDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public TokenInvalidDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    public TokenInvalidDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    protected TokenInvalidDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public TokenInvalidDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public TokenInvalidDialog setPositiveButton(String name) {
        this.positiveName = name;
        return this;
    }

    public TokenInvalidDialog setShowNegativeButton(boolean showNegativeButton) {
        this.showNegativeButton = showNegativeButton;
        return this;
    }

    public TokenInvalidDialog setNegativeButton(String name) {
        this.negativeName = name;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_commom);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        contentTxt = findViewById(R.id.content);
        titleTxt = findViewById(R.id.title);
        submitTxt = findViewById(R.id.submit);
        submitTxt.setOnClickListener(this);
        cancelTxt = findViewById(R.id.cancel);
        cancelTxt.setOnClickListener(this);
        view = findViewById(R.id.view);

        contentTxt.setText(content);
        if (!TextUtils.isEmpty(positiveName)) {
            submitTxt.setText(positiveName);
        }

        if (!TextUtils.isEmpty(negativeName)) {
            cancelTxt.setText(negativeName);
            cancelTxt.setEnabled(false);
            cancelTxt.setTextColor(ContextCompat.getColor(mContext, R.color.color_text_hint));
        }

        if (!TextUtils.isEmpty(title)) {
            titleTxt.setText(title);
        }
        if (!showNegativeButton) {
            cancelTxt.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cancel) {
            if (listener != null) {
                listener.onClick(this, false);
            }
            this.dismiss();
        } else if (i == R.id.submit) {
            if (listener != null) {
                listener.onClick(this, true);
            }

        }
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean confirm);
    }
}
