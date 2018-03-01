package com.march.uikit.app.proxy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.march.common.utils.LogUtils;

/**
 * CreateAt : 2017/12/20
 * Describe : Fragment 懒加载代理
 *
 * @author chendong
 */
public abstract class LazyLoadViewProxy extends AbsViewProxy {

    private boolean mIsInit;
    private boolean mIsPrepared;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            lazyLoadInternal();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        lazyLoadInternal();
        LogUtils.e("onViewCreated");
        LogUtils.e("onCreateView");
        mIsPrepared = true;
        mIsInit = false;
        return null;
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
}
