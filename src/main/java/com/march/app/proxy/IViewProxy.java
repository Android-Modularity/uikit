package com.march.app.proxy;


import com.march.app.common.IView;
import com.march.lifecycle.ViewLifeCycle;

/**
 * CreateAt : 2017/12/7
 * Describe : View 代理接口
 *
 * @author chendong
 */
public interface IViewProxy extends ViewLifeCycle ,IView {

    int TYPE_ACTIVITY = 1;
    int TYPE_FRAGMENT = 2;

}
