package com.uiho.sgmw.common.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.uiho.sgmw.common.R;


/**
 * 作者：uiho_mac
 * 时间：2018/4/18
 * 描述：自定义提示框
 * 版本：1.0
 * 修订历史：
 */

public class UpdateDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private String content;
    private OnCloseListener listener;
    private String positiveName;
    private String negativeName;
    private String title;
    private boolean showNegativeButton = true;

    public UpdateDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public UpdateDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    public UpdateDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    protected UpdateDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public UpdateDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public UpdateDialog setPositiveButton(String name) {
        this.positiveName = name;
        return this;
    }

    public UpdateDialog setShowNegativeButton(boolean showNegativeButton) {
        this.showNegativeButton = showNegativeButton;
        return this;
    }

    public UpdateDialog setNegativeButton(String name) {
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
        TextView contentTxt = findViewById(R.id.content);
        TextView titleTxt = findViewById(R.id.title);
        TextView submitTxt = findViewById(R.id.submit);
        submitTxt.setOnClickListener(this);
        TextView cancelTxt = findViewById(R.id.cancel);
        cancelTxt.setOnClickListener(this);
        View view = findViewById(R.id.view);

        contentTxt.setText(content);
        if (!TextUtils.isEmpty(positiveName)) {
            submitTxt.setText(positiveName);
        }

        if (!TextUtils.isEmpty(negativeName)) {
            cancelTxt.setText(negativeName);
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
