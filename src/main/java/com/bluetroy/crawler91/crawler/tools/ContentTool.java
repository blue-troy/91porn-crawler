package com.bluetroy.crawler91.crawler.tools;

import com.bluetroy.crawler91.crawler.Crawler;
import com.bluetroy.crawler91.crawler.dao.BaseDao;
import com.bluetroy.crawler91.crawler.dao.entity.KeyContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;

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
    @Autowired
    private Crawler crawler;
    @Autowired
    private BaseDao dao;

    private KeyContent getKeyContentMap(String key) {
        return new KeyContent(key, HttpClient.getInFuture(dao.getMovieData().get(key).getDetailURL()));
    }

    /**
     * @return 返回拥有视频基本信息(不包括视频下载地址)的对象队列
     */
    public LinkedBlockingDeque<Future<String>> getMovieContents() {
        LinkedBlockingDeque<Future<String>> contentQueue = new LinkedBlockingDeque<>();
        for (String url : crawler.getScanUrls()) {
            contentQueue.offer(HttpClient.getInFuture(url));
        }
        return contentQueue;
    }

    /**
     * @return 返回拥有详细视频信息的对象队列
     */
    public LinkedBlockingDeque<KeyContent> getDetailContents() {
        LinkedBlockingDeque<KeyContent> contentQueue = new LinkedBlockingDeque<>();
        dao.getFilteredMovies().forEach(5, (k, v) -> {
            if (v) {
                return;
            }
            contentQueue.offer(getKeyContentMap(k));
        });
        return contentQueue;
    }
}