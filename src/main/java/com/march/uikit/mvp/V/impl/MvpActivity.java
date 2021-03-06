package com.march.uikit.mvp.V.impl;

import com.march.uikit.app.BaseActivity;
import com.march.uikit.mvp.P.MvpPresenter;
import com.march.uikit.mvp.V.MvpView;
import com.march.uikit.mvp.factory.IPresenterFactory;
import com.march.uikit.mvp.factory.MvpPresenterFactory;
import com.march.uikit.mvp.proxy.MvpViewProxy;

/**
 * CreateAt : 2017/12/7
 * Describe : Activity
 *
 * @author chendong
 */
public abstract class MvpActivity<V extends MvpView, P extends MvpPresenter<V>>
        extends BaseActivity
        implements MvpView<P> {

    @Override
    @SuppressWarnings("unchecked")
    public MvpViewProxy<V, P> newViewDelegate() {
        return MvpViewProxy.create((V) this, getPresenterFactory());
    }

    /**
     * 子类可以重写这个方法，返回自己的工厂类
     *
     * @return presenter 工厂类
     */
    public IPresenterFactory<V, P> getPresenterFactory() {
        return MvpPresenterFactory.create(getClass());
    }

    @Override
    @SuppressWarnings("unchecked")
    public P getPresenter() {
        return ((MvpViewProxy<V, P>)  mViewDelegate).getPresenter();
    }
}
