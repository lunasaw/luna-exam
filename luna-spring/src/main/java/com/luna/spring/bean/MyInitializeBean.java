package com.luna.spring.bean;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author luna@mac
 * 2021年03月31日 11:55:00
 */
public class MyInitializeBean implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("执行 afterPropertiesSet 每个Bean 均执行 ==========");
    }
}
