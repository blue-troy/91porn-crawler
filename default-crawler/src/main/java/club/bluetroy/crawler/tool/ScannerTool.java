package club.bluetroy.crawler.tool;

import club.bluetroy.crawler.dao.BaseDao;
import club.bluetroy.crawler.dao.entity.KeyContent;
import club.bluetroy.crawler.domain.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author heyixin
 * Date: 2018-07-11
 * Time: 下午3:49
 */
@Slf4j
@Component
public class ScannerTool {
    private Selector selector;
    private final BaseDao dao;

    @Autowired
    public ScannerTool(Selector selector, BaseDao dao) {
        this.selector = selector;
        this.dao = dao;
    }

    public void scanDownloadUrls(Queue<KeyContent> keyContents) {
        KeyContent keyContent;
        while ((keyContent = keyContents.poll()) != null) {
            if (keyContent.getContent().isDone()) {
                try {
                    String downloadUrl = selector.getDownloadUrl(keyContent.getContent().get());
                    dao.saveDownloadUrl(keyContent.getKey(), downloadUrl);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            } else {
                keyContents.offer(keyContent);
            }
        }
    }

    public void scanMovies(Queue<Future<String>> movieContents) {
        Future<String> html;
        while ((html = movieContents.poll()) != null) {
            if (html.isDone()) {
                try {
                    List<Movie> movies = selector.getMovies(html.get());
                    dao.saveScannedMovies(movies);
                } catch (ExecutionException | InterruptedException e) {
                    log.info("读取视频信息失败", e);
                }
            } else {
                movieContents.offer(html);
            }
        }
    }

    public String scanDownloadUrl(String html) {
        return selector.getDownloadUrl(html);
    }
}
