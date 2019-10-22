package club.bluetroy.service;

import club.bluetroy.crawler.Downloader;
import club.bluetroy.crawler.Filter;
import club.bluetroy.crawler.Scanner;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @author heyixin
 * Date: 2018-07-12
 * Time: 下午1:49
 */
@Component
@Order(2)
public class ProjectService implements CommandLineRunner {
    private ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("project-pool-%d").build();
    private final Scanner scanner;
    private final Filter filter;
    private final Downloader downloader;
    private ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    @Autowired
    public ProjectService(Scanner scanner, Filter filter, Downloader downloader) {
        this.scanner = scanner;
        this.filter = filter;
        this.downloader = downloader;
    }

    public void shutdown() {
        singleThreadPool.shutdown();
        System.exit(0);
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void process() {
        singleThreadPool.submit((Runnable) this::run);
    }

    private void run() {
        System.out.println("开始执行操作");
        scanner.scanMovies();
        filter.doFilter();
        scanner.scanDownloadUrl();
        downloader.downloadNow();
    }

    @Override
    public void run(String... args) throws Exception {
//        process();
    }
}
