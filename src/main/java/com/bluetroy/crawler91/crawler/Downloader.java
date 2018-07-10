package com.bluetroy.crawler91.crawler;

import com.bluetroy.crawler91.repository.pojo.Movie;
import com.bluetroy.crawler91.utils.HttpRequester;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.bluetroy.crawler91.repository.Repository.*;

/**
 * @author heyixin
 */
@Component
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
        while ((!isContinuousDownloadStart) && ((key = TO_DOWNLOAD_MOVIES.poll()) != null)) {
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
            setDownloadedMovies(key);
        } catch (IOException e) {
            setDownloadError(key);
            e.printStackTrace();
        }
    }
}
