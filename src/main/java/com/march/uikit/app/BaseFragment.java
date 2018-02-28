package com.march.uikit.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.march.uikit.app.common.IView;
import com.march.uikit.app.common.ViewConfig;
import com.march.uikit.app.proxy.BasicViewProxy;
import com.march.uikit.app.proxy.LazyLoadViewProxy;
import com.march.uikit.widget.TitleView;


/**
 * CreateAt : 2017/12/7
 * Describe : 基类 Activity
 *
 * @author chendong
 */
public abstract class BaseFragment extends Fragment
        implements IView, BasicViewProxy.ViewConfigInterface {

    protected BasicViewProxy mViewProxy;
    private LazyLoadViewProxy mLazyLoad;

    @Override
    public BasicViewProxy createViewProxy() {
        return null;
    }

    // 支持懒加载
    public LazyLoadViewProxy getLazyLoad() {
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 同步生命周期
    ///////////////////////////////////////////////////////////////////////////


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mLazyLoad != null)
            mLazyLoad.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLazyLoad = getLazyLoad();
        if (mLazyLoad != null) mLazyLoad.setFragment(this);
        mViewProxy = BasicViewProxy.create(this);
        mViewProxy.onCreate();
        mViewProxy.onRestoreInstanceState(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mLazyLoad != null) mLazyLoad.onCreateView(inflater, container);
        return mViewProxy.onCreateView(inflater, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            initAfterViewCreated();
            if (mLazyLoad != null) mLazyLoad.onViewCreated();
            mViewProxy.onViewCreated();
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
        return new ViewConfig().setWithTitle(false);
    }
}
