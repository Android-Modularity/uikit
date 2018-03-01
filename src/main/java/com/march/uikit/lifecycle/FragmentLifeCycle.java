package com.march.uikit.lifecycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * CreateAt : 2017/12/20
 * Describe :
 *
 * @author chendong
 */
public interface FragmentLifeCycle {

    void setUserVisibleHint(boolean isVisibleToUser);

    View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    void onViewCreated(View view, Bundle savedInstanceState);
}
