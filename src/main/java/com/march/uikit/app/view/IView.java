package com.march.uikit.app.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

/**
 * CreateAt : 2017/12/7
 * Describe :
 * View 层基础方法封装
 *
 * @author chendong
 */
public interface IView {

    Context getContext();

    Activity getActivity();

    void startActivity(Class clz);

    Bundle getData();
}
