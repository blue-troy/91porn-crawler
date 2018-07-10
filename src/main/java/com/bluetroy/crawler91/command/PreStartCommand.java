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

    @Override
    public void run(String... args) throws Exception {
        System.out.println("容器加载完毕， 要把数据从数据库中加载出来");
        try (ObjectInputStream scannedMoviesInputStream = new ObjectInputStream(new FileInputStream("SCANNED_MOVIES.dat"));
             ObjectInputStream toDownloadMoviesInputStream = new ObjectInputStream(new FileInputStream("TO_DOWNLOAD_MOVIES.dat"));
             ObjectInputStream filteredMoviesInputStream = new ObjectInputStream(new FileInputStream("FILTERED_MOVIES.dat"));
             ObjectInputStream movieDataInputStream = new ObjectInputStream(new FileInputStream("MOVIE_DATA.dat"));
             ObjectInputStream downloadedMoviesInputStream = new ObjectInputStream(new FileInputStream("DOWNLOADED_MOVIES.dat"));
             ObjectInputStream downloadErrorInputStream = new ObjectInputStream(new FileInputStream("DOWNLOAD_ERROR.dat"));
        ) {
            ConcurrentHashMap<String, Boolean> scannedMovies = (ConcurrentHashMap<String, Boolean>) scannedMoviesInputStream.readObject();
            LinkedBlockingDeque<String> toDownloadMovies = (LinkedBlockingDeque<String>) toDownloadMoviesInputStream.readObject();
            ConcurrentHashMap<String, Boolean> filteredMovies = (ConcurrentHashMap<String, Boolean>) filteredMoviesInputStream.readObject();
            ConcurrentHashMap<String, Movie> movieData = (ConcurrentHashMap<String, Movie>) movieDataInputStream.readObject();
            ConcurrentHashMap<String, String> downloadedMovies = (ConcurrentHashMap<String, String>) downloadedMoviesInputStream.readObject();
            ConcurrentHashMap<String, DownloadErrorInfo> downloadError = (ConcurrentHashMap<String, DownloadErrorInfo>) downloadErrorInputStream.readObject();
            Repository.init(scannedMovies, toDownloadMovies, filteredMovies, movieData, downloadedMovies, downloadError);
        } catch (IOException e) {
            log.warn("数据加载失败");
            e.printStackTrace();
        }
    }
}
