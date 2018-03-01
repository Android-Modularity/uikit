package com.march.uikit.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.march.uikit.app.config.ViewConfigInterface;
import com.march.uikit.app.config.ViewConfigModel;
import com.march.uikit.app.proxy.BasicViewProxy;
import com.march.uikit.app.proxy.LazyLoadViewProxy;
import com.march.uikit.app.view.IView;
import com.march.uikit.lifecycle.ViewLifeCycle;


/**
 * CreateAt : 2017/12/7
 * Describe : 基类 Activity
 *
 * @author chendong
 */
public class BaseFragment extends Fragment implements IView, ViewLifeCycle, ViewConfigInterface {

    protected BasicViewProxy mViewProxy;
    private LazyLoadViewProxy mLazyLoadViewProxy;

    @Override
    public BasicViewProxy newViewProxy() {
        return null;
    }

    // 支持懒加载
    public LazyLoadViewProxy newLazyLoadProxy() {
        return null;
    }


    //////////////////////////////  -- StateLifeCycle --  //////////////////////////////

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLazyLoadViewProxy = newLazyLoadProxy();
        if (mLazyLoadViewProxy != null) {
            mLazyLoadViewProxy.setHost(this);
        }
        mViewProxy = BasicViewProxy.create(this);
        mViewProxy.onCreate();
        mViewProxy.onRestoreInstanceState(savedInstanceState);
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

    //////////////////////////////  -- FragmentLifeCycle --  //////////////////////////////

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mLazyLoadViewProxy != null)
            mLazyLoadViewProxy.setUserVisibleHint(isVisibleToUser);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mLazyLoadViewProxy != null) {
            mLazyLoadViewProxy.onCreateView(inflater, container, savedInstanceState);
        }
        initBeforeViewCreated();
        View view = mViewProxy.onCreateView(inflater, container, savedInstanceState);
        initCreateView();
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            initAfterViewCreated();
            mViewProxy.onViewReady();
            if (mLazyLoadViewProxy != null) {
                mLazyLoadViewProxy.onViewCreated(view, savedInstanceState);
            }
            mViewProxy.onViewCreated(view, savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //////////////////////////////  -- IView --  //////////////////////////////

    @Override
    public void startActivity(Class clz) {
        mViewProxy.startActivity(clz);
    }

    @Override
    public Bundle getData() {
        return mViewProxy.getData();
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

    @Override
    public void initBeforeViewCreated() {
        mViewProxy.initBeforeViewCreated();
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
        return new ViewConfigModel().setWithTitle(false);
    }
}
