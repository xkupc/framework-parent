package com.xkupc.framework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


@ServletComponentScan
@SpringBootApplication
public class SequenceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SequenceApplication.class, args);
    }

}
