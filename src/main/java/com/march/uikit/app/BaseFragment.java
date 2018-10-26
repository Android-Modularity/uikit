package com.march.uikit.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.march.uikit.app.config.IViewInit;
import com.march.uikit.app.config.ViewConfig;
import com.march.uikit.app.delegate.LazyLoadViewDelegate;
import com.march.uikit.app.delegate.ViewDelegateImpl;
import com.march.uikit.app.view.IView;


/**
 * CreateAt : 2017/12/7
 * Describe : 基类 Activity
 *
 * @author chendong
 */
public class BaseFragment extends Fragment implements IView, IViewInit {

    protected ViewDelegateImpl     mViewDelegate;
    private   LazyLoadViewDelegate mLazyLoadViewDelegate;

    @Override
    public ViewDelegateImpl newViewDelegate() {
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
        mViewDelegate = ViewDelegateImpl.create(this);
        mViewDelegate.onCreate();
        mViewDelegate.onRestoreInstanceState(savedInstanceState);
        onDispatchInit(savedInstanceState);
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mViewDelegate.onSaveInstanceState(outState);
    }

    //////////////////////////////  -- FragmentLifeCycle --  //////////////////////////////

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mLazyLoadViewDelegate != null) {
            mLazyLoadViewDelegate.setUserVisibleHint(isVisibleToUser);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mLazyLoadViewDelegate != null) {
            mLazyLoadViewDelegate.onCreateView(inflater, container, savedInstanceState);
        }
        View view = mViewDelegate.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            if (mLazyLoadViewDelegate != null) {
                mLazyLoadViewDelegate.onViewCreated(view, savedInstanceState);
            }
            mViewDelegate.onViewCreated(view, savedInstanceState);
            onDispatchInit(savedInstanceState);
            mViewDelegate.onViewReady();
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

    @Override
    public void onDispatchInit(Bundle savedInstanceState) {

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


    public ViewDelegateImpl getViewDelegate() {
        return mViewDelegate;
    }
}
