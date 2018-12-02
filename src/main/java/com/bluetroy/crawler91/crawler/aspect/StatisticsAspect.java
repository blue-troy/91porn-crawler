package com.bluetroy.crawler91.crawler.aspect;

import com.bluetroy.crawler91.crawler.Adviser;
import com.bluetroy.crawler91.crawler.dao.BaseDao;
import com.bluetroy.crawler91.crawler.dao.MovieStatus;
import com.bluetroy.crawler91.crawler.dao.entity.Movie;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

/**
 * Created with IntelliJ IDEA.
 * Description: 统计包括：扫描视频的数量，filterr 结果，download结果 的数据，并通知webSocket通知前端口
 *
 * @author: heyixin
 * Date: 2018-10-13
 * Time: 1:41 AM
 */
//todo 重构 一个类做了两件事情

@Aspect
@Component
@Log4j2
public class StatisticsAspect {
    @Autowired
    private BaseDao dao;
    @Autowired
    private Adviser adviser;

    private HashMap<String, Movie> downloadingMovies = new HashMap<>();

    @Pointcut("execution(java.util.concurrent.Future com.bluetroy.crawler91.crawler.Crawler.downloadByKey(String)) && args(key)")
    public void downloadPerformance(String key) {
    }

    /**
     * 特例测试：统计扫描视频的数量并通知webSocket通知前端
     */
    @After("execution(void com.bluetroy.crawler91.crawler.Crawler.scanMovies())")
    public void gatherScannedMoviesCount() throws Exception {
        adviser.message("/scannedMovies/count", dao.scannedMovieCount());
    }

    @After("execution(void com.bluetroy.crawler91.crawler.Crawler.scanMovies())")
    public void gatherScannedMovies() throws Exception {
        adviser.message("/scannedMovies", dao.getMovies(MovieStatus.SCANNED_MOVIES));
    }

    @After("execution(void com.bluetroy.crawler91.crawler.Crawler.doFilter())")
    public void gatherFilteredMovies() throws Exception {
        adviser.message("/filteredMovies", getData(MovieStatus.FILTERED_MOVIES));
    }

    @After("execution(void com.bluetroy.crawler91.crawler.tools.Selector.getDownloadUrl(*))")
    public void gatherToDownloadMovies() throws Exception {
        sendToDownloadMovies();
    }

    @Around(value = "downloadPerformance(key)", argNames = "proceedingJoinPoint,key")
    public Object gatherDownloadingMovies(ProceedingJoinPoint proceedingJoinPoint, String key) throws Throwable {
        addDownloadingMovie(key);
        Future returnObject = (Future) proceedingJoinPoint.proceed();
        returnObject.get();
        removeDownloadingMovie(key);
        return returnObject;
    }

    @After(value = "downloadPerformance(key))", argNames = "key")
    public void gatherDownloadResult(String key) throws Exception {
        sendDownloadResult();
    }

    public void gatherAllMoviesStatistics() throws Exception {
        gatherScannedMovies();
        gatherScannedMoviesCount();
        gatherFilteredMovies();
        sendToDownloadMovies();
        sendDownloadingMovies();
        sendDownloadResult();
    }

    private void sendDownloadResult() throws Exception {
        adviser.message("/downloadedMovies", getData(MovieStatus.DOWNLOADED_MOVIES));
        adviser.message("/downloadErrorMovies", getData(MovieStatus.DOWNLOAD_ERROR));
    }

    private void sendToDownloadMovies() throws Exception {
        adviser.message("/toDownloadMovies", getData(MovieStatus.TO_DOWNLOAD_MOVIES));
    }

    private void sendDownloadingMovies() throws Exception {
        adviser.message("/downloadingMovies", downloadingMovies);
    }

    private void addDownloadingMovie(String key) throws Exception {
        downloadingMovies.put(key, dao.getMovie(key));
        sendDownloadingMovies();
    }

    private void removeDownloadingMovie(String key) throws Exception {
        downloadingMovies.remove(key);
        sendDownloadingMovies();
    }

    private ConcurrentHashMap<String, Movie> getData(MovieStatus movieStatus) {
        return dao.getMovies(movieStatus);
    }

}
