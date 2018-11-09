package com.bluetroy.crawler91.controller;

import com.bluetroy.crawler91.crawler.impl.aspect.StatisticsAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-08-17
 * Time: 下午2:38
 */
@RestController
@RequestMapping("/info")
public class InfoController {
    @Autowired
    private StatisticsAspect statisticsAspect;


    @GetMapping
    public String getInfo() throws IOException {
        statisticsAspect.gatherAllMoviesStatistics();
        return "success";
    }
}
