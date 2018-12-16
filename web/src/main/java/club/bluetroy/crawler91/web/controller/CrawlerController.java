package club.bluetroy.crawler91.web.controller;

import club.bluetroy.crawler91.web.service.ProjectService;
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
//todo 重复提交问题

@RestController()
public class CrawlerController {
    @Autowired
    private ProjectService projectService;

    @PatchMapping(value = "/start")
    void start() {
        projectService.process();
    }

    @PatchMapping("/shutdown")
    void shutdown() {
        projectService.shutdown();
    }
}
