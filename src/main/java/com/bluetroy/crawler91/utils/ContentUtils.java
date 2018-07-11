package com.bluetroy.crawler91.utils;

import com.bluetroy.crawler91.crawler.Scanner;
import com.bluetroy.crawler91.repository.pojo.KeyContent;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;

import static com.bluetroy.crawler91.repository.Repository.FILTERED_MOVIES;
import static com.bluetroy.crawler91.repository.Repository.MOVIE_DATA;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-07-11
 * Time: 下午3:46
 */
public class ContentUtils {
    private static KeyContent getKeyContentMap(String key) {
        return new KeyContent(key, HttpRequester.get(MOVIE_DATA.get(key).getDetailURL()));
    }

    public static LinkedBlockingDeque<KeyContent> getDetailContents() {
        LinkedBlockingDeque<KeyContent> contentQueue = new LinkedBlockingDeque<>();
        FILTERED_MOVIES.forEach(5, (k, v) -> {
            if (v) {
                return;
            }
            contentQueue.offer(getKeyContentMap(k));
        });
        return contentQueue;
    }

    public static LinkedBlockingDeque<Future<String>> getMovieContents() {
        LinkedBlockingDeque<Future<String>> contentQueue = new LinkedBlockingDeque<>();
        for (String url : Scanner.getUrlsForScan()) {
            contentQueue.offer(HttpRequester.get(url));
        }
        return contentQueue;
    }
}

