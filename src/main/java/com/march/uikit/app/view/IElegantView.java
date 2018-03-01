package com.march.uikit.app.view;

import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.view.View;

import com.march.uikit.widget.TitleView;

/**
 * CreateAt : 2017/12/7
 * Describe :
 * 定义了 View 的一些通用方法，为了达到在 View 层（Activity 和 Fragment） 统一的体验
 *
 * @author chendong
 */
public interface IElegantView extends IView {

    TitleView getTitleView();

    <V extends View> V getView(int resId);

    View getRootView();

    void setClickListener(View.OnClickListener listener, int... viewIds);

    void setTitleText(String left, String center, String right);

    void setTitleText(int pos, String text);

    void setTitleCenterText(String text);

    int getColor(@ColorRes int colorRes);

    String getString(@StringRes int stringRes);
}
