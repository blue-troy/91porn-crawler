package com.bluetroy.crawler91.crawler;

import com.bluetroy.crawler91.crawler.dao.BaseDao;
import com.bluetroy.crawler91.crawler.dao.entity.KeyContent;
import com.bluetroy.crawler91.crawler.tools.ContentTool;
import com.bluetroy.crawler91.crawler.tools.ScannerTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;


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

    static {
        URLS_FOR_SCAN.add("http://91porn.com/v.php?category=hot&viewtype=basic");
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
        LinkedBlockingDeque<Future<String>> movieContents = contentTool.getMovieContents();
        scannerTool.scanMovies(movieContents);
    }

    @Override
    public void scanDownloadUrl() {
        scanFilteredMovieDownloadUrl();
    }

    private void scanFilteredMovieDownloadUrl() {
        LinkedBlockingDeque<KeyContent> keyContentQueue = contentTool.getDetailContents();
        scannerTool.scanDownloadUrls(keyContentQueue);
    }


    @Override
    public List<String> getScanUrls() {
        return URLS_FOR_SCAN;
    }
}
