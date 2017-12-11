package com.march.uikit.mvp.factory;

import com.march.uikit.mvp.P.MvpPresenter;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * CreateAt : 2017/12/7
 * Describe : 绑定 presenter 的注解，运行时注解
 *
 * @author chendong
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface BindPresenter {
    Class<? extends MvpPresenter> value();
}
