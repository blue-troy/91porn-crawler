package com.bluetroy.crawler91.crawler;

import com.bluetroy.crawler91.crawler.dao.Repository;
import com.bluetroy.crawler91.crawler.dao.entity.KeyContent;
import com.bluetroy.crawler91.crawler.tools.ContentTool;
import com.bluetroy.crawler91.crawler.tools.ScannerTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;


/**
 * @author heyixin
 */
@Component
public class Scanner {
    private static final List<String> URLS_FOR_SCAN = new ArrayList<>();

    static {
        URLS_FOR_SCAN.add("http://94.91p14.space/v.php?category=hot&viewtype=basic");
    }

    @Autowired
    private ScannerTool scannerTool;
    @Autowired
    private Repository repository;
    @Autowired
    private ContentTool contentTool;

    public static List<String> getUrlsForScan() {
        return URLS_FOR_SCAN;
    }

    public void addUrlForScan(String url) {
        URLS_FOR_SCAN.add(url);
    }

    /**
     * 扫描 urlList中页面的视频，当扫描完毕的时候返回，同步方法
     */
    public void scanMovies() {
        scanMoviesByUrlList();
    }

    public void scanDownloadUrl() {
        scanFilteredMovieDownloadUrl();
    }

    private void scanFilteredMovieDownloadUrl() {
        LinkedBlockingDeque<KeyContent> keyContentQueue = contentTool.getDetailContents();
        scannerTool.scanDownloadUrls(keyContentQueue);
    }

    private void scanMoviesByUrlList() {
        LinkedBlockingDeque<Future<String>> movieContents = contentTool.getMovieContents();
        scannerTool.scanMovies(movieContents);
    }
}
