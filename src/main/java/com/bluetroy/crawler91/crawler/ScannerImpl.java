package com.bluetroy.crawler91.crawler;

import com.bluetroy.crawler91.crawler.dao.BaseDao;
import com.bluetroy.crawler91.crawler.dao.MovieStatus;
import com.bluetroy.crawler91.crawler.dao.entity.Category;
import com.bluetroy.crawler91.crawler.dao.entity.KeyContent;
import com.bluetroy.crawler91.crawler.dao.entity.PornUrl;
import com.bluetroy.crawler91.crawler.tools.ContentTool;
import com.bluetroy.crawler91.crawler.tools.ScannerTool;
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
    public List<String> getScanUrls() {
        return URLS_FOR_SCAN;
    }
}
