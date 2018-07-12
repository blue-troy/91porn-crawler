package com.bluetroy.crawler91.command;

import com.bluetroy.crawler91.crawler.Downloader;
import com.bluetroy.crawler91.crawler.Filter;
import com.bluetroy.crawler91.crawler.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ScanCommand {
    @Autowired
    Scanner scanner;
    @Autowired
    Filter filter;
    @Autowired
    Downloader downloader;

    @Scheduled(cron = "0 */4 * * * ?")
    public void process() {
        System.out.println("开始执行操作");
        scanner.scanMovies();
        filter.doFilter();
        scanner.scanDownloadUrl();
        downloader.downloadNow();
    }
}
