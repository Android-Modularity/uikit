package com.march;

import android.app.Activity;

/**
 * CreateAt : 2017/12/7
 * Describe :
 *
 * @author chendong
 */
public class UIKit {

    public static class UIKitService {

        public void log(String tag, String msg, Throwable throwable) {
        }

        public void onActivityStartOrFinish(Activity activity, boolean start) {
        }

        public void toast(String msg, boolean isLongDuration) {

        }

    }

    private static UIKitService sUIKitService;

    public static UIKitService getUIKitService() {
        if (sUIKitService == null) {
            sUIKitService = new UIKitService();
        }
        return sUIKitService;
    }

    public static void setUIKitService(UIKitService UIKitService) {
        sUIKitService = UIKitService;
    }
}
