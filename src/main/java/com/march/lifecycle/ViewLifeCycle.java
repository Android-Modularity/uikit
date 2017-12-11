package com.march.lifecycle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * CreateAt : 2017/12/7
 * Describe :
 *
 * @author chendong
 */
public interface ViewLifeCycle extends BaseLifeCycle {

    void onCreate();

    View onCreateView(LayoutInflater inflater, ViewGroup container);

    void onViewCreated();
}
