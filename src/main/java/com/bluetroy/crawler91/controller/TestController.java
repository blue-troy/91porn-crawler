package com.bluetroy.crawler91.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-11-13
 * Time: 9:40 PM
 */
@RestController
public class TestController {
    @RequestMapping("/test")
    void test() {
        System.out.println("test");
    }

    @RequestMapping("/testerror")
    void error() throws Exception {
        throw new Exception("spring boot exception");
    }
}
