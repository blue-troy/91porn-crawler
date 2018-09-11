package com.bluetroy.crawler91.service;

import com.bluetroy.crawler91.crawler.Downloader;
import com.bluetroy.crawler91.crawler.Filter;
import com.bluetroy.crawler91.crawler.Scanner;
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
    Scanner scanner;
    @Autowired
    Filter filter;
    @Autowired
    Downloader downloader;

    public void shutdown() {
        new Thread(() -> {
            System.exit(0);
        }).start();
    }

    @Scheduled(cron = "0 0 */4 * * ?")
    public void process() {
        new Thread(() -> {
            System.out.println("开始执行操作");
            scanner.scanMovies();
            filter.doFilter();
            scanner.scanDownloadUrl();
            downloader.downloadNow();
        }).start();
    }

    @Override
    public void run(String... args) throws Exception {
//        process();
    }
}
