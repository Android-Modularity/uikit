package com.march.uikit.mvp.factory;

import com.march.uikit.mvp.P.MvpPresenter;
import com.march.uikit.mvp.V.MvpView;

/**
 * CreateAt : 2017/12/7
 * Describe : presenter 工厂接口
 *
 * @author chendong
 */
public interface IPresenterFactory<V extends MvpView, P extends MvpPresenter<V>> {

    /**
     * 创建 presenter
     * @return 创建的 presenter
     */
    P newPresenter();
}
