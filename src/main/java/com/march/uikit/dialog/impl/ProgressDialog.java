package com.march.uikit.dialog.impl;

import android.content.Context;
import android.view.Gravity;
import android.widget.ProgressBar;

import com.march.uikit.dialog.BaseDialog;
import com.march.uikit.R;


/**
 * CreateAt : 2017/1/22
 * Describe : 默认的进度显示
 *
 * @author chendong
 */
public class ProgressDialog extends BaseDialog {

    private ProgressBar mProgressBar;

    public ProgressDialog(Context context) {
        super(context);
    }

    @Override
    protected void initViewOnCreate() {
        mProgressBar = getView(R.id.prgress_bar);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_progress;
    }

    @Override
    protected void setWindowParams() {
        setDialogAttributes(WRAP, WRAP, Gravity.CENTER);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
    }

    public void setProgressBar(ProgressBar progressBar) {
        mProgressBar = progressBar;
    }
}
