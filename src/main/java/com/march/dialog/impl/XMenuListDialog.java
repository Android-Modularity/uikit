package com.march.dialog.impl;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.march.common.utils.CheckUtils;
import com.march.common.utils.DimensUtils;
import com.march.dialog.BaseDialog;
import com.march.lightadapter.core.LightAdapter;
import com.march.lightadapter.core.ViewHolder;
//import com.march.lightadapter.exs.LinerDividerDecoration;
import com.march.lightadapter.listener.SimpleItemListener;
import com.march.uikit.R;

import java.util.List;

/**
 * CreateAt : 2017/1/18
 * Describe : 列表菜单dialog
 *
 * @author chendong
 */
public class XMenuListDialog extends BaseDialog {

    private int mHeight;
    private RecyclerView mMenuRv;
    private TextView mTitleTv;
    private View mLineBelowTitle;
    private List<Menu> mMenuDatas;
    private String mTitle;

    private OnMenuClickListener mOnMenuClickListener;

    public interface OnMenuClickListener {
        void onClick(int pos, Menu data, XMenuListDialog dialog);
    }

    public XMenuListDialog(Context context, List<Menu> datas) {
        super(context);
        mMenuDatas = datas;
    }

    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener) {
        this.mOnMenuClickListener = onMenuClickListener;
    }


    public void setMenuTitle(String title) {
        mTitle = title;
    }


    @Override
    protected void initViewOnCreate() {
        mMenuRv = getView(R.id.rv_menu_list);
        mTitleTv = getView(R.id.tv_menu_title);
        mLineBelowTitle = getView(R.id.line_below_title);


        if (!CheckUtils.isEmpty(mTitle)) {
            mTitleTv.setVisibility(View.VISIBLE);
            mLineBelowTitle.setVisibility(View.VISIBLE);
            mTitleTv.setText(mTitle);
        }

        LightAdapter<Menu> mAdapter = new LightAdapter<Menu>(getContext(), mMenuDatas, R.layout.dialog_item_menu_list) {
            @Override
            public void onBindView(ViewHolder holder, Menu data, int pos, int type) {
                TextView contentTv = (TextView) holder.getView(R.id.tv_menu_content);
                holder.text(R.id.tv_menu_content, data.display);
                contentTv.setTextColor(data.color);
            }
        };
        mAdapter.setOnItemListener(new SimpleItemListener<Menu>() {
            @Override
            public void onClick(int pos, ViewHolder holder, Menu data) {
                dismiss();
                if (mOnMenuClickListener != null) {
                    mOnMenuClickListener.onClick(pos, mMenuDatas.get(pos), XMenuListDialog.this);
                }
            }
        });

        mMenuRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        // LinerDividerDecoration.attachRecyclerView(mMenuRv, R.drawable.common_selector_bg);
        mMenuRv.setAdapter(mAdapter);
    }

    @Override
    protected int[] getViewsRegisterClickEvent() {
        return new int[]{R.id.tv_cancel, R.id.tv_menu_title};
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_common_menu_list;
    }

    @Override
    protected void setWindowParams() {
        if (mHeight == 0) {
            float targetHeight = DimensUtils.HEIGHT * .618f;
            int totalHeight = DimensUtils.dp2px(46) * mMenuDatas.size() - (mTitleTv.isShown() ? 1 : 0);
            mHeight = totalHeight < targetHeight ? WRAP : (int) targetHeight;
        }

        buildDefaultParams(MATCH, mHeight, Gravity.BOTTOM);
        setBottomToCenterAnimation();
        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }


    @Override
    protected void onClickView(View view) {
        int viewId = view.getId();
        if (viewId == R.id.tv_cancel || viewId == R.id.tv_menu_title)
            dismiss();

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
