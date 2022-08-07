package com.luna.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {

    /**
     *【改】返回类型为Mono<String>
     * @return
     */
    @GetMapping("/hello")
    public Mono<String> hello() {
        // 【改】使用Mono.just生成响应式数据
        return Mono.just("Welcome to reactive world ~");
    }


}
