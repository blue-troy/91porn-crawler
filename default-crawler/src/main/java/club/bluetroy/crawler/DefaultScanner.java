package club.bluetroy.crawler;

import club.bluetroy.crawler.dao.BaseDao;
import club.bluetroy.crawler.dao.MovieStatus;
import club.bluetroy.crawler.dao.entity.Category;
import club.bluetroy.crawler.dao.entity.KeyContent;
import club.bluetroy.crawler.tool.ContentTool;
import club.bluetroy.crawler.tool.PornUrl;
import club.bluetroy.crawler.tool.ScannerTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Future;


/**
 * @author heyixin
 */
@Service
class DefaultScanner implements Scanner {
    private static final List<String> URLS_FOR_SCAN = new ArrayList<>();
    @Autowired
    private ScannerTool scannerTool;
    @Autowired
    private ContentTool contentTool;
    @Autowired
    private BaseDao dao;

    @Override
    public void addUrl(String url) {
        URLS_FOR_SCAN.add(url);
    }

    /**
     * 扫描 urlList中页面的视频，当扫描完毕的时候返回，同步方法
     */
    @Override
    public void scanMovies() {
        Queue<Future<String>> contents = contentTool.getContents(URLS_FOR_SCAN);
        scannerTool.scanMovies(contents);
    }

    @Override
    public void scanDownloadUrl() {
        scanFilteredMovieDownloadUrl();
    }

    private void scanFilteredMovieDownloadUrl() {
        Queue<KeyContent> keyContents = contentTool.getDetailContent(dao.listMovies(MovieStatus.FILTERED_MOVIES));
        scannerTool.scanDownloadUrls(keyContents);
    }

    @Override
    public String getDownloadUrl(String key) throws Exception {
        String html = contentTool.getContent(dao.getMovie(key));
        String downloadUrl = scannerTool.scanDownloadUrl(html);
        dao.saveDownloadUrl(key, downloadUrl);
        return downloadUrl;
    }

    @Override
    public List<String> getScanUrls() {
        return URLS_FOR_SCAN;
    }
}
