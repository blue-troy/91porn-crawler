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
     * boolean:记录是否被过滤过了,过滤过了在不修改过滤器的情况下不应该再被过滤
     */
    ConcurrentHashMap<String, Boolean> scannedMovies;
    /**
     * 即将要下载的队列，队列内容为视频的key
     */
    LinkedBlockingDeque<String> toDownloadMovies;
    /**
     * 视频被过滤后加载到此，若已经加入下载队列则标记为true，未加入为false
     */
    ConcurrentHashMap<String, Boolean> filteredMovies;
    /**
     * 视频信息档案仓库，key为视频key，value为视频信息对象
     */
    ConcurrentHashMap<String, Movie> movieData;
    /**
     * 已经下载完毕的视频的信息，key为视频key，value为视频成功下载的时间
     */
    ConcurrentHashMap<String, String> downloadedMovies;
    /**
     * 下载错误的视频信息，key为视频key，value为下载错误信息
     */
    ConcurrentHashMap<String, DownloadErrorInfo> downloadError;
    @Autowired
    transient
    private Persistence persistence;

    @Override
    public void addFilteredMovies(Queue<String> filteredMovies) {
        filteredMovies.forEach(key -> this.filteredMovies.put(key, false));
    }


    @Override
    public ConcurrentHashMap<String, Movie> getMovies(MovieStatus movieStatus) {
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

    @Override
    public void initialize(Persistability persistentDao) {
        persistence.initialize(this);
    }

    @Override
    public void persistence(Persistability persistentDao) {
        persistence.persistence(this);
    }

    @Override
    public Movie getMovie(String key) {
        return movieData.get(key);
    }

    @Override
    public void addDownloadUrl(String key, String downloadUrl) {
        getMovie(key).setDownloadURL(downloadUrl);
        toDownloadMovies.offer(key);
        filteredMovies.replace(key, true);
    }

    @Override
    public void addScannedMovies(List<Movie> movies) {
        for (Movie movie : movies) {
            addScannedMovie(movie);
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
    @Override
    public void addScannedMovie(Movie movie) {
        if (scannedMovies.containsKey(movie.getKey())) {
            if (movie.compareTo(movieData.get(movie.getKey())) != 0) {
                movieData.get(movie.getKey()).update(movie);
                scannedMovies.replace(movie.getKey(), false);
            }
        } else {
            scannedMovies.putIfAbsent(movie.getKey(), false);
            movieData.put(movie.getKey(), movie);
        }
    }

    @Override
    public void resetFilteredStatus() {
        scannedMovies.entrySet().forEach(entry -> entry.setValue(false));
    }

    @Override
    public int scannedMovieCount() {
        return scannedMovies.size();
    }

    @Override
    public void addDownloadError(String key) {
        if (downloadError.containsKey(key)) {
            downloadError.get(key).update();
        } else {
            downloadError.putIfAbsent(key, new DownloadErrorInfo(key));
        }
    }

    @Override
    public void addDownloadedMovies(String key) {
        downloadedMovies.putIfAbsent(key, TimeUtils.getDate());
    }

    private ConcurrentHashMap<String, Movie> getDownloadedMovies() {
        ConcurrentHashMap<String, Movie> data = new ConcurrentHashMap<>();
        downloadedMovies.forEachKey(1, key -> data.put(key, getMovie(key)));
        return data;
    }

    private ConcurrentHashMap<String, Movie> getDownloadErrorMovies() {
        ConcurrentHashMap<String, Movie> data = new ConcurrentHashMap<>();
        downloadError.forEachKey(1, key -> data.put(key, getMovie(key)));
        return data;
    }

    private ConcurrentHashMap<String, Movie> getScannedMovies() {
        ConcurrentHashMap<String, Movie> tobeFilter = new ConcurrentHashMap<>(16);
        scannedMovies.forEach(1, (k, v) -> {
            if (!v) {
                tobeFilter.put(k, getMovie(k));
            }
        });
        return tobeFilter;
    }

    @Override
    public LinkedBlockingDeque<String> getToDownloadMovies() {
        return toDownloadMovies;
    }

    private ConcurrentHashMap<String, Movie> getFilteredMovies() {
        ConcurrentHashMap<String, Movie> concurrentHashMap = new ConcurrentHashMap<>();
        filteredMovies.forEachEntry(1, entry -> {
            if (!entry.getValue()) {
                concurrentHashMap.put(entry.getKey(), getMovie(entry.getKey()));
            }
        });
        return concurrentHashMap;
    }

    @Override
    public ConcurrentHashMap<String, DownloadErrorInfo> getDownloadError() {
        return downloadError;
    }
}
