package com.march.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public abstract class BaseFragment extends Fragment
        implements IView, BaseViewProxy.ViewConfigInterface {

    protected BaseViewProxy mViewProxy;

    @Override
    public BaseViewProxy createViewProxy() {
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 同步生命周期
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewProxy = BaseViewProxy.create(this);
        mViewProxy.onCreate();
        mViewProxy.onRestoreInstanceState(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mViewProxy.onCreateView(inflater, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAfterViewCreated();
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
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mViewProxy.onSaveInstanceState(outState);
    }

    ///////////////////////////////////////////////////////////////////////////
    // IView 通用逻辑
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public View getRootView() {
        return mViewProxy.getRootView();
    }

    public TitleView getTitleView() {
        return mViewProxy.getTitleView();
    }

    @Override
    public <V extends View> V getView(int resId) {
        return mViewProxy.getView(resId);
    }

    @Override
    public void initAfterViewCreated() {
    }

    @Override
    public void startActivity(Class clz) {
        mViewProxy.startActivity(clz);
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