package com.bluetroy.crawler91.crawler.tools;

import com.bluetroy.crawler91.crawler.dao.entity.KeyContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-07-11
 * Time: 下午3:49
 */
@Component
public class ScannerTool {
    @Autowired
    XpathTool xpathTool;

    public void scanDownloadUrls(LinkedBlockingDeque<KeyContent> keyContentQueue) {
        //todo 增加延时获取的功能，因为contentQueue中的内容会动态的增加
        KeyContent keyContent;
        while ((keyContent = keyContentQueue.poll()) != null) {
            if (keyContent.getContent().isDone()) {
                xpathTool.scanDownloadUrl(keyContent);
            } else {
                keyContentQueue.offer(keyContent);
            }
        }
    }

    public void scanMovies(LinkedBlockingDeque<Future<String>> movieContents) {
        //todo 增加延时获取的功能，因为contentQueue中的内容会动态的增加
        Future<String> future;
        while ((future = movieContents.poll()) != null) {
            if (future.isDone()) {
                xpathTool.setMovie(future);
            } else {
                movieContents.offer(future);
            }
        }
    }

}
