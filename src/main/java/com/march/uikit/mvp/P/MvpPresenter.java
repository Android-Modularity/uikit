package com.march.uikit.mvp.P;

import android.os.Bundle;
import android.view.View;

import com.march.uikit.mvp.V.MvpView;

/**
 * CreateAt : 2017/12/7
 * Describe : Presenter 基类
 *
 * @author chendong
 */
public class MvpPresenter<V extends MvpView> implements IPresenter<V> {

    protected V mView;

    /**
     * 无法保证 View 全部完成初始化，建议只读
     * @param view view
     */
    public void onAttachView(V view){
        mView = view;
    }

    public void onDetachView(){
        mView = null;
    }

    public V getView() {
        return mView;
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
}