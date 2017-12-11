package com.march.lifecycle;

import android.os.Bundle;
import android.view.View;

/**
 * CreateAt : 2017/12/7
 * Describe : 生命周期
 *
 * @author chendong
 */
public interface BaseLifeCycle {

    void onResume();

    void onPause();

    void onDestroy();

    void onSaveInstanceState(Bundle outState);

    void onRestoreInstanceState(Bundle savedInstanceState);
}
