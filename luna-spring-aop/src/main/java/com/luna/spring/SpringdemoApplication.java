package com.luna.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author luna_mac
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SpringdemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringdemoApplication.class, args);

    }

}
