package com.march.uikit.app.proxy;

import com.march.uikit.lifecycle.StateLifeCycle;
import com.march.uikit.lifecycle.ViewLifeCycle;

/**
 * CreateAt : 2017/12/7
 * Describe : View 代理接口
 *
 * @author chendong
 */
public interface IViewProxy extends StateLifeCycle, ViewLifeCycle {

    int TYPE_ACTIVITY = 1;
    int TYPE_FRAGMENT = 2;
}