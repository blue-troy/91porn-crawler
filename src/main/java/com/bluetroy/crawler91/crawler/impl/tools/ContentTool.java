package com.bluetroy.crawler91.crawler.impl.tools;

import com.bluetroy.crawler91.crawler.impl.ScannerImpl;
import com.bluetroy.crawler91.crawler.impl.dao.Repository;
import com.bluetroy.crawler91.crawler.impl.dao.entity.KeyContent;
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
    Repository repository;

    private KeyContent getKeyContentMap(String key) {
        return new KeyContent(key, HttpClient.getInFuture(repository.getMovieData().get(key).getDetailURL()));
    }

    /**
     * @return 返回拥有视频基本信息(不包括视频下载地址)的对象队列
     */
    public LinkedBlockingDeque<Future<String>> getMovieContents() {
        //todo 这边可以做成异步的，先返回contentQueue对象，对象中的的内容咱可以慢慢添加不是，这就要求处理contentQueue对象的方法需要有一定的等待功能
        LinkedBlockingDeque<Future<String>> contentQueue = new LinkedBlockingDeque<>();
        for (String url : ScannerImpl.getScanUrls()) {
            contentQueue.offer(HttpClient.getInFuture(url));
        }
        return contentQueue;
    }

    /**
     * @return 返回拥有详细视频信息的对象队列
     */
    public LinkedBlockingDeque<KeyContent> getDetailContents() {
        //todo 这边可以做成异步的，先返回contentQueue对象，对象中的的内容咱可以慢慢添加不是，这就要求处理contentQueue对象的方法需要有一定的等待功能
        LinkedBlockingDeque<KeyContent> contentQueue = new LinkedBlockingDeque<>();
        repository.getFilteredMovies().forEach(5, (k, v) -> {
            if (v) {
                return;
            }
            contentQueue.offer(getKeyContentMap(k));
        });
        return contentQueue;
    }
}