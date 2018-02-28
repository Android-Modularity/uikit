package com.march.uikit.mvp.P;


import com.march.uikit.lifecycle.StateLifeCycle;
import com.march.uikit.mvp.V.MvpView;

/**
 * CreateAt : 2017/12/7
 * Describe :
 *
 * @author chendong
 */
interface IPresenter<V extends MvpView> extends StateLifeCycle {

    /**
     * presenter 被创建，绑定到 View，此时 View 的状态无法确定
     * @param view view
     */
    void onAttachView(V view);

    /**
     * view 执行了 onViewCreated
     */
    void onViewReady();

    /**
     * 从 view 中解除绑定
     */
    void onDetachView();

    /**
     * 获取绑定到的 view
     * @return view
     */
    V getView();
}
