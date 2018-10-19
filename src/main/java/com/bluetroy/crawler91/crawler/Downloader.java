package com.bluetroy.crawler91.crawler;

import com.bluetroy.crawler91.crawler.dao.Repository;
import com.bluetroy.crawler91.crawler.dao.entity.KeyContent;
import com.bluetroy.crawler91.crawler.dao.entity.Movie;
import com.bluetroy.crawler91.crawler.tools.HttpTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.SynchronousQueue;

/**
 * @author heyixin
 */
@Component
public class Downloader {
    private static final SynchronousQueue<KeyContent> downloadTask = new SynchronousQueue();
    @Autowired
    Repository repository;
    private volatile boolean isContinuousDownloadStart = false;

    public void continuousDownload() {
        try {
            isContinuousDownloadStart = true;
            startContinuousDownload();
        } catch (InterruptedException e) {
            isContinuousDownloadStart = false;
            e.printStackTrace();
        }
    }

    public void downloadNow() {
        String key;
        while ((!isContinuousDownloadStart) && ((key = repository.getToDownloadMovies().poll()) != null)) {
            downloadMovieByKey(key);
        }
        verifyDownloadTask();
        repository.save();//保存一次数据 避免异常退出时没有保存
    }

    private void downloadProcessByKey(String key) {
        downloadTask.offer(downloadMovieByKey(key));
    }

    private void continuousVerifyDownloadTask() {
        while (isContinuousDownloadStart) {
            try {
                KeyContent keyContent = downloadTask.take();
                verifyProcess(keyContent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void verifyProcess(KeyContent keyContent) {
        if (keyContent.getContent().isDone()) {
            try {
                keyContent.getContent().get();
                repository.setDownloadedMovies(keyContent.getKey());
            } catch (InterruptedException | ExecutionException e) {
                repository.setDownloadError(keyContent.getKey());
                e.printStackTrace();
            }
        }
    }

    //todo 本来是private方法 由于spring aop获取不到 暂时改成public

    public void verifyDownloadTask() {
        KeyContent keyContent;
        while ((keyContent = downloadTask.poll()) != null) {
            verifyProcess(keyContent);
        }
    }

    private void startContinuousDownload() throws InterruptedException {
        while (isContinuousDownloadStart) {
            String key = repository.getToDownloadMovies().take();
            downloadProcessByKey(key);
        }
    }

    //todo 本来是private方法 由于spring aop获取不到 暂时改成public

    public KeyContent downloadMovieByKey(String key) {
        Movie movie = repository.getMovieData(key);
        return new KeyContent(key, HttpTool.download(movie.getDownloadURL(), movie.getFileName()));
    }

    public SynchronousQueue<KeyContent> getDownloadTask() {
        return downloadTask;
    }
}
