package com.march.uikit.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.march.uikit.UIKit;
import com.march.uikit.app.config.ViewConfigInterface;
import com.march.uikit.app.config.ViewConfigModel;
import com.march.uikit.app.proxy.BasicViewProxy;
import com.march.uikit.app.view.IView;
import com.march.uikit.lifecycle.ViewLifeCycle;
import com.march.uikit.manager.UIManager;


/**
 * CreateAt : 2017/12/7
 * Describe : 基类 Activity
 *
 * @author chendong
 */
public abstract class BaseActivity extends AppCompatActivity implements IView, ViewLifeCycle, ViewConfigInterface {

    protected BasicViewProxy mViewProxy;

    @Override
    public BasicViewProxy newViewProxy() {
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
            mViewProxy = BasicViewProxy.create(this);
            initBeforeViewCreated();
            mViewProxy.onCreate();
            initCreateView();
            initAfterViewCreated();
            mViewProxy.onViewReady();
            mViewProxy.onRestoreInstanceState(savedInstanceState);
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


    //////////////////////////////  -- ViewLifeCycle --  //////////////////////////////

    @Override
    public void initCreateView() {
        mViewProxy.initCreateView();
    }

    @Override
    public void initAfterViewCreated() {
        mViewProxy.initAfterViewCreated();
    }

    public void initBeforeViewCreated() {
        mViewProxy.initBeforeViewCreated();
    }

    //////////////////////////////  -- IView --  //////////////////////////////

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
    public Bundle getData() {
        return mViewProxy.getData();
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
    public ViewConfigModel getViewConfig() {
        return new ViewConfigModel();
    }
}
