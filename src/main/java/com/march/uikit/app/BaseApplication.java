package com.march.uikit.app;

import android.app.Application;
import android.text.TextUtils;

import com.march.common.adapter.JsonParseAdapter;
import com.march.common.utils.AppUtils;
import com.march.uikit.UIKit;
import com.march.common.Common;
import com.march.uikit.common.JsonParseAdapterImpl;

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
        if (TextUtils.equals(AppUtils.getProcessName(), getPackageName())) {
            Common.init(this, new JsonParseAdapterImpl());
            UIKit.setUIKitService(new UIKit.UIKitService());
            initInMainProcess();
        }
    }

    protected void initInMainProcess() {

    }

    public static BaseApplication getApp() {
        return sApp;
    }
}
