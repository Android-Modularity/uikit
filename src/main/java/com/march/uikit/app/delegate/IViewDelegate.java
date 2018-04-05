package com.march.uikit.app.delegate;

import com.march.uikit.lifecycle.FragmentLifeCycle;
import com.march.uikit.lifecycle.StateLifeCycle;
import com.march.uikit.lifecycle.ViewLifeCycle;

/**
 * CreateAt : 2017/12/7
 * Describe : View 代理接口
 *
 * @author chendong
 */
public interface IViewDelegate extends StateLifeCycle, ViewLifeCycle, FragmentLifeCycle {

    int TYPE_ACTIVITY = 1;
    int TYPE_FRAGMENT = 2;
}
