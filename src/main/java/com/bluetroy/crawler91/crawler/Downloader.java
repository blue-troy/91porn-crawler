package com.bluetroy.crawler91.crawler;

import com.bluetroy.crawler91.crawler.dao.Repository;
import com.bluetroy.crawler91.crawler.tools.SegmentDownloader;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @author heyixin
 * description : downloadNow采用多线程下载，一次把ToDownloadMovies下载完毕并验证
 * ContinuousDownload 持续下载，单线程发出下载指令，监听ToDownloadMovies，一旦有需要下载的视频即刻下载，下载完成后验证
 */
@Component
@Log4j2
public class Downloader {
    private static final ExecutorService DOWNLOAD_SERVICE;
    @Autowired
    private Repository repository;
    private volatile boolean isContinuousDownloadStart = false;

    static {
        DOWNLOAD_SERVICE = new ThreadPoolExecutor(0, 5, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadFactoryBuilder()
                .setNameFormat("DOWNLOAD-pool-%d").build(), new ThreadPoolExecutor.AbortPolicy());
    }

    public void downloadNow() {
        String key;
        while ((!isContinuousDownloadStart) && ((key = repository.getToDownloadMovies().poll()) != null)) {
            ((Downloader) AopContext.currentProxy()).downloadByKey(key);
        }
        repository.save();//保存一次数据 避免异常退出时没有保存
    }

    public synchronized void startContinuousDownload() throws InterruptedException {
        if (!isContinuousDownloadStart) {
            isContinuousDownloadStart = true;
            while (isContinuousDownloadStart) {
                String key = repository.getToDownloadMovies().take();
                ((Downloader) AopContext.currentProxy()).downloadByKey(key);
            }
        }
    }

    public Future downloadByKey(String key) {
        return DOWNLOAD_SERVICE.submit(() -> {
            try {
                SegmentDownloader.download(getDownloadUrl(key));
                repository.setDownloadedMovies(key);
            } catch (Exception e) {
                repository.setDownloadError(key);
                log.info("下载失败", e);
            }
        });
    }

    private String getDownloadUrl(String key) {
        return repository.getMovieData(key).getDownloadURL();
    }
}
