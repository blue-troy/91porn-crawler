package com.bluetroy.crawler91.utils;

import com.bluetroy.crawler91.dao.entity.KeyContent;

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
        //todo 增加延时获取的功能，因为contentQueue中的内容会动态的增加
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
        //todo 增加延时获取的功能，因为contentQueue中的内容会动态的增加
        Future<String> future;
        while ((future = movieContents.poll()) != null) {
            if (future.isDone()) {
                setMovie(future);
            } else {
                movieContents.offer(future);
            }
        }
    }

    public static String scanLoginState(String loginResult) {
        return XpathUtils.getLoginState(loginResult);
    }
}
