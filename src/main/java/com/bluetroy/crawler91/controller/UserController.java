package com.bluetroy.crawler91.controller;

import com.bluetroy.crawler91.crawler.UserAuthenticator;
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
    @RequestMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, @RequestParam("captcha_input") String captchaInput) {
        if (UserAuthenticator.login(username, password, captchaInput)) {
            return "登陆成功";
        } else {
            return "登陆失败";
        }
    }
}
