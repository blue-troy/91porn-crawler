package com.bluetroy.crawler91.crawler;

import com.bluetroy.crawler91.repository.pojo.KeyContent;
import com.bluetroy.crawler91.repository.pojo.Movie;
import com.bluetroy.crawler91.utils.HttpRequester;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.SynchronousQueue;

import static com.bluetroy.crawler91.repository.Repository.*;

/**
 * @author heyixin
 */
@Component
public class Downloader {
    private static SynchronousQueue<KeyContent> downloadTask = new SynchronousQueue();
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
        while ((!isContinuousDownloadStart) && ((key = TO_DOWNLOAD_MOVIES.poll()) != null)) {
            downloadMovieByKey(key);
        }
        verifyDownloadTask();
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
                setDownloadedMovies(keyContent.getKey());
            } catch (InterruptedException | ExecutionException e) {
                setDownloadError(keyContent.getKey());
                e.printStackTrace();
            }
        }
    }

    private void verifyDownloadTask() {
        KeyContent keyContent;
        while ((keyContent = downloadTask.poll()) != null) {
            verifyProcess(keyContent);
        }
    }

    private void startContinuousDownload() throws InterruptedException {
        while (isContinuousDownloadStart) {
            String key = TO_DOWNLOAD_MOVIES.take();
            downloadProcessByKey(key);
        }
    }

    private KeyContent downloadMovieByKey(String key) {
        Movie movie = MOVIE_DATA.get(key);
        return new KeyContent(key, HttpRequester.download(movie.getDownloadURL(), movie.getFileName()));
    }
}
