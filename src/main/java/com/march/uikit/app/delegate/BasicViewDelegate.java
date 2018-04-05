package com.march.uikit.app.delegate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.march.common.utils.CheckUtils;
import com.march.common.utils.LogUtils;
import com.march.uikit.app.view.IElegantView;
import com.march.uikit.app.config.IViewConfig;
import com.march.uikit.app.config.ViewConfig;
import com.march.uikit.widget.TitleView;

/**
 * CreateAt : 2017/12/7
 * Describe :
 *
 * @author chendong
 */
public class BasicViewDelegate extends AbsViewDelegate implements IElegantView {

    private ViewConfig mViewConfig;
    private TitleView mTitleView;
    private View mRootView;

    /**
     * 创建一个 proxy
     *
     * @param pageConfInterface 配置接口
     * @return BaseViewProxy
     */
    public static BasicViewDelegate create(IViewConfig pageConfInterface) {
        BasicViewDelegate viewProxy = pageConfInterface.newViewProxy();
        if (viewProxy == null) {
            viewProxy = new BasicViewDelegate();
        }
        viewProxy.setHost(pageConfInterface);
        ViewConfig viewConfig = pageConfInterface.getViewConfig();
        if (viewConfig == null) {
            viewConfig = new ViewConfig();
        }
        viewConfig.init(pageConfInterface.getLayoutId(), pageConfInterface.getLayoutView());
        // 注解配置更优先
        viewConfig.parseViewConfigAnnotation(pageConfInterface);
        viewProxy.mViewConfig = viewConfig;
        return viewProxy;
    }


    @Override
    public void onCreate() {
        if (mType == TYPE_ACTIVITY) {
            setContentViewForActivity();
            mRootView = mActivity.findViewById(android.R.id.content);
        }
    }

    @Override
    public TitleView getTitleView() {
        return mTitleView;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V extends View> V findView(int resId) {
        if (mType == TYPE_ACTIVITY) {
            return (V) mActivity.findViewById(resId);
        } else {
            return (V) mRootView.findViewById(resId);
        }
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
        if (clz == null) {
            return;
        }
        Intent intent = new Intent(getContext(), clz);
        if (getContext().getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            try {
                mActivity.startActivity(intent);
            } catch (Exception e) {
                LogUtils.e(e);
            }
        }
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    @Override
    public Bundle getData() {
        Bundle bundle = null;
        if (mType == TYPE_ACTIVITY && mActivity != null && mActivity.getIntent() != null) {
            bundle = mActivity.getIntent().getExtras();
        } else if (mFragment != null) {
            bundle = mFragment.getArguments();
        }
        if (bundle == null) {
            bundle = new Bundle();
        }
        return bundle;
    }

    @Override
    public void setClickListener(View.OnClickListener listener, int... viewIds) {
        for (int viewId : viewIds) {
            findView(viewId).setOnClickListener(listener);
        }
    }

    @Override
    public void setTitleText(int pos, String text) {
        if (getTitleView() != null) {
            getTitleView().setText(pos, text);
        }
    }

    @Override
    public void setTitleText(String left, String center, String right) {
        if (getTitleView() != null) {
            getTitleView().setText(left, center, right);
        }
    }

    @Override
    public void setTitleCenterText(String text) {
        if (getTitleView() != null) {
            setTitleText(TitleView.CENTER, text);
        }
    }

    @Override
    public int getColor(int colorRes) {
        return getContext().getResources().getColor(colorRes);
    }

    @Override
    public String getString(int stringRes) {
        return getContext().getResources().getString(stringRes);
    }

    @Override
    public void finish() {
        Activity activity = getActivity();
        if (activity != null) {
            activity.finish();
        }
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
        ActionBar actionBar = ((AppCompatActivity) mActivity).getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        if (mActivity.getActionBar() != null) {
            mActivity.getActionBar().hide();
        }
        // 自动接入 showTitleBar
        if (mViewConfig.isWithTitle()) {
            mTitleView = new TitleView(mActivity);
            if (layoutId != 0) {
                mActivity.getLayoutInflater().inflate(layoutId, mTitleView, true);
            } else if (layoutView != null) {
                mTitleView.addView(layoutView);
            }
            mTitleView.setLeftBackListener(mActivity);
            mActivity.setContentView(mTitleView);
        } else {
            if (layoutId != 0) {
                mActivity.setContentView(layoutId);
            } else if (layoutView != null) {
                mActivity.setContentView(layoutView);
            }
        }
        viewInitCommon();
    }

    // 为 Fragment 创建 view
    private View createViewForFragment(LayoutInflater inflater, ViewGroup container) {
        if (mViewConfig == null)
            return null;
        View view = null;
        View layoutView = mViewConfig.getLayoutView();
        int layoutId = mViewConfig.getLayoutId();
        // 自动接入 showTitleBar
        if (mViewConfig.isWithTitle()) {
            view = mTitleView = new TitleView(mActivity);
            if (layoutId != 0) {
                inflater.inflate(layoutId, mTitleView, true);
            } else if (layoutView != null) {
                mTitleView.addView(layoutView);
            }
            mTitleView.setLeftBackListener(mActivity);
        } else {
            if (layoutId != 0) {
                view = inflater.inflate(layoutId, container, false);
            } else if (layoutView != null) {
                view = layoutView;
            }
        }
        mRootView = view;
        viewInitCommon();
        return view;
    }

    private void viewInitCommon() {
        if (mTitleView != null) {
            if (!CheckUtils.isEmpty(mViewConfig.getTitle())) {
                mTitleView.setCenterText(mViewConfig.getTitle());
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return createViewForFragment(inflater, container);
    }
}
