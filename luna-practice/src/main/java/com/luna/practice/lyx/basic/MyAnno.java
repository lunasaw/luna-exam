package com.luna.practice.lyx.basic;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author luna@mac
 * @className MyAnno.java
 * @description TODO
 * @createTime 2021年03月05日 17:51:00
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnno {


    String[] value() default "unknown";

}
