package com.bluetroy.crawler91.crawler;

import com.bluetroy.crawler91.crawler.dao.BaseDao;
import com.bluetroy.crawler91.crawler.utils.SegmentDownloader;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
@Service("downloader")
class DownloaderImpl implements Downloader {
    private static final ExecutorService DOWNLOAD_SERVICE;
    @Autowired
    private BaseDao dao;
    private volatile boolean isContinuousDownloadStart = false;

    static {
        DOWNLOAD_SERVICE = new ThreadPoolExecutor(0, 5, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadFactoryBuilder()
                .setNameFormat("DOWNLOAD-pool-%d").build(), new ThreadPoolExecutor.AbortPolicy());
    }

    @Override
    public void downloadNow() {
        String key;
        while ((!isContinuousDownloadStart) && ((key = dao.getToDownloadMovies().poll()) != null)) {
            ((Downloader) AopContext.currentProxy()).downloadByKey(key);
        }
    }

    @Override
    public synchronized void startContinuousDownload() throws InterruptedException {
        if (!isContinuousDownloadStart) {
            isContinuousDownloadStart = true;
            while (isContinuousDownloadStart) {
                String key = dao.getToDownloadMovies().take();
                ((Downloader) AopContext.currentProxy()).downloadByKey(key);
            }
        }
    }

    @Override
    public Future downloadByKey(String key) {
        return DOWNLOAD_SERVICE.submit(() -> {
            try {
                SegmentDownloader.download(getDownloadUrl(key), getFileName(key));
                dao.addDownloadedMovies(key);
            } catch (Exception e) {
                dao.addDownloadError(key);
                log.info("下载失败", e);
            }
        });
    }

    private String getDownloadUrl(String key) throws Exception {
        String downloadUrl = dao.getMovie(key).getDownloadURL();
        if (downloadUrl == null) {
            downloadUrl = crawler.getDownloadUrl(key);
        }
        return downloadUrl;
    }

    private String getFileName(String key) {
        return dao.getMovie(key).getFileName();
    }

    @Override
    public void setResource(Path path) {
        SegmentDownloader.setResourceFolder(path);
    }
}
