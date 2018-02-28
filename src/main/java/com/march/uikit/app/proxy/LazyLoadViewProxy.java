package com.march.uikit.app.proxy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.march.common.utils.LogUtils;
import com.march.uikit.lifecycle.FragmentLifeCycle;

/**
 * CreateAt : 2017/12/20
 * Describe : Fragment 懒加载代理
 *
 * @author chendong
 */
public abstract class LazyLoadViewProxy implements IViewProxy,FragmentLifeCycle {

    private boolean mIsInit;
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
        mIsInit = false;
        return null;
    }

    @Override
    public void onViewCreated() {
        lazyLoadInternal();
        LogUtils.e("onViewCreated");
    }

    private void lazyLoadInternal() {
        if (mFragment.getUserVisibleHint() && mIsPrepared && !mIsInit) {
            lazyLoad();
            mIsInit = true;
        }
    }

    protected abstract void lazyLoad();

    public void finishLoad(boolean finish) {
        mIsInit = finish;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }
}
