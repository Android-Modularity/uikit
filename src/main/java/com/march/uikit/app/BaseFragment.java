package com.march.uikit.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.march.uikit.app.config.IViewConfig;
import com.march.uikit.app.config.ViewConfig;
import com.march.uikit.app.delegate.BasicViewDelegate;
import com.march.uikit.app.delegate.LazyLoadViewDelegate;
import com.march.uikit.app.view.IView;
import com.march.uikit.lifecycle.ViewLifeCycle;


/**
 * CreateAt : 2017/12/7
 * Describe : 基类 Activity
 *
 * @author chendong
 */
public class BaseFragment extends Fragment implements IView, ViewLifeCycle, IViewConfig {

    protected BasicViewDelegate mViewDelegate;
    private LazyLoadViewDelegate mLazyLoadViewDelegate;

    @Override
    public BasicViewDelegate newViewProxy() {
        return null;
    }

    // 支持懒加载
    public LazyLoadViewDelegate newLazyLoadProxy() {
        return null;
    }


    //////////////////////////////  -- StateLifeCycle --  //////////////////////////////

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLazyLoadViewDelegate = newLazyLoadProxy();
        if (mLazyLoadViewDelegate != null) {
            mLazyLoadViewDelegate.setHost(this);
        }
        mViewDelegate = BasicViewDelegate.create(this);
        mViewDelegate.onCreate();
        mViewDelegate.onRestoreInstanceState(savedInstanceState);
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
    public void onDestroy() {
        super.onDestroy();
        mViewDelegate.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mViewDelegate.onSaveInstanceState(outState);
    }

    //////////////////////////////  -- FragmentLifeCycle --  //////////////////////////////

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mLazyLoadViewDelegate != null)
            mLazyLoadViewDelegate.setUserVisibleHint(isVisibleToUser);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mLazyLoadViewDelegate != null) {
            mLazyLoadViewDelegate.onCreateView(inflater, container, savedInstanceState);
        }
        initBeforeViewCreated();
        View view = mViewDelegate.onCreateView(inflater, container, savedInstanceState);
        initCreateView();
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            initAfterViewCreated();
            mViewDelegate.onViewReady();
            if (mLazyLoadViewDelegate != null) {
                mLazyLoadViewDelegate.onViewCreated(view, savedInstanceState);
            }
            mViewDelegate.onViewCreated(view, savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //////////////////////////////  -- IView --  //////////////////////////////

    @Override
    public void startActivity(Class clz) {
        mViewDelegate.startActivity(clz);
    }

    @Override
    public Bundle getData() {
        return mViewDelegate.getData();
    }

    //////////////////////////////  -- ViewLifeCycle --  //////////////////////////////

    @Override
    public void initCreateView() {
        mViewDelegate.initCreateView();
    }

    @Override
    public void initAfterViewCreated() {
        mViewDelegate.initAfterViewCreated();
    }

    @Override
    public void initBeforeViewCreated() {
        mViewDelegate.initBeforeViewCreated();
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
        return new ViewConfig().setWithTitle(false);
    }


    public BasicViewDelegate getViewDelegate() {
        return mViewDelegate;
    }
}
