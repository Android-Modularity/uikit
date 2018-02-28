package com.march.uikit.app.common;

import android.view.View;

/**
 * CreateAt : 2017/11/8
 * Describe : UI 相关配置
 *
 * @author chendong
 */
public class ViewConfig {

    private boolean isFullScreen = false;
    private boolean isHideActionBar = true;
    private boolean isWithTitle = true;

    public boolean isFullScreen() {
        return isFullScreen;
    }

    public ViewConfig setFullScreen(boolean fullScreen) {
        this.isFullScreen = fullScreen;
        return this;
    }

    public boolean isHideActionBar() {
        return isHideActionBar;
    }

    public ViewConfig setHideActionBar(boolean hideActionBar) {
        this.isHideActionBar = hideActionBar;
        return this;

    }

    public boolean isWithTitle() {
        return isWithTitle;
    }

    public ViewConfig setWithTitle(boolean withTitle) {
        this.isWithTitle = withTitle;
        return this;
    }

    private int layoutId = 0;
    private View layoutView = null;

    public void init(int layoutId,View layoutView){
        this.layoutId = layoutId;
        this.layoutView = layoutView;
    }
    public int getLayoutId() {
        return layoutId;
    }

    public View getLayoutView() {
        return layoutView;
    }
}
