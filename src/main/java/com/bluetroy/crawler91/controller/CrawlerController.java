package com.bluetroy.crawler91.controller;

import com.bluetroy.crawler91.command.ScanCommand;
import com.bluetroy.crawler91.command.ShutdownCommand;
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
    ScanCommand scanCommand;

    @RequestMapping("/start")
    public String start() {
        scanCommand.process();
        return "启动成功";
    }

    @RequestMapping("/shutdown")
    public String shutdown() {
        ShutdownCommand.process();
        return "关闭成功";
    }
}
