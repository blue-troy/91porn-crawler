package club.bluetroy.controller;

import club.bluetroy.crawler.Statistics;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author heyixin
 * Date: 2018-08-17
 * Time: 下午2:38
 */
@RestController
@RequestMapping("/info")
public class InfoController {
    private final Statistics statistics;

    public InfoController(Statistics statistics) {
        this.statistics = statistics;
    }


    @GetMapping
    public void getInfo() throws Exception {
        statistics.gatherAllMoviesStatistics();
    }
}
