package club.bluetroy.crawler;

import club.bluetroy.crawler.dao.BaseDao;
import club.bluetroy.crawler.dao.MovieStatus;
import club.bluetroy.crawler.dao.entity.KeyContent;
import club.bluetroy.crawler.tools.ContentTool;
import club.bluetroy.crawler.tools.ScannerTool;
import club.bluetroy.crawler.dao.entity.Category;
import club.bluetroy.crawler.dao.entity.PornUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Future;


/**
 * @author heyixin
 */
@Service("scanner")
class ScannerImpl implements Scanner {
    private static final List<String> URLS_FOR_SCAN = new ArrayList<>();
    @Autowired
    private ScannerTool scannerTool;
    @Autowired
    private ContentTool contentTool;
    @Autowired
    private BaseDao dao;

    static {
        URLS_FOR_SCAN.add(PornUrl.getUrl(Category.HOT));
    }

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
        Queue<KeyContent> keyContents = contentTool.getDetailContent(dao.getMovies(MovieStatus.FILTERED_MOVIES));
        scannerTool.scanDownloadUrls(keyContents);
    }

    @Override
    public String getDownloadUrl(String key) throws Exception {
        String html = contentTool.getContent(dao.getMovie(key));
        String downloadUrl = scannerTool.scanDownloadUrl(html);
        dao.addDownloadUrl(key, downloadUrl);
        return downloadUrl;
    }

    @Override
    public List<String> getScanUrls() {
        return URLS_FOR_SCAN;
    }
}
