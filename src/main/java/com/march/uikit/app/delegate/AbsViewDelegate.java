package com.march.uikit.app.delegate;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * CreateAt : 2018/3/1
 * Describe :
 *
 * @author chendong
 */
class AbsViewDelegate implements IViewDelegate {

    protected int mType;
    protected Activity mActivity;
    protected Fragment mFragment;


    public void setHost(Object host) {
        if (host == null) {
            return;
        }
        if (host instanceof Activity) {
            mActivity = (Activity) host;
            mType = TYPE_ACTIVITY;
        } else if (host instanceof Fragment) {
            mFragment = (Fragment) host;
            mActivity = mFragment.getActivity();
            mType = TYPE_FRAGMENT;
        }
    }



    //////////////////////////////  -- StateLifeCycle --  //////////////////////////////

    @Override
    public void onCreate() {

    }

    @Override
    public void onViewReady() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onBeforeDestroy() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    //////////////////////////////  -- FragmentLifeCycle --  //////////////////////////////

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }
}
