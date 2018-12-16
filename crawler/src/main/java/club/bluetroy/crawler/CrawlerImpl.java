package club.bluetroy.crawler;

import club.bluetroy.crawler.vo.FilterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-11-09
 * Time: 3:19 PM
 */
@Service
class CrawlerImpl implements Crawler {
    @Autowired
    private Downloader downloader;
    @Autowired
    private Scanner scanner;
    @Autowired
    private Filter filter;
    @Autowired
    private UserAuthenticator userAuthenticator;

    @Override
    public void downloadNow() {
        downloader.downloadNow();
    }

    @Override
    public Future downloadByKey(String key) {
        return downloader.downloadByKey(key);
    }

    @Override
    public void setParallelTaskAndConcurrentThreads(Integer parallelTask, Integer concurrentThreads) {
        downloader.setParallelTaskAndConcurrentThreads(parallelTask, concurrentThreads);
    }

    @Override
    public void doFilter() {
        filter.doFilter();
    }

    @Override
    public void scanMovies() {
        scanner.scanMovies();
    }

    @Override
    public void scanDownloadUrl() {
        scanner.scanDownloadUrl();
    }

    @Override
    public String getDownloadUrl(String key) throws Exception {
        return scanner.getDownloadUrl(key);
    }

    @Override
    public void addUrl(String url) {
        scanner.addUrl(url);
    }

    @Override
    public void login(String name, String password, String verificationCode) throws Exception {
        userAuthenticator.login(name, password, verificationCode);
    }

    @Override
    public List<String> getScanUrls() {
        return scanner.getScanUrls();
    }

    @Override
    public String getFilterInfo() {
        return filter.getFilterInfo();
    }

    @Override
    public void setResource(Path path) {
        downloader.setResource(path);
    }

    @Override
    public void setFilter(FilterVO filterVO) {
        filter.setFilter(filterVO);
    }
}
