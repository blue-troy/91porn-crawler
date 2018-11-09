package com.bluetroy.crawler91.controller;

import com.bluetroy.crawler91.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
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
    private ProjectService projectService;

    //todo 重复提交问题

    @PatchMapping(value = "/start")
    String start() {
        projectService.process();
        return "开启成功";
    }

    @PatchMapping("/shutdown")
    String shutdown() {
        projectService.shutdown();
        return "关闭成功";
    }
}
