package com.bluetroy.crawler91.crawler.tools;

import com.bluetroy.crawler91.crawler.dao.BaseDao;
import com.bluetroy.crawler91.crawler.dao.entity.KeyContent;
import com.bluetroy.crawler91.crawler.dao.entity.Movie;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;
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
@Log4j2
@Component
public class ScannerTool {
    @Autowired
    private Selector selector;
    @Autowired
    private BaseDao dao;

    public void scanDownloadUrls(LinkedBlockingDeque<KeyContent> keyContentQueue) {
        KeyContent keyContent;
        while ((keyContent = keyContentQueue.poll()) != null) {
            if (keyContent.getContent().isDone()) {
                try {
                    String downloadUrl = selector.getDownloadUrl(keyContent.getContent().get());
                    dao.addDownloadUrl(keyContent.getKey(), downloadUrl);
                    dao.addToDownloadMoviesByKey(keyContent.getKey());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            } else {
                keyContentQueue.offer(keyContent);
            }
        }
    }

    public void scanMovies(LinkedBlockingDeque<Future<String>> movieContents) {
        Future<String> html;
        while ((html = movieContents.poll()) != null) {
            if (html.isDone()) {
                try {
                    List<Movie> movies = selector.getMovies(html.get());
                    dao.addScannedMovie(movies);
                } catch (ExecutionException | InterruptedException e) {
                    log.info("读取视频信息失败", e);
                }
            } else {
                movieContents.offer(html);
            }
        }
    }

}
