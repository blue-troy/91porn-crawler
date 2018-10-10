package com.bluetroy.crawler91.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-09-13
 * Time: 上午1:54
 */
@RestController
@RequestMapping("/echo")
public class EchoController {

    @RequestMapping()
    String echo() {
        return "hello world";
    }
}
