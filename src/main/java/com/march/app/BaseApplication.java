package com.march.app;

import android.app.Application;

import com.march.UIKit;

/**
 * CreateAt : 2017/12/7
 * Describe : application
 *
 * @author chendong
 */
public class BaseApplication extends Application {

    protected static BaseApplication sInst;

    @Override
    public void onCreate() {
        super.onCreate();
        sInst = this;
    }

    public static BaseApplication getInst() {
        return sInst;
    }
}
