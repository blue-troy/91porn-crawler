package com.bluetroy.crawler91.command;

import com.bluetroy.crawler91.crawler.Downloader;
import com.bluetroy.crawler91.crawler.Filter;
import com.bluetroy.crawler91.crawler.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-07-09
 * Time: 下午7:49
 */
@Order(2)
@Component
public class StartCommand implements CommandLineRunner {
    @Autowired
    Scanner scanner;
    @Autowired
    Filter filter;
    @Autowired
    Downloader downloader;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("开始执行操作");
        scanner.scanMovies();
        filter.doFilter();
        scanner.scanDownloadUrl();
//        downloader.downloadNow();

    }
}
