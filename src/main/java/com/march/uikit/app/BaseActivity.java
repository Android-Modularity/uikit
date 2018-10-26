package com.march.uikit.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.march.uikit.UIKit;
import com.march.uikit.app.config.IViewInit;
import com.march.uikit.app.config.ViewConfig;
import com.march.uikit.app.delegate.ViewDelegateImpl;
import com.march.uikit.app.view.IView;
import com.march.uikit.manager.UIManager;


/**
 * CreateAt : 2017/12/7
 * Describe : 基类 Activity
 *
 * @author chendong
 */
public abstract class BaseActivity extends AppCompatActivity implements IView, IViewInit {

    protected ViewDelegateImpl mViewDelegate;

    @Override
    public ViewDelegateImpl newViewDelegate() {
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

    //////////////////////////////  -- StateLifeCycle --  //////////////////////////////

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mViewDelegate = ViewDelegateImpl.create(this);
            mViewDelegate.onCreate();
            onDispatchInit(savedInstanceState);
            mViewDelegate.onViewReady();
            mViewDelegate.onRestoreInstanceState(savedInstanceState);
            UIManager.getInst().addActivity(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mViewDelegate.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mViewDelegate.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewDelegate.onStop();
        if (isFinishing()) {
            mViewDelegate.onBeforeDestroy();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewDelegate.onDestroy();
        UIManager.getInst().removeActivity(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mViewDelegate.onSaveInstanceState(outState);
    }


    //////////////////////////////  -- ViewLifeCycle --  //////////////////////////////


    @Override
    public void onDispatchInit(Bundle savedInstanceState) {

    }

    //////////////////////////////  -- IView --  //////////////////////////////

    @Override
    public Context getContext() {
        return mViewDelegate.getContext();
    }

    @Override
    public Activity getActivity() {
        return mViewDelegate.getActivity();
    }

    @Override
    public void startActivity(Class clz) {
        mViewDelegate.startActivity(clz);
    }

    @Override
    public Bundle getData() {
        return mViewDelegate.getData();
    }


    //////////////////////////////  -- ViewConfigInterface --  //////////////////////////////
    @Override
    public View getLayoutView() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public ViewConfig getViewConfig() {
        return new ViewConfig();
    }

    public ViewDelegateImpl getViewDelegate() {
        return mViewDelegate;
    }
}
