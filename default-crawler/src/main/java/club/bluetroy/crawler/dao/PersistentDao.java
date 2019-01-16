package club.bluetroy.crawler.dao;

import club.bluetroy.crawler.dao.entity.DownloadErrorInfo;
import club.bluetroy.crawler.domain.Movie;
import club.bluetroy.crawler.util.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author heyixin
 * todo 闭包
 */
@Slf4j
@Repository
class PersistentDao implements BaseDao, Persistability {
    private static final long serialVersionUID = 707378050594103627L;
    /**
     * 扫描下来的 movie 信息
     * key:movie的key
     */
    ConcurrentHashMap<String, Movie> scannedMovies = new ConcurrentHashMap<>();
    /**
     * 即将要下载的队列，队列内容为视频的key
     */
    LinkedBlockingDeque<String> toDownloadMovies = new LinkedBlockingDeque<>();
    /**
     * 视频被过滤后加载到此
     */
    ConcurrentHashMap<String, Movie> filteredMovies = new ConcurrentHashMap<>();
    /**
     * 视频信息档案仓库，key为视频key，value为视频信息对象
     */
    ConcurrentHashMap<String, Movie> movieData = new ConcurrentHashMap<>();
    /**
     * 已经下载完毕的视频的信息，key为视频key，value为视频成功下载的时间
     */
    ConcurrentHashMap<String, String> downloadedMovies = new ConcurrentHashMap<>();
    /**
     * 下载错误的视频信息，key为视频key，value为下载错误信息
     */
    ConcurrentHashMap<String, DownloadErrorInfo> downloadError = new ConcurrentHashMap<>();
    @Autowired
    transient
    private Persistence persistence;

    @Override
    public void saveFilteredMoviesByKeys(Queue<String> filteredMovies) {
        filteredMovies.forEach(key -> {
            this.filteredMovies.put(key, movieData.get(key));
            this.scannedMovies.remove(key);
        });
    }

    @Override
    public ConcurrentHashMap<String, Movie> listMovies(MovieStatus movieStatus) {
        switch (movieStatus) {
            case TO_DOWNLOAD_MOVIES:
                ConcurrentHashMap<String, Movie> data = new ConcurrentHashMap<>();
                toDownloadMovies.forEach(k -> {
                    data.put(k, movieData.get(k));
                });
                return data;
            case SCANNED_MOVIES:
                return getScannedMovies();
            case FILTERED_MOVIES:
                return getFilteredMovies();
            case DOWNLOAD_ERROR:
                return getDownloadErrorMovies();
            case DOWNLOADED_MOVIES:
                return getDownloadedMovies();
            default:
                return (ConcurrentHashMap<String, Movie>) Collections.EMPTY_MAP;
        }
    }

    private ConcurrentHashMap<String, Movie> getScannedMovies() {
        return new ConcurrentHashMap<>(scannedMovies);
    }

    private ConcurrentHashMap<String, Movie> getFilteredMovies() {
        return new ConcurrentHashMap<>(filteredMovies);
    }

    private ConcurrentHashMap<String, Movie> getDownloadErrorMovies() {
        ConcurrentHashMap<String, Movie> data = new ConcurrentHashMap<>();
        downloadError.forEachKey(1, key -> data.put(key, getMovie(key)));
        return data;
    }

    private ConcurrentHashMap<String, Movie> getDownloadedMovies() {
        ConcurrentHashMap<String, Movie> data = new ConcurrentHashMap<>();
        downloadedMovies.forEachKey(1, key -> data.put(key, getMovie(key)));
        return data;
    }

    @Override
    public Movie getMovie(String key) {
        return movieData.get(key);
    }

    @Override
    public void saveDownloadUrl(String key, String downloadUrl) {
        getMovie(key).setDownloadURL(downloadUrl);
        toDownloadMovies.offer(key);
        filteredMovies.remove(key);
    }

    @Override
    public void saveScannedMovies(List<Movie> movies) {
        for (Movie movie : movies) {
            saveScannedMovie(movie);
        }
    }

    /**
     * 设置扫描到的视频
     * 若视频曾经被扫描过，且有所不同则应该去更新视频的信息
     * 没有不同就算了。
     * 若不被扫描过，则应当去增加视频的信息。
     *
     * @param newMovie movie
     */
    @Override
    public void saveScannedMovie(Movie newMovie) {
        String newMovieKey = newMovie.getKey();
        if (movieData.containsKey(newMovieKey)) {
            Movie movie = movieData.get(newMovieKey);
            if (isMovieUpdate(newMovie, movie)) {
                movie.update(newMovie);
                scannedMovies.put(movie.getKey(), movie);
            }
        } else {
            scannedMovies.put(newMovieKey, newMovie);
            movieData.put(newMovieKey, newMovie);
        }
    }

    private boolean isMovieUpdate(Movie newMovie, Movie movie) {
        return newMovie.compareTo(movie) != 0;
    }

    @Override
    public int countScannedMovies() {
        return movieData.size();
    }

    @Override
    public void saveDownloadError(String key) {
        toDownloadMovies.remove(key);
        if (downloadError.containsKey(key)) {
            downloadError.get(key).update();
        } else {
            downloadError.putIfAbsent(key, new DownloadErrorInfo(key));
        }
    }

    @Override
    public void saveDownloadedMovies(String key) {
        toDownloadMovies.remove(key);
        downloadedMovies.put(key, TimeUtils.getDate());
    }

    @Override
    public void initialize(Persistability persistentDao) {
        persistence.initialize(this);
    }

    @Override
    public void persistence(Persistability persistentDao) {
        persistence.persistence(this);
    }

    @Override
    public ConcurrentHashMap<String, DownloadErrorInfo> getDownloadError() {
        return downloadError;
    }
}
