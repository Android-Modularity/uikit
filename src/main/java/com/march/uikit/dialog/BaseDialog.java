package com.march.uikit.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.march.uikit.R;

/**
 * CreateAt : 16/8/15
 * Describe : dialog基类
 * 第1次 ： 父类构造方法 -> 子类构造方法 -> show() -> onCreated()
 * 第2次 ： show()
 *
 * @author chendong
 */
public abstract class BaseDialog extends AppCompatDialog {
    /**
     * match_parent
     */
    protected int MATCH = ViewGroup.LayoutParams.MATCH_PARENT;
    /**
     * wrap_content
     */
    protected int WRAP = ViewGroup.LayoutParams.WRAP_CONTENT;

    /**
     * 构造函数
     *
     * @param context 上下文
     */
    public BaseDialog(Context context) {
        this(context, R.style.dialog_theme);
    }


    /**
     * 构造函数
     *
     * @param context 上下文
     * @param theme   主题
     */
    public BaseDialog(Context context, int theme) {
        super(context, theme);
        setContentView(getLayoutId());
        initOnConstruct();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewOnCreate();
        setWindowParams();
    }

    protected abstract void initViewOnCreate();

    protected void initOnConstruct() {
    }

    protected abstract int getLayoutId();

    protected abstract void setWindowParams();

    /* 设置从底部到中间的动画 */
    protected void setAnimationBottomToCenter() {
        Window window = getWindow();
        if (window == null)
            return;
        window.setWindowAnimations(R.style.dialog_anim_bottom_center);
    }

    /* 全部参数设置属性 */
    protected void setDialogAttributes(int width, int height, float alpha, float dim, int gravity) {
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        Window window = getWindow();
        if (window == null)
            return;
        WindowManager.LayoutParams params = window.getAttributes();
        // setContentView设置布局的透明度，0为透明，1为实际颜色,该透明度会使layout里的所有空间都有透明度，不仅仅是布局最底层的view
        params.alpha = alpha;
        // 窗口的背景，0为透明，1为全黑
        params.dimAmount = dim;
        params.width = width;
        params.height = height;
        params.gravity = gravity;
        window.setAttributes(params);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    /* 默认不透明+背景黑暗0.6f */
    protected void setDialogAttributes(int width, int height, int gravity) {
        setDialogAttributes(width, height, 1f, .6f, gravity);
    }

    /* 宽度march;高度wrap;alpha=1;dim=0.6;gravity=center */
    protected void setDialogAttributes() {
        setDialogAttributes(MATCH, WRAP, 1f, .6f, Gravity.CENTER);
    }

    protected <V extends View> V getView(int id) {
        return (V) findViewById(id);
    }

    protected void setClickListener(View.OnClickListener listener, int... viewIds) {
        for (int viewId : viewIds) {
            View view = getView(viewId);
            if (view != null) {
                view.setOnClickListener(listener);
            }
        }
    }
}
