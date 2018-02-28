package com.march.uikit.widget;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.march.common.utils.LogUtils;
import com.march.uikit.R;

/**
 * CreateAt : 2016/9/12
 * Describe : 滑动选中
 *
 * @author chendong
 */
public class SlidingSelectLayout extends FrameLayout {

    public static final  String TAG             = SlidingSelectLayout.class.getSimpleName();
    private static final float  TOUCH_SLOP_RATE = 0.15f;// 初始化值
    private static final int    INVALID_PARAM   = -1;

    public SlidingSelectLayout(Context context) {
        this(context, null);
    }

    public SlidingSelectLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTagKey(R.id.sliding_pos, R.id.sliding_data);
        itemSpanCount = INVALID_PARAM;
        preViewPos = INVALID_PARAM;
    }

    private RecyclerView mTargetRv;// 内部的rv
    private int          offsetTop;
    private float        xTouchSlop;// 横轴滑动阈值，超过阈值表示触发横轴滑动
    private float        yTouchSlop;// 纵轴滑动阈值，超过阈值表示触发纵轴滑动
    private int          itemSpanCount;// 横向的item数量
    private float        mInitialDownX;// down 事件初始值
    private float        mInitialDownY;// down 事件初始值
    private boolean      isBeingSlide;// 是否正在滑动
    private int          tagPosKey;
    private int          tagDataKey;
    private int          preViewPos;

    private OnSlidingSelectListener onSlidingSelectListener;// 滑动选中监听

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isEnabled())
            return super.onInterceptTouchEvent(ev);
        // 不支持多点触摸
        int pointerCount = ev.getPointerCount();
        if (pointerCount > 1) {
            return super.onInterceptTouchEvent(ev);
        }
        // 获取RecyclerView
        ensureTarget();
        // 初始化参数
        ensureLayoutManager();
        if (!isReadyToIntercept())
            return super.onInterceptTouchEvent(ev);

        int action = MotionEventCompat.getActionMasked(ev);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // build
                mInitialDownX = ev.getX();
                mInitialDownY = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                // stop
                isBeingSlide = false;
                break;
            case MotionEvent.ACTION_MOVE:
                // handle
                float xDiff = Math.abs(ev.getX() - mInitialDownX);
                float yDiff = Math.abs(ev.getY() - mInitialDownY);
                if (yDiff < yTouchSlop && xDiff > xTouchSlop) {
                    isBeingSlide = true;
                }
                break;
        }
        return isBeingSlide;
    }

    private float generateX(float x) {
        return x;
    }

    private float generateY(float y) {
        return y - offsetTop;
    }

    private void setTargetRv(RecyclerView mTargetRv) {
        this.mTargetRv = mTargetRv;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = MotionEventCompat.getActionMasked(ev);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                // re build 手指抬起时重置
                isBeingSlide = false;
                preViewPos = INVALID_PARAM;
                break;
            case MotionEvent.ACTION_MOVE:
                // 滑动过程中，触发监听事件
                publishSlidingCheck(ev);
                break;
        }
        return isBeingSlide;
    }

    /**
     * 初始化参数
     */
    private void ensureLayoutManager() {
        if (mTargetRv == null || itemSpanCount != INVALID_PARAM)
            return;
        RecyclerView.LayoutManager lm = mTargetRv.getLayoutManager();
        if (lm == null)
            return;
        if (lm instanceof GridLayoutManager) {
            GridLayoutManager glm = (GridLayoutManager) lm;
            itemSpanCount = glm.getSpanCount();
        } else {
            LogUtils.e(TAG, "暂时不支持其他布局类型，请使用GridLayoutManager");
            itemSpanCount = 4;
        }
        int size = (int) (getResources().getDisplayMetrics().widthPixels / (itemSpanCount * 1.0f));
        xTouchSlop = yTouchSlop = size * TOUCH_SLOP_RATE;
    }


    /**
     * 发布结果
     *
     * @param event 事件
     */
    private void publishSlidingCheck(MotionEvent event) {
        float x = generateX(event.getX());
        float y = generateY(event.getY());
        View childViewUnder = mTargetRv.findChildViewUnder(x, y);
        // fast stop
        if (onSlidingSelectListener == null || childViewUnder == null)
            return;
        int pos = getPos(childViewUnder);
        Object data = getData(childViewUnder);
        // fast stop 当前触摸的点与上一次触摸的点相同 || 没有pos || 没有数据
        if (preViewPos == pos || pos == INVALID_PARAM || data == null)
            return;
        try {
            // 这里使用范型强制转换
            onSlidingSelectListener.onSlidingSelect(pos, childViewUnder, data);
            preViewPos = pos;
        } catch (ClassCastException e) {
            Log.e("SlidingSelect", "ClassCastException:填写的范型有误，无法转换");
        }
    }

    private void setTagKey(int tagPosKey, int tagDataKey) {
        this.tagPosKey = tagPosKey;
        this.tagDataKey = tagDataKey;
    }

    /**
     * 设置pos和data作为View的tag
     *
     * @param parentView view
     * @param pos pos
     * @param data  data
     */
    public void markView(View parentView, int pos, Object data) {
        parentView.setTag(tagPosKey, pos);
        parentView.setTag(tagDataKey, data);
    }

    private int getPos(View parentView) {
        int pos = INVALID_PARAM;
        Object tag = parentView.getTag(tagPosKey);
        if (tag != null)
            pos = (int) tag;
        return pos;
    }

    private Object getData(View parentView) {
        return parentView.getTag(tagDataKey);
    }

    /**
     * 是否可以开始拦截处理事件，当recyclerView数据完全ok之后开始
     *
     * @return 是否可以开始拦截处理事件
     */

    private boolean isReadyToIntercept() {
        return mTargetRv != null
                && mTargetRv.getAdapter() != null
                && itemSpanCount != INVALID_PARAM;
    }

    /**
     * 获取RecyclerView
     */
    private void ensureTarget() {
        if (mTargetRv != null)
            return;
        View findView = searchInViewGroup(this);
        if (findView == null) {
            LogUtils.e(TAG, "can not find RecyclerView");
        } else {
            mTargetRv = (RecyclerView) findView;
        }
    }

    private View searchInViewGroup(ViewGroup viewGroup) {
        View rstView = null;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof RecyclerView) {
                rstView = childAt;
                break;
            } else if (childAt instanceof ViewGroup) {
                rstView = searchInViewGroup((ViewGroup) childAt);
            }
        }
        return rstView;
    }

    public void setOffsetTop(int offsetTop) {
        this.offsetTop = offsetTop;
    }

    public <D> void setOnSlidingSelectListener(OnSlidingSelectListener<D> onSlidingCheckListener) {
        this.onSlidingSelectListener = onSlidingCheckListener;
    }

    public interface OnSlidingSelectListener<D> {
        void onSlidingSelect(int pos, View parentView, D data);
    }
}

