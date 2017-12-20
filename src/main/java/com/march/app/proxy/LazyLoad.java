package com.march.app.proxy;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.march.common.utils.LogUtils;
import com.march.lifecycle.FragmentLifeCycle;
import com.march.lifecycle.ViewLifeCycle;

/**
 * CreateAt : 2017/12/20
 * Describe :
 *
 * @author chendong
 */
public abstract class LazyLoad implements FragmentLifeCycle, ViewLifeCycle {

    private boolean mIsInited;
    private boolean mIsPrepared;

    private Fragment mFragment;

    public void setFragment(Fragment fragment) {
        mFragment = fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            lazyLoadInternal();
        }
    }

    @Override
    public void onCreate() {
        LogUtils.e("onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container) {
        LogUtils.e("onCreateView");
        mIsPrepared = true;
        mIsInited = false;
        return null;
    }

    @Override
    public void onViewCreated() {
        lazyLoadInternal();
        LogUtils.e("onViewCreated");
    }

    private void lazyLoadInternal() {
        if (mFragment.getUserVisibleHint() && mIsPrepared && !mIsInited) {
            lazyLoad();
            mIsInited = true;
        }
    }

    protected abstract void lazyLoad();

    public void finishLoad(boolean finish) {
        mIsInited = finish;
    }
}
