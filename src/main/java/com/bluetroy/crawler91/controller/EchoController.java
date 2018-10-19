package com.bluetroy.crawler91.controller;

import com.bluetroy.crawler91.crawler.dao.entity.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-10-11
 * Time: 4:51 PM
 */
@RestController
@RequestMapping("/echo")
public class EchoController {
    @RequestMapping("/fuck")
    public String fuck() {
        String s = "fuck";
        test();
        return s;
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
