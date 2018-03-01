package com.march.uikit.app.config;

import android.view.View;

import com.march.uikit.app.proxy.BasicViewProxy;

/**
 * CreateAt : 2017/12/7
 * Describe : 页面配置接口
 *
 * @author chendong
 */
public interface ViewConfigInterface {

    int getLayoutId();

    View getLayoutView();

    ViewConfigModel getViewConfig();

    BasicViewProxy newViewProxy();
}
