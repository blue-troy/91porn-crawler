package com.bluetroy.crawler91.runner;

import com.bluetroy.crawler91.crawler.Crawler;
import com.bluetroy.crawler91.http.HttpRequestor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
//        new Crawler().crawHotMovieList();
    }

}
