package club.bluetroy.crawler.dao;

import club.bluetroy.crawler.dao.entity.DownloadErrorInfo;
import club.bluetroy.crawler.domain.Movie;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-11-09
 * Time: 5:05 PM
 */
public interface BaseDao {
    /**
     * 获取某个状态的视频的哈希表形式
     *
     * @param movieStatus 视频状态
     * @return 该视频状态的视频表
     */
    ConcurrentHashMap<String, Movie> listMovies(MovieStatus movieStatus);

    /**
     * 用视频key获取视频对象
     *
     * @param key 视频key
     * @return 视频对象
     */
    Movie getMovie(String key);

    /**
     * 添加通过过滤的视频，并维护仓库关系
     *
     * @param filteredMovies 通过过滤的视频key队列
     */
    void saveFilteredMoviesByKeys(Queue<String> filteredMovies);


    /**
     * 为视频对象添加下载地址，并维护仓库关系
     *
     * @param key         视频key
     * @param downloadUrl 视频下载地址
     */
    void saveDownloadUrl(String key, String downloadUrl);

    /**
     * 添加被扫描下的视频信息列表，并维护仓库关系
     *
     * @param movies 扫描到的视频列表
     */
    void saveScannedMovies(List<Movie> movies);

    /**
     * 添加被扫描下的视频信息列表，并维护仓库关系
     *
     * @param movie 扫描到的视频
     */
    void saveScannedMovie(Movie movie);

    /**
     * 添加下载错误信息，并维护仓库关系
     *
     * @param key 视频key
     */
    void saveDownloadError(String key);

    /**
     * 添加成功下载视频，并维护仓库关系
     *
     * @param key 视频key
     */
    void saveDownloadedMovies(String key);

    /**
     * 获取扫描到的视频数量
     *
     * @return 扫描到的视频数量
     */
    int countScannedMovies();


    /**
     * 获取下载错误视频信息表
     *
     * @return 下载错误视频信息表
     */
    ConcurrentHashMap<String, DownloadErrorInfo> getDownloadError();
}
