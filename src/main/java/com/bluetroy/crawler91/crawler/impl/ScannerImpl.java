package com.bluetroy.crawler91.crawler.impl;

import com.bluetroy.crawler91.crawler.impl.dao.Repository;
import com.bluetroy.crawler91.crawler.impl.dao.entity.KeyContent;
import com.bluetroy.crawler91.crawler.Scanner;
import com.bluetroy.crawler91.crawler.impl.tools.ContentTool;
import com.bluetroy.crawler91.crawler.impl.tools.ScannerTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;


/**
 * @author heyixin
 */
@Service
public class ScannerImpl implements Scanner {
    private static final List<String> URLS_FOR_SCAN = new ArrayList<>();
    @Autowired
    private ScannerTool scannerTool;
    @Autowired
    private Repository repository;
    @Autowired
    private ContentTool contentTool;

    static {
        URLS_FOR_SCAN.add("http://94.91p14.space/v.php?category=hot&viewtype=basic");
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
        scanMoviesByUrlList();
    }

    @Override
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

    @Override
    public List<String> getScanUrls() {
        return URLS_FOR_SCAN;
    }
}
