package club.bluetroy.crawler;

import club.bluetroy.crawler.dao.BaseDao;
import club.bluetroy.crawler.domain.MovieStatus;
import club.bluetroy.crawler.util.SegmentDownloader;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.concurrent.*;

/**
 * @author heyixin
 * description : downloadNow采用多线程下载，一次把ToDownloadMovies下载完毕并验证
 * ContinuousDownload 持续下载，单线程发出下载指令，监听ToDownloadMovies，一旦有需要下载的视频即刻下载，下载完成后验证
 */
@Slf4j
@Service
class DefaultDownloader implements Downloader {
    private static final ExecutorService DOWNLOAD_SERVICE;
    private final BaseDao dao;
    private final Scanner scanner;

    static {
        DOWNLOAD_SERVICE = new ThreadPoolExecutor(0, 5, 60L, TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadFactoryBuilder()
                .setNameFormat("DOWNLOAD-pool-%d").build(), new ThreadPoolExecutor.AbortPolicy());
    }

    @Autowired
    public DefaultDownloader(BaseDao dao, Scanner scanner) {
        this.dao = dao;
        this.scanner = scanner;
    }

    @Override
    public void downloadNow() {

        dao.listMoviesByStatus(MovieStatus.TO_DOWNLOAD)
                .forEach(
                        movie -> ((Downloader) AopContext.currentProxy()).downloadByKey(movie.getKey()));
    }


    @Override
    public Future downloadByKey(String key) {
        return DOWNLOAD_SERVICE.submit(() -> {
            try {
                SegmentDownloader.download(getDownloadUrl(key), getFileName(key));
                dao.saveDownloadedMovies(key);
            } catch (Exception e) {
                dao.saveDownloadError(key);
                log.info("下载失败", e);
            }
        });
    }

    private String getDownloadUrl(String key) throws Exception {
        String downloadUrl = dao.getByKey(key).getDownloadUrl();
        if (downloadUrl == null) {
            downloadUrl = scanner.getDownloadUrl(key);
        }
        return downloadUrl;
    }

    private String getFileName(String key) {
        return dao.getByKey(key).getFileName();
    }

    @Override
    public void setParallelTaskAndConcurrentThreads(Integer parallelTask, Integer concurrentThreads) {
        if (parallelTask != null) {
            if (concurrentThreads != null) {
                SegmentDownloader.setParallelTaskAndConcurrentThreads(parallelTask, concurrentThreads);
            } else {
                SegmentDownloader.setParallelTask(parallelTask);
            }
        } else {
            if (concurrentThreads != null) {
                SegmentDownloader.setConcurrentThreads(concurrentThreads);
            } else {
                throw new IllegalArgumentException();
            }
        }
        log.info("setParallelTask: {} ; ConcurrentThreads :{}", SegmentDownloader.getParallelTask(), SegmentDownloader.getConcurrentThreads());
    }

    @Override
    public void setResource(Path path) {
        SegmentDownloader.setResourceFolder(path);
        log.info("setResourceFolder : {}", SegmentDownloader.getResourceFolder());
    }
}
