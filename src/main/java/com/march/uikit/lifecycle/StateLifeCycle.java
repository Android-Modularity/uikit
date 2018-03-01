package com.march.uikit.lifecycle;

import android.os.Bundle;

/**
 * CreateAt : 2017/12/7
 * Describe : View 状态生命周期
 *
 * @author chendong
 */
public interface StateLifeCycle {

    void onCreate();

    void onViewReady();

    void onResume();

    void onPause();

    void onDestroy();

    void onSaveInstanceState(Bundle outState);

    void onRestoreInstanceState(Bundle savedInstanceState);
}
