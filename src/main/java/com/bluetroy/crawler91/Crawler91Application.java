package com.bluetroy.crawler91;

import com.bluetroy.crawler91.crawler.Downloader;
import com.bluetroy.crawler91.crawler.Filter;
import com.bluetroy.crawler91.crawler.Scanner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author heyixin
 */
@SpringBootApplication
public class Crawler91Application {

    CommandLineRunner commandLineRunner = new CommandLineRunner() {
        Scanner scanner = new Scanner();
        Filter filter = new Filter();
        Downloader downloader = new Downloader();

        @Override
        public void run(String... args) throws Exception {
            scanner.scanMovies();
            filter.doFilter();
            downloader.downloadNow();
        }
    };

    public static void main(String[] args) {
        SpringApplication.run(Crawler91Application.class, args);
    }
}
