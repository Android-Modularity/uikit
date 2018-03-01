package com.march.uikit.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * CreateAt : 2018/2/28
 * Describe :
 *
 * @author chendong
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewConfig {

    boolean fullScreen() default false;

    int layoutId() default 0;

    String title() default "";
}
