package club.bluetroy.crawler91.web.controller;

import club.bluetroy.crawler.Crawler;
import club.bluetroy.crawler.vo.FilterVO;
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
