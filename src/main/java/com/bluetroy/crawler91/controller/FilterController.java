package com.bluetroy.crawler91.controller;

import com.bluetroy.crawler91.crawler.Crawler;
import com.bluetroy.crawler91.vo.FilterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-08-12
 * Time: 下午3:56
 */
@RestController
@RequestMapping("/filter")
public class FilterController {
    @Autowired
    private Crawler crawler;

    @GetMapping
    String getFilterInfo() {
        return crawler.getFilterInfo();
    }

    @PostMapping
    void setFilter(@RequestBody FilterVO crawlerVO) {
        crawler.setFilter(crawlerVO);
    }

}
