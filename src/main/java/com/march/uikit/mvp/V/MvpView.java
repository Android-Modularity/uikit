package com.march.uikit.mvp.V;

import com.march.uikit.app.view.IView;
import com.march.uikit.mvp.P.MvpPresenter;

/**
 * CreateAt : 2017/12/7
 * Describe :
 *
 * @author chendong
 */
public interface MvpView<P extends MvpPresenter> extends IView {

    P getPresenter();


}
