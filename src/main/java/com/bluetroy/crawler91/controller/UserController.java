package com.bluetroy.crawler91.controller;

import com.bluetroy.crawler91.crawler.Crawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-07-21
 * Time: 下午5:40
 */

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private Crawler crawler;

    @RequestMapping("/login")
    public void login(@RequestParam String username, @RequestParam String password, @RequestParam("captcha_input") String captchaInput) {
        crawler.login(username, password, captchaInput);
    }
}
