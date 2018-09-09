package com.bluetroy.crawler91.crawler;

import com.bluetroy.crawler91.dao.Repository;
import com.bluetroy.crawler91.dao.entity.KeyContent;
import com.bluetroy.crawler91.utils.ContentUtils;
import com.bluetroy.crawler91.utils.ScannerUtils;
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
        URLS_FOR_SCAN.add("http://91porn.com/v.php?category=hot&viewtype=basic");
    }

    @Autowired
    private ScannerUtils scannerUtils;
    @Autowired
    private Repository repository;
    @Autowired
    private ContentUtils contentUtils;

    public static List<String> getUrlsForScan() {
        return URLS_FOR_SCAN;
    }

    public void addUrlForScan(String url) {
        URLS_FOR_SCAN.add(url);
    }

    public void scanMovies() {
        scanMoviesByUrlList();
    }

    public void scanDownloadUrl() {
        scanFilteredMovieDownloadUrl();
    }

    private void scanFilteredMovieDownloadUrl() {
        LinkedBlockingDeque<KeyContent> keyContentQueue = contentUtils.getDetailContents();
        scannerUtils.scanDownloadUrls(keyContentQueue);
    }

    private void scanMoviesByUrlList() {
        LinkedBlockingDeque<Future<String>> movieContents = contentUtils.getMovieContents();
        scannerUtils.scanMovies(movieContents);
    }

    public Integer getCount() {
        return repository.getFilteredMovies().size();
    }
}
