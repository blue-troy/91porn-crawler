package club.bluetroy.crawler;

import club.bluetroy.crawler.dao.BaseDao;
import club.bluetroy.crawler.domain.MovieStatus;
import club.bluetroy.crawler.domain.Movie;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
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
public class DefaultStatistics implements Statistics {
    @Autowired
    private BaseDao dao;
    @Autowired
    private Adviser adviser;

    private HashMap<String, Movie> downloadingMovies = new HashMap<>();

    @Pointcut("execution(java.util.concurrent.Future club.bluetroy.crawler.Downloader.downloadByKey(String)) && args(key)")
    public void downloadPerformance(String key) {
    }

    @After("execution(void club.bluetroy.crawler.tool.Selector.getDownloadUrl(*))")
    public void gatherToDownloadMovies() throws Exception {
        sendToDownloadMovies();
    }

    private void sendToDownloadMovies() throws Exception {
        adviser.message("/toDownloadMovies", getData(MovieStatus.TO_DOWNLOAD));
    }

    private List<Movie> getData(MovieStatus movieStatus) {
        return dao.listMoviesByStatus(movieStatus);
    }

    @Around(value = "downloadPerformance(key)", argNames = "proceedingJoinPoint,key")
    public Object gatherDownloadingMovies(ProceedingJoinPoint proceedingJoinPoint, String key) throws Throwable {
        addDownloadingMovie(key);
        Future returnObject = (Future) proceedingJoinPoint.proceed();
        returnObject.get();
        removeDownloadingMovie(key);
        return returnObject;
    }

    private void addDownloadingMovie(String key) throws Exception {
        downloadingMovies.put(key, dao.getByKey(key));
        sendDownloadingMovies();
    }

    private void removeDownloadingMovie(String key) throws Exception {
        downloadingMovies.remove(key);
        sendDownloadingMovies();
    }

    private void sendDownloadingMovies() throws Exception {
        adviser.message("/downloadingMovies", downloadingMovies);
    }

    @After(value = "downloadPerformance(key))", argNames = "key")
    public void gatherDownloadResult(String key) throws Exception {
        sendDownloadResult();
    }

    private void sendDownloadResult() throws Exception {
        adviser.message("/downloadedMovies", getData(MovieStatus.DOWNLOADED));
        adviser.message("/downloadErrorMovies", getData(MovieStatus.DOWNLOAD_ERROR));
    }

    @Override
    public void gatherAllMoviesStatistics() throws Exception {
        gatherScannedMovies();
        gatherScannedMoviesCount();
        gatherFilteredMovies();
        sendToDownloadMovies();
        sendDownloadingMovies();
        sendDownloadResult();
    }

    @After("execution(void club.bluetroy.crawler.Scanner.scanMovies())")
    public void gatherScannedMovies() throws Exception {
        adviser.message("/scannedMovies", dao.listMoviesByStatus(MovieStatus.SCANNED));
    }

    /**
     * 特例测试：统计扫描视频的数量并通知webSocket通知前端
     */
    @After("execution(void club.bluetroy.crawler.Scanner.scanMovies())")
    public void gatherScannedMoviesCount() throws Exception {
        adviser.message("/scannedMovies/count", dao.countMovies());
    }

    @After("execution(void club.bluetroy.crawler.Filter.doFilter())")
    public void gatherFilteredMovies() throws Exception {
        adviser.message("/filteredMovies", getData(MovieStatus.FILTERED));
    }

}
