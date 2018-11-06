package com.bluetroy.crawler91;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author heyixin
 */
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass=true,exposeProxy = true)
@SpringBootApplication
public class Crawler91Application {

    public static void main(String[] args) {
        SpringApplication.run(Crawler91Application.class, args);
    }
}
