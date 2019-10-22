package club.bluetroy.controller;

import club.bluetroy.crawler.Filter;
import club.bluetroy.crawler.domain.FilterConfig;
import org.springframework.web.bind.annotation.*;

/**
 * @author heyixin
 * Date: 2018-08-12
 * Time: 下午3:56
 */
@RestController
@RequestMapping("/filter")
public class FilterController {
    private final Filter filter;

    public FilterController(Filter filter) {
        this.filter = filter;
    }

    @GetMapping
    String getFilterInfo() {
        return filter.getFilterInfo();
    }

    @PostMapping
    void setFilter(@RequestBody FilterConfig filterConfig) {
        filter.setFilter(filterConfig);
    }

}
