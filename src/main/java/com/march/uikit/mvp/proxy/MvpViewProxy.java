package com.march.uikit.mvp.proxy;

import android.os.Bundle;
import android.util.Log;

import com.march.UIKit;
import com.march.app.proxy.BaseViewProxy;
import com.march.uikit.mvp.P.MvpPresenter;
import com.march.uikit.mvp.V.MvpView;
import com.march.uikit.mvp.factory.IPresenterFactory;

/**
 * CreateAt : 2017/12/7
 * Describe : Mvp View 代理
 *
 * @author chendong
 */
public class MvpViewProxy<V extends MvpView, P extends MvpPresenter<V>> extends BaseViewProxy {

    private IPresenterFactory<V, P> mFactory;
    private P mPresenter;
    private V mView;

    public static <V extends MvpView, P extends MvpPresenter<V>> MvpViewProxy<V, P> create(V view, IPresenterFactory<V, P> factory) {
        MvpViewProxy<V, P> viewProxy = new MvpViewProxy<>();
        viewProxy.mView = view;
        if (viewProxy.mPresenter != null) {
            throw new IllegalStateException("presenter 已经生成");
        }
        viewProxy.mFactory = factory;
        return viewProxy;
    }


    /**
     * 获取当前绑定的 presenter 用来执行任务
     *
     * @return 绑定的 presenter
     */
    public P getPresenter() {
        if (mPresenter == null && mFactory != null) {
            log("newPresenter  onAttachView");
            mPresenter = mFactory.newPresenter();
            mPresenter.onAttachView(mView);
        }
        return mPresenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getPresenter() != null) {
            log("onResume");
            mPresenter.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getPresenter() != null) {
            log("onPause");
            mPresenter.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getPresenter() != null) {
            log("onDetachView onDestroy");
            mPresenter.onDetachView();
            mPresenter.onDestroy();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (getPresenter() != null) {
            log("onSaveInstanceState");
            mPresenter.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (getPresenter() != null) {
            log("onRestoreInstanceState");
            mPresenter.onRestoreInstanceState(savedInstanceState);
        }
    }

    public static final String TAG = MvpViewProxy.class.getSimpleName();

    public void log(String msg) {
        UIKit.getUIKitService().log(TAG, msg, null);
    }
}
