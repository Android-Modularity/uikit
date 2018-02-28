package com.march.uikit.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.march.uikit.UIKit;
import com.march.uikit.app.common.IView;
import com.march.uikit.app.common.ViewConfig;
import com.march.uikit.app.proxy.BasicViewProxy;
import com.march.uikit.widget.TitleView;


/**
 * CreateAt : 2017/12/7
 * Describe : 基类 Activity
 *
 * @author chendong
 */
public abstract class BaseActivity extends AppCompatActivity implements IView, BasicViewProxy.ViewConfigInterface {

    protected BasicViewProxy mViewProxy;

    @Override
    public BasicViewProxy createViewProxy() {
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
        try {
            mViewProxy = BasicViewProxy.create(this);
            initBeforeViewCreated();
            mViewProxy.onCreate();
            initAfterViewCreated();
            mViewProxy.onRestoreInstanceState(savedInstanceState);
            mViewProxy.onViewCreated();
            UIManager.getInst().addActivity(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void initBeforeViewCreated() {

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

    @Override
    public Bundle getData() {
        return mViewProxy.getData();
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
        return new ViewConfig();
    }
}
