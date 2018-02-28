package com.march.uikit.lifecycle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * CreateAt : 2017/12/7
 * Describe : View 组件生命周期
 *
 * @author chendong
 */
public interface ViewLifeCycle {

    View onCreateView(LayoutInflater inflater, ViewGroup container);

    void onViewCreated();
}
