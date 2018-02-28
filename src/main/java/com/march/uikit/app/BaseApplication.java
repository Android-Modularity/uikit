package com.march.uikit.app;

import android.app.Application;

import com.march.uikit.UIKit;
import com.march.common.Common;

/**
 * CreateAt : 2017/12/7
 * Describe : application
 *
 * @author chendong
 */
public class BaseApplication extends Application {

    protected static BaseApplication sApp;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        Common.init(this);
        UIKit.setUIKitService(new UIKit.UIKitService());
    }

    public static BaseApplication getApp() {
        return sApp;
    }
}
