package com.bluetroy.crawler91.crawler;

import com.bluetroy.crawler91.repository.Movie;
import com.bluetroy.crawler91.utils.HttpRequester;

import java.io.IOException;

import static com.bluetroy.crawler91.repository.CrawlerList.*;

/**
 * @author heyixin
 */
public class Downloader {
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
        while ((!isContinuousDownloadStart) && ((key = TO_DOWNLOAD_MOVIES.peek()) != null)) {
            downloadProcessByKey(key);
        }
    }

    private void startContinuousDownload() throws InterruptedException {
        while (true) {
            String key = TO_DOWNLOAD_MOVIES.take();
            downloadProcessByKey(key);
        }
    }

    private void downloadMovieByKey(String key) throws IOException {
        Movie movie = MOVIE_DATA.get(key);
        HttpRequester.download(movie.getDownloadURL(), movie.getFileName());
    }

    private void downloadProcessByKey(String key) {
        try {
            downloadMovieByKey(key);
            setDownloadError(key);
        } catch (IOException e) {
            setDownloadedMovies(key);
            e.printStackTrace();
        }
    }
}
