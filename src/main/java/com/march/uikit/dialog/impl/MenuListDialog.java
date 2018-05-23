package com.march.uikit.dialog.impl;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.march.common.utils.CheckUtils;
import com.march.common.utils.DimensUtils;
import com.march.lightadapter.LightInjector;
import com.march.lightadapter.extend.decoration.LinerDividerDecoration;
import com.march.lightadapter.helper.LightManager;
import com.march.uikit.dialog.BaseDialog;
import com.march.lightadapter.LightAdapter;
import com.march.lightadapter.LightHolder;
import com.march.lightadapter.listener.SimpleItemListener;
import com.march.uikit.R;

import java.util.List;

/**
 * CreateAt : 2017/1/18
 * Describe : 列表菜单dialog
 *
 * @author chendong
 */
public class MenuListDialog extends BaseDialog {

    private int mHeight;
    private TextView mTitleTv;
    private List<Menu> mMenuDatas;
    private String mTitle;

    private OnMenuClickListener mOnMenuClickListener;

    private LightAdapter<Menu> mAdapter;

    public interface OnMenuClickListener {
        void onClick(int pos, Menu data, MenuListDialog dialog);
    }

    public MenuListDialog(Context context, List<Menu> datas) {
        super(context);
        mMenuDatas = datas;
    }

    public MenuListDialog setOnMenuClickListener(OnMenuClickListener onMenuClickListener) {
        this.mOnMenuClickListener = onMenuClickListener;
        return this;
    }

    public MenuListDialog setMenuTitle(String title) {
        mTitle = title;
        return this;
    }

    @Override
    protected void initViewOnCreate() {
        mTitleTv = getView(R.id.tv_menu_title);
        RecyclerView menuRv = getView(R.id.rv_menu_list);
        View lineBelowTitle = getView(R.id.line_below_title);

        if (!CheckUtils.isEmpty(mTitle)) {
            mTitleTv.setVisibility(View.VISIBLE);
            lineBelowTitle.setVisibility(View.VISIBLE);
            mTitleTv.setText(mTitle);
        }

        mAdapter = new LightAdapter<Menu>(getContext(), mMenuDatas, R.layout.dialog_menu_list_item) {
            @Override
            public void onBindView(LightHolder holder, Menu data, int pos, int type) {
                holder.setText(R.id.tv_menu_content, data.display)
                        .setTextColor(R.id.tv_menu_content, data.color);
            }
        };
        mAdapter.setOnItemListener(new SimpleItemListener<Menu>() {
            @Override
            public void onClick(int pos, LightHolder holder, Menu data) {
                if (mOnMenuClickListener != null) {
                    mOnMenuClickListener.onClick(pos, mMenuDatas.get(pos), MenuListDialog.this);
                }
                dismiss();
            }
        });
        LightInjector.initAdapter(mAdapter, this, menuRv, LightManager.vLinear(getContext()));
        LinerDividerDecoration.attachRecyclerView(menuRv, R.drawable.common_shape_divider);

        setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int viewId = v.getId();
                if (viewId == R.id.tv_cancel || viewId == R.id.tv_menu_title)
                    dismiss();
            }
        }, R.id.tv_cancel, R.id.tv_menu_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_menu_list;
    }

    @Override
    protected void setWindowParams() {
        if (mHeight == 0) {
            float targetHeight = DimensUtils.HEIGHT * .618f;
            int totalHeight = DimensUtils.dp2px(46) * mMenuDatas.size() - (mTitleTv.isShown() ? 1 : 0);
            mHeight = totalHeight < targetHeight ? WRAP : (int) targetHeight;
        }

        setDialogAttributes(MATCH, mHeight, Gravity.BOTTOM);
        setAnimationBottomToCenter();
        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }


    public static class Menu {

        int color;
        Object data;
        String display;

        public Menu(Object data, String desc) {
            this(data, desc, Color.argb(0xff, 0x33, 0x33, 0x33));
        }

        public Menu(Object data, String desc, int color) {
            this.data = data;
            this.display = desc;
            this.color = color;
        }

        public <D> D getData() {
            return (D) data;
        }
    }

}
