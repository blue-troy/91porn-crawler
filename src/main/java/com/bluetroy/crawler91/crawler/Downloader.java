package com.bluetroy.crawler91.crawler;

import com.bluetroy.crawler91.repository.Movie;
import com.bluetroy.crawler91.utils.HttpRequestor;

import java.io.IOException;

import static com.bluetroy.crawler91.repository.CrawlerList.*;

/**
 * @author heyixin
 */
public class Downloader {
    private void downloadMovieByKey(String key) throws IOException {
        Movie movie = MOVIE_DATA.get(key);
        HttpRequestor.download(movie.getDownloadURL(), movie.getFileName());
    }

    public void continuousDownload() throws InterruptedException {
        while (true) {
            String key = TO_DOWNLOAD_MOVIES.take();
            downloadProcessByKey(key);
        }
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

    public void downloadNow() {
        String key;
        while ((key = TO_DOWNLOAD_MOVIES.peek()) != null) {
            downloadProcessByKey(key);
        }
    }
}
