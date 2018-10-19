package com.bluetroy.crawler91.aspect;

import com.bluetroy.crawler91.controller.WebSocketController;
import com.bluetroy.crawler91.crawler.Downloader;
import com.bluetroy.crawler91.crawler.dao.MovieStatus;
import com.bluetroy.crawler91.crawler.dao.Repository;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * Description: 统计包括：扫描视频的数量，filter 结果，download结果 的数据，并通知webSocket通知前端口
 *
 * @author: heyixin
 * Date: 2018-10-13
 * Time: 1:41 AM
 */
@Aspect
@Component
public class StatisticsAspect {
    @Autowired
    Repository repository;
    @Autowired
    Downloader downloader;
    @Autowired
    WebSocketController webSocketController;

    //todo 应该去监控downloadTask才是真正的下载中
    @Pointcut("execution(* com.bluetroy.crawler91.crawler.Downloader.downloadMovieByKey())")
    public void downloadPerformance() {

    }

    /**
     * 特例测试：统计扫描视频的数量并通知webSocket通知前端
     */
    @After("execution(* com.bluetroy.crawler91.crawler.Scanner.scanMovies())")
    public void gatherScannedMoviesCount() throws IOException {
        webSocketController.send("/scannedMovies/count", repository.getScannedMovies().size());
    }

    @After("execution(* com.bluetroy.crawler91.crawler.Filter.doFilter())")
    public void gatherFilteredMovies() throws IOException {
        webSocketController.send("/filteredMovies", getData(MovieStatus.FILTERED_MOVIES));
    }

    @After("downloadPerformance()")
    public void gatherToDownloadMovies() throws IOException {
        webSocketController.send("/downloadingMovies", getData(MovieStatus.TO_DOWNLOAD_MOVIES));
    }

    @After("downloadPerformance()")
    public void gatherDownloadingMovies() throws IOException {
        webSocketController.send("/downloadingMovies", getDownloadingMovies());
    }

    @After("execution(* com.bluetroy.crawler91.crawler.Downloader.verifyDownloadTask())")
    public void gatherDownloadResult() throws IOException {
        webSocketController.send("downloadedMovies", getData(MovieStatus.DOWNLOADED_MOVIES));
        webSocketController.send("downloadErrorMovies", getData(MovieStatus.DOWNLOAD_ERROR));
    }

    private HashMap getData(MovieStatus movieStatus) {
        return repository.getMoviesData(movieStatus);
    }

    public void gatherAllMoviesStatistics() throws IOException {
        gatherScannedMoviesCount();
        gatherFilteredMovies();
        gatherToDownloadMovies();
        gatherDownloadResult();
    }

    private HashMap getDownloadingMovies() {
        HashMap hashMap = new HashMap();
        downloader.getDownloadTask().forEach(keyContent -> hashMap.put(keyContent.getKey(), repository.getMovieData().get(keyContent.getKey())));
        return hashMap;
    }
}
