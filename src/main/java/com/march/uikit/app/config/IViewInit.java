package com.march.uikit.app.config;

import android.view.View;

import com.march.uikit.app.delegate.ViewDelegateImpl;

/**
 * CreateAt : 2017/12/7
 * Describe : 页面配置接口
 *
 * @author chendong
 */
public interface IViewInit {

    int getLayoutId();

    View getLayoutView();

    ViewConfig getViewConfig();

    ViewDelegateImpl newViewDelegate();

}
