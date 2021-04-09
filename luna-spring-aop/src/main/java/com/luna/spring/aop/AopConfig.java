package com.luna.spring.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luna@mac
 * 2021年04月09日 08:42
 */
@Configuration
public class AopConfig {

    @Bean
    public HelloAop helloAop() {
        return new HelloAop();
    }

    @Bean
    public HelloAopTwo helloAopTwo() {
        return new HelloAopTwo();
    }
}
