package com.bluetroy.crawler91.crawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-07-09
 * Time: 下午5:20
 */
@Component
public class Starter implements CommandLineRunner {
    @Autowired
    Scanner scanner;
    @Autowired
    Filter filter;
    @Autowired
    Downloader downloader;
    @Override
    public void run(String... args) throws Exception {
        scanner.scanMovies();
        filter.doFilter();
        scanner.setFilteredMoviesDownloadURL();
        downloader.downloadNow();
    }
}
