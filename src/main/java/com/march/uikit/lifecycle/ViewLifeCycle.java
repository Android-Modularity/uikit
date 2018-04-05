package com.march.uikit.lifecycle;

/**
 * CreateAt : 2017/12/7
 * Describe : View 组件生命周期
 *
 * @author chendong
 */
public interface ViewLifeCycle {

    void initCreateView();

    void initAfterViewCreated();

    void initBeforeViewCreated();

}
