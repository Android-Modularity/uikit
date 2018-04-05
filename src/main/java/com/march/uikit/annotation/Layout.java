package com.march.uikit.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * CreateAt : 2018/4/5
 * Describe : 布局
 *
 * @author chendong
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Layout {

    int value() default 0;

    boolean fullScreen() default false;
}
