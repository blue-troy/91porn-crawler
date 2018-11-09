package com.bluetroy.crawler91.service;

import com.bluetroy.crawler91.crawler.Crawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-07-12
 * Time: 下午1:49
 */
@Component
@Order(2)
public class ProjectService implements CommandLineRunner {
    @Autowired
    private Crawler crawler;

    public void shutdown() {
        new Thread(() -> {
            System.exit(0);
        }).start();
    }

    @Scheduled(cron = "0 0 */4 * * ?")
    public void process() {
        new Thread(() -> {
            System.out.println("开始执行操作");
            crawler.scanMovies();
            crawler.doFilter();
            crawler.scanDownloadUrl();
            crawler.downloadNow();
        }).start();
    }

    @Override
    public void run(String... args) throws Exception {
//        process();
    }
}
