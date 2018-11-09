package com.bluetroy.crawler91.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-10-11
 * Time: 4:51 PM
 */
@Log4j2
@RestController
@RequestMapping("/test")
public class EchoController {
    @RequestMapping("/fuck")
    public String fuck() {
        String s = "fuck";
        test();
        return s;
    }

    @RequestMapping("/cookie")
    public String setCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            System.out.println(cookies[0].getValue());
        }
        Cookie cookie = new Cookie("name", "blue");
        response.addCookie(cookie);
        return "success";
    }

    private void test() {

    }

    /**
     * springboot默认使用的json解析框架是jackson;
     *
     * @return
     */
    @RequestMapping("/getStudent")
    public Student getStudent() {
        Student student = new Student();
        student.setGender("女");
        student.setsName("莉香");
        student.setsId(1);
        return student;
    }
}

class Student {
    private Integer sId;
    private String sName;
    private String gender;

    public Integer getsId() {
        return sId;
    }

    void setsId(Integer sId) {
        this.sId = sId;
    }

    public String getsName() {
        return sName;
    }

    void setsName(String sName) {
        this.sName = sName;
    }

    public String getGender() {
        return gender;
    }

    void setGender(String gender) {
        this.gender = gender;
    }
}
