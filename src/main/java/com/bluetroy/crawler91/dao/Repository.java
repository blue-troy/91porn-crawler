package com.bluetroy.crawler91.dao;

import com.bluetroy.crawler91.dao.entity.DownloadErrorInfo;
import com.bluetroy.crawler91.dao.entity.Movie;
import com.bluetroy.crawler91.utils.TimeUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author heyixin
 * todo 闭包
 */
@Log4j2
@Component
public class Repository implements Serializable {
    /**
     * 扫描下来的 movie 信息
     * key:movie的key
     * boolean:记录是否被filtered
     */
    private static final ConcurrentHashMap<String, Boolean> SCANNED_MOVIES = new ConcurrentHashMap<>();
    private static final LinkedBlockingDeque<String> TO_DOWNLOAD_MOVIES = new LinkedBlockingDeque<>();
    private static final ConcurrentHashMap<String, Boolean> FILTERED_MOVIES = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Movie> MOVIE_DATA = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, String> DOWNLOADED_MOVIES = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, DownloadErrorInfo> DOWNLOAD_ERROR = new ConcurrentHashMap<>();
    private static final String SCANNED_MOVIES_FILE = "SCANNED_MOVIES.dat";
    private static final String TO_DOWNLOAD_MOVIES_FILE = "TO_DOWNLOAD_MOVIES.dat";
    private static final String FILTERED_MOVIES_FILE = "FILTERED_MOVIES.dat";
    private static final String MOVIE_DATA_FILE = "MOVIE_DATA.dat";
    private static final String DOWNLOADED_MOVIES_FILE = "DOWNLOADED_MOVIES.dat";
    private static final String DOWNLOAD_ERROR_FILE = "DOWNLOAD_ERROR.dat";

    public static ConcurrentHashMap<String, Boolean> getScannedMovies() {
        return SCANNED_MOVIES;
    }

    public static LinkedBlockingDeque<String> getToDownloadMovies() {
        return TO_DOWNLOAD_MOVIES;
    }

    public static ConcurrentHashMap<String, Boolean> getFilteredMovies() {
        return FILTERED_MOVIES;
    }

    public static ConcurrentHashMap<String, Movie> getMovieData() {
        return MOVIE_DATA;
    }

    public static ConcurrentHashMap<String, String> getDownloadedMovies() {
        return DOWNLOADED_MOVIES;
    }

    public static void setDownloadedMovies(String key) {
        DOWNLOADED_MOVIES.putIfAbsent(key, TimeUtils.getDate());
    }

    public static ConcurrentHashMap<String, DownloadErrorInfo> getDownloadError() {
        return DOWNLOAD_ERROR;
    }

    public static void setDownloadError(String key) {
        if (DOWNLOAD_ERROR.containsKey(key)) {
            DOWNLOAD_ERROR.get(key).update();
        } else {
            DOWNLOAD_ERROR.putIfAbsent(key, new DownloadErrorInfo(key));
        }
    }

    public static void addFilteredMovies(ConcurrentHashMap<String, Boolean> filteredMovies) {
        FILTERED_MOVIES.putAll(filteredMovies);
    }

    public static ConcurrentHashMap<String, Boolean> getTobeFilter() {
        //todo 根据 SCANNED_MOVIES 设置初始值
        ConcurrentHashMap<String, Boolean> tobeFilter = new ConcurrentHashMap<>(16);
        Repository.getScannedMovies().forEach(5, (k, v) -> {
            if (v) {
                return;
            }
            tobeFilter.put(k, false);
            SCANNED_MOVIES.replace(k, true);
        });
        return tobeFilter;
    }

    public static void addToDownloadMoviesByKey(String k) {
        TO_DOWNLOAD_MOVIES.offer(k);
        FILTERED_MOVIES.replace(k, true);
    }

    public static void init() {
        System.out.println("容器加载完毕， 要把数据从数据库中加载出来");
        if (dataFilesExists()) {
            initData();
        } else {
            log.info("文件不存在");
        }
    }

    @PreDestroy
    public static void save() {
        System.out.println("马上要被销毁了 要把数据存下来");
        try (
                ObjectOutputStream scannedMoviesOutputStream = new ObjectOutputStream(new FileOutputStream("SCANNED_MOVIES.dat"));
                ObjectOutputStream toDownloadMoviesOutputStream = new ObjectOutputStream(new FileOutputStream("TO_DOWNLOAD_MOVIES.dat"));
                ObjectOutputStream filteredMoviesOutputStream = new ObjectOutputStream(new FileOutputStream("FILTERED_MOVIES.dat"));
                ObjectOutputStream movieDataOutputStream = new ObjectOutputStream(new FileOutputStream("MOVIE_DATA.dat"));
                ObjectOutputStream downloadedMoviesOutputStream = new ObjectOutputStream(new FileOutputStream("DOWNLOADED_MOVIES.dat"));
                ObjectOutputStream downloadErrorOutputStream = new ObjectOutputStream(new FileOutputStream("DOWNLOAD_ERROR.dat"))
        ) {
            Repository.writeObject(scannedMoviesOutputStream, toDownloadMoviesOutputStream, filteredMoviesOutputStream, movieDataOutputStream, downloadedMoviesOutputStream, downloadErrorOutputStream);
        } catch (IOException e) {
            log.warn("数据保存失败", e);
        }
    }

    /**
     * 设置扫描到的视频
     * 若视频曾经被扫描过，且有所不同则应该去更新视频的信息
     * 没有不同就算了。
     * 若不被扫描过，则应当去增加视频的信息。
     *
     * @param movie
     */
    public static void setScannedMovie(Movie movie) {
        if (Repository.SCANNED_MOVIES.containsKey(movie.getKey())) {
            if (movie.compareTo(MOVIE_DATA.get(movie.getKey())) != 0) {
                Repository.MOVIE_DATA.get(movie.getKey()).update(movie);
                Repository.SCANNED_MOVIES.replace(movie.getKey(), false);
                Repository.MOVIE_DATA.get(movie.getKey()).update(movie);
            }
        } else {
            Repository.SCANNED_MOVIES.putIfAbsent(movie.getKey(), false);
            Repository.MOVIE_DATA.put(movie.getKey(), movie);
        }
    }

    private static void writeObject(ObjectOutputStream scannedMoviesOutputStream, ObjectOutputStream toDownloadMoviesOutputStream, ObjectOutputStream filteredMoviesOutputStream, ObjectOutputStream movieDataOutputStream, ObjectOutputStream downloadedMoviesOutputStream, ObjectOutputStream downloadErrorOutputStream) throws IOException {
        scannedMoviesOutputStream.writeObject(SCANNED_MOVIES);
        toDownloadMoviesOutputStream.writeObject(TO_DOWNLOAD_MOVIES);
        filteredMoviesOutputStream.writeObject(FILTERED_MOVIES);
        movieDataOutputStream.writeObject(MOVIE_DATA);
        downloadedMoviesOutputStream.writeObject(DOWNLOADED_MOVIES);
        downloadErrorOutputStream.writeObject(DOWNLOAD_ERROR);
        scannedMoviesOutputStream.flush();
        toDownloadMoviesOutputStream.flush();
        filteredMoviesOutputStream.flush();
        movieDataOutputStream.flush();
        downloadedMoviesOutputStream.flush();
        downloadErrorOutputStream.flush();
    }

    private static void init(ConcurrentHashMap<String, Boolean> scannedMovies, LinkedBlockingDeque<String> toDownloadMovies, ConcurrentHashMap<String, Boolean> filteredMovies, ConcurrentHashMap<String, Movie> movieData, ConcurrentHashMap<String, String> downloadedMovies, ConcurrentHashMap<String, DownloadErrorInfo> downloadError) {
        SCANNED_MOVIES.putAll(scannedMovies);
        TO_DOWNLOAD_MOVIES.addAll(toDownloadMovies);
        FILTERED_MOVIES.putAll(filteredMovies);
        MOVIE_DATA.putAll(movieData);
        DOWNLOADED_MOVIES.putAll(downloadedMovies);
        DOWNLOAD_ERROR.putAll(downloadError);
    }

    private static boolean dataFilesExists() {
        return Files.exists(Paths.get(SCANNED_MOVIES_FILE))
                && Files.exists(Paths.get(TO_DOWNLOAD_MOVIES_FILE))
                && Files.exists(Paths.get(FILTERED_MOVIES_FILE))
                && Files.exists(Paths.get(MOVIE_DATA_FILE))
                && Files.exists(Paths.get(DOWNLOADED_MOVIES_FILE))
                && Files.exists(Paths.get(DOWNLOAD_ERROR_FILE));
    }

    private static void initData() {
        try (ObjectInputStream scannedMoviesInputStream = new ObjectInputStream(new FileInputStream(SCANNED_MOVIES_FILE));
             ObjectInputStream toDownloadMoviesInputStream = new ObjectInputStream(new FileInputStream(TO_DOWNLOAD_MOVIES_FILE));
             ObjectInputStream filteredMoviesInputStream = new ObjectInputStream(new FileInputStream(FILTERED_MOVIES_FILE));
             ObjectInputStream movieDataInputStream = new ObjectInputStream(new FileInputStream(MOVIE_DATA_FILE));
             ObjectInputStream downloadedMoviesInputStream = new ObjectInputStream(new FileInputStream(DOWNLOADED_MOVIES_FILE));
             ObjectInputStream downloadErrorInputStream = new ObjectInputStream(new FileInputStream(DOWNLOAD_ERROR_FILE))
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
