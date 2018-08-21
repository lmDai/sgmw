package com.uiho.sgmw.common.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.uiho.sgmw.common.R;
import com.uiho.sgmw.common.widget.dialog.CommonDialog;
import com.uiho.sgmw.common.widget.dialog.DialogListener;
import com.uiho.sgmw.common.widget.dialog.IProgressDialog;
import com.uiho.sgmw.common.widget.dialog.UpdateDialog;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * 作者：uiho_mac
 * 时间：2018/4/18
 * 描述：弹框提示工具类
 * 版本：1.0
 * 修订历史：
 */

public class DialogUtils {
    /**
     * 提示框
     *
     * @param mContext 上下文
     * @param content  提示内容
     * @param title    标题
     * @param positive 确定按钮
     * @param negative 取消按钮
     * @param listener 点击监听
     */
    public static void showPromptDialog(Context mContext, String content, String title, String positive, String negative, DialogListener listener) {
        new CommonDialog(mContext, R.style.common_dialog, content, (dialog, confirm) -> {
            if (confirm) {
                dialog.dismiss();
            }
            listener.onClick(confirm);
        }).setTitle(title).setPositiveButton(positive).setNegativeButton(negative).show();
    }

    /**
     * 更新提示框
     *
     * @param mContext
     * @param content      更新内容
     * @param title        标题
     * @param positive     确认按钮
     * @param negative     返回按钮
     * @param shownegative 是否显示返回按钮
     * @param listener     点击监听
     */
    public static void showUpdateDialog(Context mContext, String content, String title, String positive, String negative, boolean shownegative, DialogListener listener) {
        new UpdateDialog(mContext, R.style.common_dialog, content, (dialog, confirm) -> {
            if (confirm) {
                dialog.dismiss();
            }
            listener.onClick(confirm);
        }).setTitle(title).setPositiveButton(positive).setNegativeButton(negative).setShowNegativeButton(shownegative).show();
    }

    /**
     * 显示加载对话框
     * 网络请求等待框
     *
     * @param context 上下文
     * @param msg     对话框显示内容
     */
    public static Dialog showDialogForLoading(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_loading_dialog, null);
        AVLoadingIndicatorView avLoadingIndicatorView = view.findViewById(R.id.AVLoadingIndicatorView);
        final Dialog mLoadingDialog = new Dialog(context, R.style.common_dialog);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        avLoadingIndicatorView.smoothToShow();
        mLoadingDialog.setCancelable(false);
        return mLoadingDialog;
    }

    /**
     * 显示加载框
     *
     * @param context 上下文
     * @param msg     提示信息
     * @return dialog
     */
    public static IProgressDialog getmProgressDialog(Context context) {
        IProgressDialog mProgressDialog = new IProgressDialog() {
            @Override
            public Dialog getDialog() {
                return showDialogForLoading(context);
            }
        };
        return mProgressDialog;
    }
}
