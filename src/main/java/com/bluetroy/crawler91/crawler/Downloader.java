package com.bluetroy.crawler91.crawler;

import com.bluetroy.crawler91.utils.HttpRequestor;
import com.bluetroy.crawler91.repository.Movie;

import static com.bluetroy.crawler91.repository.CrawlerList.*;

public class Downloader {
    private boolean downloadMovie(String key) {
        Movie movie = MOVIE_DATA.get(key);
        if (movie == null) return false;
        return HttpRequestor.download(movie.getDownloadURL(), movie.getFileName());
    }

    private void down() throws InterruptedException {
        while (true) {
            String key = TO_DOWNLOAD_MOVIES.take();
            if (!downloadMovie(key)) setDownloadError(key);
            else setDownloadedMovies(key);
        }
    }
}
