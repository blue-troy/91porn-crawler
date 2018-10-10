package com.bluetroy.crawler91.controller;

import com.bluetroy.crawler91.crawler.Filter;
import com.bluetroy.crawler91.vo.FilterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    Filter filter;

    @PostMapping
    String setFilter(FilterVO filterVO) {
        filter.setFilter(filterVO);
        return "当前过滤器为" + filter.getFilterInfo();
    }

    @GetMapping
    String getFilterInfo() {
        return filter.getFilterInfo();
    }

}
