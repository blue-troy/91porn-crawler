package com.bluetroy.crawler91.service;

import com.bluetroy.crawler91.crawler.impl.DownloaderImpl;
import com.bluetroy.crawler91.crawler.impl.FilterImpl;
import com.bluetroy.crawler91.crawler.impl.ScannerImpl;
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
    ScannerImpl scannerImpl;
    @Autowired
    FilterImpl filter;
    @Autowired
    DownloaderImpl downloaderImpl;

    public void shutdown() {
        new Thread(() -> {
            System.exit(0);
        }).start();
    }

    @Scheduled(cron = "0 0 */4 * * ?")
    public void process() {
        new Thread(() -> {
            System.out.println("开始执行操作");
            scannerImpl.scanMovies();
            filter.doFilter();
            scannerImpl.scanDownloadUrl();
            downloaderImpl.downloadNow();
        }).start();
    }

    @Override
    public void run(String... args) throws Exception {
//        process();
    }
}
