package com.march.uikit.manager;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import java.util.Stack;

/**
 * CreateAt : 2017/12/10
 * Describe : UI 管理类
 *
 * @author chendong
 */
public class UIManager {

    private static UIManager sInst;

    public static UIManager getInst() {
        if (sInst == null) {
            synchronized (UIManager.class) {
                if (sInst == null) {
                    sInst = new UIManager();
                }
            }
        }
        return sInst;
    }


    private UIManager() {
        mActivityStack = new Stack<>();
        mHandler = new Handler(Looper.getMainLooper());
    }

    private Handler mHandler;
    private Stack<Activity> mActivityStack;

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if(!mActivityStack.contains(activity))
        mActivityStack.add(activity);
    }

    /**
     * 移除Activity
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (mActivityStack.contains(activity))
            mActivityStack.remove(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity lastActivity() {
        return mActivityStack.lastElement();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishLastActivity() {
        Activity activity = mActivityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            if (mActivityStack.contains(activity)) {
                finishActivityInternal(activity);
                mActivityStack.remove(activity);
            }
            activity = null;
        }
    }


    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            finishActivity(mActivityStack.get(i));
        }
        mActivityStack.clear();
    }

    public boolean isAppExit() {
        return mActivityStack == null || mActivityStack.isEmpty();
    }

    private void finishActivityInternal(Activity activity) {
        if (activity != null && !isActivityFinish(activity)) {
            activity.finish();
        }
    }

    public boolean isActivityFinish(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return activity.isDestroyed() || activity.isFinishing();
        } else {
            return activity.isFinishing();
        }
    }

    public void postUI(Runnable runnable, long time) {
        mHandler.postDelayed(runnable, time);
    }

    public Handler getHandler() {
        return mHandler;
    }
}
