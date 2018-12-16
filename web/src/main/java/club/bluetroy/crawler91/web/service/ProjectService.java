package club.bluetroy.crawler91.web.service;

import club.bluetroy.crawler.Crawler;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

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
    private ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("project-pool-%d").build();
    private ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());


    @Autowired
    private Crawler crawler;

    public void shutdown() {
        singleThreadPool.shutdown();
        System.exit(0);
    }

    @Scheduled(cron = "0 0 */4 * * ?")
    public void process() {
        singleThreadPool.submit((Runnable) this::run);
    }

    private void run() {
        System.out.println("开始执行操作");
        crawler.scanMovies();
        crawler.doFilter();
        crawler.scanDownloadUrl();
        crawler.downloadNow();
    }

    @Override
    public void run(String... args) throws Exception {
//        process();
    }
}
