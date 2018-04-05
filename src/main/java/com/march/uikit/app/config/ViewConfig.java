package com.march.uikit.app.config;

import android.view.View;

import com.march.uikit.annotation.Layout;
import com.march.uikit.annotation.Title;

/**
 * CreateAt : 2017/11/8
 * Describe : UI 相关配置
 *
 * @author chendong
 */
public class ViewConfig {

    private boolean isFullScreen = false;
    private boolean isWithTitle = true;
    private String title;

    public boolean isFullScreen() {
        return isFullScreen;
    }

    private ViewConfig setFullScreen(boolean fullScreen) {
        this.isFullScreen = fullScreen;
        return this;
    }

    public boolean isWithTitle() {
        return isWithTitle;
    }

    public ViewConfig setWithTitle(boolean withTitle) {
        this.isWithTitle = withTitle;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ViewConfig setTitle(String title) {
        this.title = title;
        return this;
    }

    private int layoutId = 0;
    private View layoutView = null;

    public void init(int layoutId, View layoutView) {
        this.layoutId = layoutId;
        this.layoutView = layoutView;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public View getLayoutView() {
        return layoutView;
    }


    private void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public void parseViewConfigAnnotation(Object object) {
        Layout layoutAnno = object.getClass().getAnnotation(Layout.class);
        if (layoutAnno != null) {
            setLayoutId(layoutAnno.value());
            setFullScreen(layoutAnno.fullScreen());
        }
        Title titleAnno = object.getClass().getAnnotation(Title.class);
        if (titleAnno != null) {
            setTitle(titleAnno.titleText());
            setWithTitle(titleAnno.hasTitle());
        }
    }
}
