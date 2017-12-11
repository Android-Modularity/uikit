package com.march.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.march.UIKit;
import com.march.app.common.IView;
import com.march.app.common.ViewConfig;
import com.march.app.proxy.BaseViewProxy;
import com.march.widget.TitleView;


/**
 * CreateAt : 2017/12/7
 * Describe : 基类 Activity
 *
 * @author chendong
 */
public abstract class BaseActivity extends AppCompatActivity
        implements IView, BaseViewProxy.ViewConfigInterface {

    protected BaseViewProxy mViewProxy;

    @Override
    public BaseViewProxy createViewProxy() {
        return null;
    }


    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        UIKit.getUIKitService().onActivityStartOrFinish(getActivity(), true);
    }

    @Override
    public void finish() {
        super.finish();
        UIKit.getUIKitService().onActivityStartOrFinish(getActivity(), false);
    }


    ///////////////////////////////////////////////////////////////////////////
    // 同步生命周期
    ///////////////////////////////////////////////////////////////////////////


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewProxy = BaseViewProxy.create(this);
        mViewProxy.onCreate();
        initAfterViewCreated();
        mViewProxy.onRestoreInstanceState(savedInstanceState);
        mViewProxy.onViewCreated();
        UIManager.getInst().addActivity(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewProxy.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mViewProxy.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewProxy.onDestroy();
        UIManager.getInst().removeActivity(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mViewProxy.onSaveInstanceState(outState);
    }

    ///////////////////////////////////////////////////////////////////////////
    // IView
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void initAfterViewCreated() {

    }

    @Override
    public Context getContext() {
        return mViewProxy.getContext();
    }

    @Override
    public Activity getActivity() {
        return mViewProxy.getActivity();
    }

    @Override
    public void startActivity(Class clz) {
        mViewProxy.startActivity(clz);
    }

    @Override
    public TitleView getTitleView() {
        return mViewProxy.getTitleView();
    }

    @Override
    public <V extends View> V getView(int resId) {
        return mViewProxy.getView(resId);
    }

    @Override
    public View getRootView() {
        return mViewProxy.getRootView();
    }


    ///////////////////////////////////////////////////////////////////////////
    // IViewConfigInterface
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public View getLayoutView() {
        return null;
    }

    @Override
    public ViewConfig getViewConfig() {
        return null;
    }
}
