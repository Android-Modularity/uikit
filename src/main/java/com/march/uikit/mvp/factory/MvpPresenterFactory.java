package com.march.uikit.mvp.factory;

import com.march.uikit.mvp.P.MvpPresenter;
import com.march.uikit.mvp.V.MvpView;

/**
 * CreateAt : 2017/12/7
 * Describe : 生成 presenter 工厂
 *
 * @author chendong
 */
public class MvpPresenterFactory<V extends MvpView, P extends MvpPresenter<V>>
        implements IPresenterFactory<V, P> {

    public static <V extends MvpView, P extends MvpPresenter<V>>
    MvpPresenterFactory<V, P> create(Class<?> viewClz) {
        BindPresenter annotation = viewClz.getAnnotation(BindPresenter.class);
        Class<P> presenterClz = null;
        if (annotation != null) {
            presenterClz = (Class<P>) annotation.value();
        }
        return presenterClz == null ? null : new MvpPresenterFactory<>(presenterClz);
    }

    private Class<P> mPresenterClz;

    public MvpPresenterFactory(Class<P> presenterClz) {
        mPresenterClz = presenterClz;
    }

    @Override
    public P newPresenter() {
        try {
            return mPresenterClz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Presenter创建失败!", e);
        }
    }
}
