package com.march.uikit.app.common;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.march.uikit.widget.TitleView;

/**
 * CreateAt : 2017/12/7
 * Describe :
 * 定义了 View 的一些通用方法，为了达到在 View 层（Activity 和 Fragment） 统一的体验
 *
 * @author chendong
 */
public interface IView {

    void initAfterViewCreated();

    TitleView getTitleView();

    <V extends View> V getView(int resId);

    Context getContext();

    Activity getActivity();

    void startActivity(Class clz);

    View getRootView();

    Bundle getData();
}
