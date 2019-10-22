package club.bluetroy.controller;

import club.bluetroy.service.ProjectService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author heyixin
 * Date: 2018-08-10
 * Time: 下午8:18
 */
//todo 重复提交问题

@RestController()
public class CrawlerController {
    private final ProjectService projectService;

    public CrawlerController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PatchMapping(value = "/start")
    void start() {
        projectService.process();
    }

    @PatchMapping("/shutdown")
    void shutdown() {
        projectService.shutdown();
    }
}
