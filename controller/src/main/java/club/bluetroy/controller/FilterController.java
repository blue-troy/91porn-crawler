package club.bluetroy.controller;

import club.bluetroy.crawler.Filter;
import club.bluetroy.crawler.domain.FilterConfig;
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
    private Filter filter;

    @GetMapping
    String getFilterInfo() {
        return filter.getFilterInfo();
    }

    @PostMapping
    void setFilter(@RequestBody FilterConfig crawlerVO) {
        filter.setFilter(crawlerVO);
    }

}
