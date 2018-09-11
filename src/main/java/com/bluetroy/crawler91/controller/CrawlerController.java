package com.bluetroy.crawler91.controller;

import com.bluetroy.crawler91.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-08-10
 * Time: 下午8:18
 */
@RestController()
public class CrawlerController {
    @Autowired
    ProjectService projectService;

    @RequestMapping("/start")
    public String start() {
        projectService.process();
        return "启动成功";
    }

    @RequestMapping("/shutdown")
    public String shutdown() {
        projectService.shutdown();
        return "关闭成功";
    }
}
