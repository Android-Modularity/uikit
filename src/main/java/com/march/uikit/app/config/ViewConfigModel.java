package com.march.uikit.app.config;

import android.view.View;

import com.march.common.utils.CheckUtils;

/**
 * CreateAt : 2017/11/8
 * Describe : UI 相关配置
 *
 * @author chendong
 */
public class ViewConfigModel {

    private boolean isFullScreen = false;
    private boolean isWithTitle = true;
    private String title;

    public boolean isFullScreen() {
        return isFullScreen;
    }

    public ViewConfigModel setFullScreen(boolean fullScreen) {
        this.isFullScreen = fullScreen;
        return this;
    }

    public boolean isWithTitle() {
        return isWithTitle;
    }

    public ViewConfigModel setWithTitle(boolean withTitle) {
        this.isWithTitle = withTitle;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ViewConfigModel setTitle(String title) {
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

    public static void parseViewConfigAnnotation(Object object, ViewConfigModel config) {
        com.march.uikit.annotation.ViewConfig anno = object.getClass().getAnnotation(com.march.uikit.annotation.ViewConfig.class);
        if (anno != null) {
            if (anno.layoutId() != 0) {
                config.setLayoutId(anno.layoutId());
            }
            config.setFullScreen(anno.fullScreen());
            String title = anno.title();
            if (CheckUtils.isEmpty(title)) {
                config.setWithTitle(false);
            } else {
                config.setWithTitle(true);
                config.setTitle(title);
            }
        }
    }
}
