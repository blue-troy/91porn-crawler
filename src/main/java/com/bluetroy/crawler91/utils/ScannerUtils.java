package com.bluetroy.crawler91.utils;

import com.bluetroy.crawler91.repository.pojo.KeyContent;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;

import static com.bluetroy.crawler91.utils.XpathUtils.setMovie;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-07-11
 * Time: 下午3:49
 */
public class ScannerUtils {
    public static void scanDownloadUrls(LinkedBlockingDeque<KeyContent> keyContentQueue) {
        KeyContent keyContent;
        while ((keyContent = keyContentQueue.poll()) != null) {
            if (keyContent.getContent().isDone()) {
                XpathUtils.scanDownloadUrl(keyContent);
            } else {
                keyContentQueue.offer(keyContent);
            }
        }
    }

    public static void scanMovies(LinkedBlockingDeque<Future<String>> movieContents) {
        Future<String> future;
        while ((future = movieContents.poll()) != null) {
            if (future.isDone()) {
                setMovie(future);
            } else {
                movieContents.offer(future);
            }
        }
    }
}
