package com.march.app.proxy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.march.app.common.ViewConfig;
import com.march.widget.TitleView;

/**
 * CreateAt : 2017/12/7
 * Describe :
 *
 * @author chendong
 */
public class BaseViewProxy implements IViewProxy {

    private int mType;
    private Activity mActivity;
    private Fragment mFragment;
    private ViewConfig mViewConfig;
    private TitleView mTitleView;
    private View mRootView;

    public interface ViewConfigInterface {

        int getLayoutId();

        View getLayoutView();

        ViewConfig getViewConfig();

        BaseViewProxy createViewProxy();
    }

    /**
     * 创建一个 proxy
     *
     * @param viewConfigInterface 配置接口
     * @return BaseViewProxy
     */
    public static BaseViewProxy create(ViewConfigInterface viewConfigInterface) {
        BaseViewProxy viewProxy = viewConfigInterface.createViewProxy();
        if (viewProxy == null)
            viewProxy = new BaseViewProxy();
        viewProxy.setHost(viewConfigInterface);
        ViewConfig viewConfig = viewConfigInterface.getViewConfig();
        if (viewConfig == null)
            viewConfig = new ViewConfig();
        viewConfig.init(viewConfigInterface.getLayoutId(), viewConfigInterface.getLayoutView());
        viewProxy.mViewConfig = viewConfig;
        return viewProxy;
    }

    private void setHost(Object host) {
        if (host == null) {
            return;
        }
        if (host instanceof Activity) {
            mActivity = (Activity) host;
            mType = TYPE_ACTIVITY;
        } else if (host instanceof Fragment) {
            mFragment = (Fragment) host;
            mActivity = mFragment.getActivity();
            mType = TYPE_FRAGMENT;
        }
    }

    @Override
    public void onCreate() {
        if (mType == TYPE_ACTIVITY) {
            setContentViewForActivity();
            mRootView = mActivity.findViewById(android.R.id.content);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container) {
        return createViewForFragment(inflater, container);
    }

    @Override
    public void onViewCreated() {

    }


    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public TitleView getTitleView() {
        return mTitleView;
    }

    @Override
    public <V extends View> V getView(int resId) {
        if (mType == TYPE_ACTIVITY) {
            return (V) mActivity.findViewById(resId);
        } else {
            return (V) mRootView.findViewById(resId);
        }
    }

    @Override
    public void initAfterViewCreated() {

    }

    @Override
    public Context getContext() {
        return mActivity;
    }

    @Override
    public Activity getActivity() {
        return mActivity;
    }

    @Override
    public void startActivity(Class clz) {
        mActivity.startActivity(new Intent(getContext(), clz));
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    // 为 Activity 设置界面
    private void setContentViewForActivity() {
        if (mViewConfig == null)
            return;
        View layoutView = mViewConfig.getLayoutView();
        int layoutId = mViewConfig.getLayoutId();
        // 全屏显示
        if (mViewConfig.isFullScreen()) {
            mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        // 隐藏 action bar
        if (mViewConfig.isHideActionBar()) {
            ActionBar actionBar = ((AppCompatActivity) mActivity).getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
            if (mActivity.getActionBar() != null) {
                mActivity.getActionBar().hide();
            }
        }
        // 自动接入 title
        if (mViewConfig.isWithTitle()) {
            mTitleView = new TitleView(mActivity);
            if (layoutId != 0) {
                mActivity.getLayoutInflater().inflate(layoutId, mTitleView, true);
            } else if (layoutView != null) {
                mTitleView.addView(layoutView);
            }
            mActivity.setContentView(mTitleView);
        } else {
            if (layoutId != 0) {
                mActivity.setContentView(layoutId);
            } else if (layoutView != null) {
                mActivity.setContentView(layoutView);
            }
        }
    }

    // 为 Fragment 创建 view
    private View createViewForFragment(LayoutInflater inflater, ViewGroup container) {
        if (mViewConfig == null)
            return null;
        View view = null;
        View layoutView = mViewConfig.getLayoutView();
        int layoutId = mViewConfig.getLayoutId();
        // 自动接入 title
        if (mViewConfig.isWithTitle()) {
            view = mTitleView = new TitleView(mActivity);
            if (layoutId != 0) {
                inflater.inflate(layoutId, mTitleView, true);
            } else if (layoutView != null) {
                mTitleView.addView(layoutView);
            }
        } else {
            if (layoutId != 0) {
                view = inflater.inflate(layoutId, container, false);
            } else if (layoutView != null) {
                view = layoutView;
            }
        }
        mRootView = view;
        return view;
    }
}
