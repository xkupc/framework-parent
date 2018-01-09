package com.xkupc.framework;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author xk
 * @createTime 2018/1/8 0008 下午 4:25
 * @description
 */
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(TestApplication.class).web(true).run(args);
    }
}
