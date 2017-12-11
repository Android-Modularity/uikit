package com.march.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.march.uikit.R;

/**
 * CreateAt : 2016/10/13
 * Describe : 自定义标题栏
 *
 * @author chendong
 */
public class TitleView extends LinearLayout {

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.widget_title, this);
        if (isInEditMode())
            return;
        initViews();
    }

    /**
     * 左边位置
     */
    public static final int LEFT   = 0;
    /**
     * 右边位置
     */
    public static final int RIGHT  = 1;
    /**
     * 中间标题位置
     */
    public static final int CENTER = 2;

    private View     parentView;
    private TextView leftTv;
    private TextView rightTv;
    private TextView centerTv;

    private void initViews() {
        setOrientation(LinearLayout.VERTICAL);
        parentView = findViewById(R.id.title_bar_parent);
        leftTv = (TextView) findViewById(R.id.tv_titlebar_left);
        rightTv = (TextView) findViewById(R.id.tv_titlebar_right);
        centerTv = (TextView) findViewById(R.id.tv_titlebar_center);
    }

    /**
     * 获取某个位置的TextView
     *
     * @param pos 位置
     * @return TextView
     */
    public TextView get(int pos) {
        switch (pos) {
            case LEFT:
                return leftTv;
            case RIGHT:
                return rightTv;
            case CENTER:
                return centerTv;
        }
        return null;
    }

    /**
     * 给某个位置的TextViews设置文本
     *
     * @param pos 位置
     * @param txt 文本
     */
    public void setText(int pos, String txt) {
        get(pos).setText(txt);
    }

    /**
     * 设置文本
     *
     * @param leftTxt   左边显示文本，null时不显示
     * @param centerTxt 中间显示文本，null时不显示
     * @param rightTxt  右边显示文本，null时不显示
     */
    public void setText(String leftTxt, String centerTxt, String rightTxt) {
        if (leftTxt != null)
            leftTv.setText(leftTxt);
        if (centerTxt != null)
            centerTv.setText(centerTxt);
        if (rightTxt != null)
            rightTv.setText(rightTxt);
    }

    public void setCenterText(String text) {
        setText(CENTER, text);
    }

    /**
     * 给某个位置的TextView设置监听
     *
     * @param pos      位置
     * @param listener 监听
     */
    public void setListener(int pos, OnClickListener listener) {
        get(pos).setOnClickListener(listener);
    }

    public void setLeftBackListener(final Activity activity) {
        leftTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
    }

    public View getParentView() {
        return parentView;
    }

    /**
     * 隐藏title
     */
    public void hide() {
        setVisibility(View.GONE);
    }

    /**
     * 隐藏某个位置的显示
     *
     * @param pos 位置
     */
    public void hide(int pos) {
        get(pos).setVisibility(View.GONE);
    }

}
