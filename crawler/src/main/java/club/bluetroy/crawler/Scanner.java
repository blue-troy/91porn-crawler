package club.bluetroy.crawler;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-11-09
 * Time: 3:03 PM
 */
public interface Scanner {
    /**
     * 扫描视频，不包括视频下载地址
     */
    void scanMovies();

    /**
     * 扫描视频详细信息，包括下载地址
     */
    void scanDownloadUrl();

    /**
     * 获取视频下载地址
     *
     * @param key 视频key
     * @return 视频的下载地址
     * @throws Exception 获取视频下载地址失败
     */
    String getDownloadUrl(String key) throws Exception;

    /**
     * 为scanner添加新的要扫描的地址
     *
     * @param url 要扫描的url地址
     */
    void addUrl(String url);

    /**
     * 获取scanner要扫描的地址
     *
     * @return 要扫描的地址
     */
    List<String> getScanUrls();
}
