package com.march.uikit.dialog.impl;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.march.common.utils.DimensUtils;
import com.march.common.utils.ViewUtils;
import com.march.uikit.R;
import com.march.uikit.dialog.BaseDialog;

/**
 * CreateAt : 2018/2/28
 * Describe : 信息弹窗
 * 支持0、1、2按钮
 * 支持自定义布局
 *
 * @author chendong
 */
public class AlertMsgDialog extends BaseDialog {

    private TextView mTitleTv;
    private TextView mContentTv;
    private TextView mLeftBtnTv;
    private TextView mRightBtnTv;
    private ViewGroup mContentContainerView;
    private View mLineView;


    public AlertMsgDialog(Context context) {
        super(context);
    }

    @Override
    protected void initOnConstruct() {
        super.initOnConstruct();

        mTitleTv = getView(R.id.tv_title);
        mContentTv = getView(R.id.tv_content);
        mLeftBtnTv = getView(R.id.tv_left);
        mRightBtnTv = getView(R.id.tv_right);
        mContentContainerView = getView(R.id.alert_content);
        mLineView = getView(R.id.view_line);

        View.OnClickListener dismissListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        };
        mLeftBtnTv.setOnClickListener(dismissListener);
        mRightBtnTv.setOnClickListener(dismissListener);
    }

    private DialogInterface.OnClickListener checkEmpty(DialogInterface.OnClickListener listener) {
        if (listener == null) {
            return new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };
        } else {
            return listener;
        }
    }
    @Override
    protected void initViewOnCreate() {
        if(mLeftBtnTv.isShown() || mLeftBtnTv.isShown()){
            mLineView.setVisibility(View.VISIBLE);
        }
    }

    public AlertMsgDialog setContent(String content) {
        ViewUtils.setTextIfNotEmpty(mContentTv, content);
        return this;
    }

    public AlertMsgDialog setTitle(String title) {
        ViewUtils.setTextIfNotEmpty(mTitleTv, title);
        return this;
    }

    public AlertMsgDialog setCustomLayout(View customLayout) {
        mContentContainerView.removeAllViews();
        mContentContainerView.addView(customLayout);
        return this;
    }

    public AlertMsgDialog setLeftBtn(String text, DialogInterface.OnClickListener listener) {
        ViewUtils.setTextIfNotEmpty(mLeftBtnTv, text);
        final OnClickListener onClickListener = checkEmpty(listener);
        mLeftBtnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(AlertMsgDialog.this, 0);
            }
        });
        return this;
    }

    public AlertMsgDialog setRightBtn(String text, final DialogInterface.OnClickListener listener) {
        ViewUtils.setTextIfNotEmpty(mRightBtnTv, text);
        final OnClickListener onClickListener = checkEmpty(listener);
        mRightBtnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(AlertMsgDialog.this, 1);
            }
        });
        return this;
    }

    public AlertMsgDialog setLeftColor(int color) {
        mLeftBtnTv.setTextColor(color);
        return this;
    }

    public AlertMsgDialog setRightColor(int color) {
        mRightBtnTv.setTextColor(color);
        return this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_alert;
    }

    @Override
    protected void setWindowParams() {
        setDialogAttributes((int) (DimensUtils.WIDTH * 0.75f), WRAP, Gravity.CENTER);
    }
}
