package com.march.dialog.impl;

import android.content.Context;
import android.view.Gravity;
import android.widget.ProgressBar;

import com.march.dialog.BaseDialog;
import com.march.uikit.R;


/**
 * CreateAt : 2017/1/22
 * Describe : 默认的进度显示
 *
 * @author chendong
 */
public class XProgressDialog extends BaseDialog {

    private ProgressBar mProgressBar;

    public XProgressDialog(Context context) {
        super(context);
    }

    @Override
    protected void initViewOnCreate() {
        mProgressBar = getView(R.id.prgress_bar);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_common_progress;
    }

    @Override
    protected void setWindowParams() {
        buildDefaultParams(WRAP, WRAP, Gravity.CENTER);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
    }

    public void setProgressBar(ProgressBar progressBar) {
        mProgressBar = progressBar;
    }
}
