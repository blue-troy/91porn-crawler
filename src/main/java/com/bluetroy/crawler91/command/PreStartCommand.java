package com.bluetroy.crawler91.command;

import com.bluetroy.crawler91.repository.Repository;
import com.bluetroy.crawler91.repository.pojo.DownloadErrorInfo;
import com.bluetroy.crawler91.repository.pojo.Movie;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-07-09
 * Time: 下午7:49
 */
@Component
@Order(1)
@Log4j2
public class PreStartCommand implements CommandLineRunner {
    private static final String SCANNED_MOVIES = "SCANNED_MOVIES.dat";
    private static final String TO_DOWNLOAD_MOVIES = "TO_DOWNLOAD_MOVIES.dat";
    private static final String FILTERED_MOVIES = "FILTERED_MOVIES.dat";
    private static final String MOVIE_DATA = "MOVIE_DATA.dat";
    private static final String DOWNLOADED_MOVIES = "DOWNLOADED_MOVIES.dat";
    private static final String DOWNLOAD_ERROR = "DOWNLOAD_ERROR.dat";

    @Override
    public void run(String... args) throws Exception {
        System.out.println("容器加载完毕， 要把数据从数据库中加载出来");
        if (dataFilesExists()) {
            initData();
        } else {
            log.info("文件不存在");
        }
    }

    private boolean dataFilesExists() {
        return Files.exists(Paths.get(SCANNED_MOVIES))
                && Files.exists(Paths.get(TO_DOWNLOAD_MOVIES))
                && Files.exists(Paths.get(FILTERED_MOVIES))
                && Files.exists(Paths.get(MOVIE_DATA))
                && Files.exists(Paths.get(DOWNLOADED_MOVIES))
                && Files.exists(Paths.get(DOWNLOAD_ERROR));
    }


    private void initData() {
        try (ObjectInputStream scannedMoviesInputStream = new ObjectInputStream(new FileInputStream(SCANNED_MOVIES));
             ObjectInputStream toDownloadMoviesInputStream = new ObjectInputStream(new FileInputStream(TO_DOWNLOAD_MOVIES));
             ObjectInputStream filteredMoviesInputStream = new ObjectInputStream(new FileInputStream(FILTERED_MOVIES));
             ObjectInputStream movieDataInputStream = new ObjectInputStream(new FileInputStream(MOVIE_DATA));
             ObjectInputStream downloadedMoviesInputStream = new ObjectInputStream(new FileInputStream(DOWNLOADED_MOVIES));
             ObjectInputStream downloadErrorInputStream = new ObjectInputStream(new FileInputStream(DOWNLOAD_ERROR));
        ) {
            ConcurrentHashMap<String, Boolean> scannedMovies = (ConcurrentHashMap<String, Boolean>) scannedMoviesInputStream.readObject();
            LinkedBlockingDeque<String> toDownloadMovies = (LinkedBlockingDeque<String>) toDownloadMoviesInputStream.readObject();
            ConcurrentHashMap<String, Boolean> filteredMovies = (ConcurrentHashMap<String, Boolean>) filteredMoviesInputStream.readObject();
            ConcurrentHashMap<String, Movie> movieData = (ConcurrentHashMap<String, Movie>) movieDataInputStream.readObject();
            ConcurrentHashMap<String, String> downloadedMovies = (ConcurrentHashMap<String, String>) downloadedMoviesInputStream.readObject();
            ConcurrentHashMap<String, DownloadErrorInfo> downloadError = (ConcurrentHashMap<String, DownloadErrorInfo>) downloadErrorInputStream.readObject();
            Repository.init(scannedMovies, toDownloadMovies, filteredMovies, movieData, downloadedMovies, downloadError);
        } catch (IOException | ClassNotFoundException e) {
            log.warn("数据加载失败", e);
        }
    }
}
