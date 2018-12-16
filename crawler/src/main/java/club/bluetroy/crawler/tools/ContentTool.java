package club.bluetroy.crawler.tools;

import club.bluetroy.crawler.dao.entity.KeyContent;
import club.bluetroy.crawler.dao.entity.Movie;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

/**
 * Created with IntelliJ IDEA.
 * Description: content 为网页string
 *
 * @author: heyixin
 * Date: 2018-07-11
 * Time: 下午3:46
 */
@Component
public class ContentTool {
    /**
     * @param urlsForScan 需要扫描的页面
     * @return 返回拥有视频基本信息(不包括视频下载地址)的对象队列
     */
    public Queue<Future<String>> getContents(List<String> urlsForScan) {
        Queue<Future<String>> contentQueue = new LinkedList<>();
        urlsForScan.forEach(url -> contentQueue.offer(HttpClient.getInFuture(url)));
        return contentQueue;
    }


    /**
     * @param moviesData
     * @return 返回拥有详细视频信息的对象队列
     */
    public Queue<KeyContent> getDetailContent(ConcurrentHashMap<String, Movie> moviesData) {
        Queue<KeyContent> contentQueue = new LinkedList<>();
        moviesData.forEach((key, movie) -> contentQueue.offer(new KeyContent(key, HttpClient.getInFuture(movie.getDetailURL()))));
        return contentQueue;
    }

    public String getContent(Movie movie) throws Exception {
        return HttpClient.getNow(movie.getDetailURL());
    }
}