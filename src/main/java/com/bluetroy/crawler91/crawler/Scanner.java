package com.bluetroy.crawler91.crawler;

import com.bluetroy.crawler91.repository.Repository;
import com.bluetroy.crawler91.repository.pojo.KeyContent;
import com.bluetroy.crawler91.repository.pojo.Movie;
import com.bluetroy.crawler91.utils.HttpRequester;
import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Node;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.seimicrawler.xpath.exception.XpathSyntaxErrorException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;

import static com.bluetroy.crawler91.repository.Repository.FILTERED_MOVIES;
import static com.bluetroy.crawler91.repository.Repository.MOVIE_DATA;

/**
 * @author heyixin
 */
@Log4j2
@Component
public class Scanner {
    private static final List<String> URLS_FOR_SCAN = new ArrayList<>();

    static {
        URLS_FOR_SCAN.add("http://91porn.com/v.php?category=hot&viewtype=basic");
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
        LinkedBlockingDeque<KeyContent> keyContentQueue = getDetailContentsQueue();
        scanDownloadUrlInKeyContentQueue(keyContentQueue);
    }

    private void scanDownloadUrlInKeyContentQueue(LinkedBlockingDeque<KeyContent> keyContentQueue) {
        KeyContent keyContent;
        while ((keyContent = keyContentQueue.poll()) != null) {
            if (keyContent.getContent().isDone()) {
                scanDownloadUrlInKeyContent(keyContent);
            } else {
                keyContentQueue.offer(keyContent);
            }
        }
    }

    private void scanDownloadUrlInKeyContent(KeyContent keyContent) {
        try {
            String content = keyContent.getContent().get();
            JXDocument doc = JXDocument.create(content);
            scanDownloadUrlInDoc(doc, keyContent);
        } catch (InterruptedException | ExecutionException | XpathSyntaxErrorException e) {
            e.printStackTrace();
            log.warn("搜索不到下载地址，应该是被ban了");
        }
    }

    private void scanDownloadUrlInDoc(JXDocument doc, KeyContent keyContent) throws XpathSyntaxErrorException {
        Movie movie = MOVIE_DATA.get(keyContent.getKey());
        List<JXNode> rs = doc.selN("//source");
        //todo 如果得不到就会 java.lang.IndexOutOfBoundsException 可以增加一个判断是否被ban了
        String downloadURL = rs.get(0).getElement().attributes().get("src");
        movie.setDownloadURL(downloadURL);
        log.info(movie.toString());
        Repository.addToDownloadMoviesByKey(movie.getKey());
    }

    private void scanMoviesByUrlList() {
        LinkedBlockingDeque<Future<String>> contentQueue = getContentsQueue();
        scanMoviesInContentQueue(contentQueue);
    }

    private void scanMoviesInContentQueue(LinkedBlockingDeque<Future<String>> contentQueue) {
        Future<String> future;
        while ((future = contentQueue.poll()) != null) {
            if (future.isDone()) {
                scanMovieInContent(future);
            } else {
                contentQueue.offer(future);
            }
        }
    }

    private void scanMovieInContent(Future<String> future) {
        try {
            String content = future.get();
            JXDocument doc = JXDocument.create(content);
            scanMovieInDoc(doc);
        } catch (InterruptedException | ExecutionException | XpathSyntaxErrorException e) {
            e.printStackTrace();
        }
    }

    private void scanMovieInDoc(JXDocument doc) throws XpathSyntaxErrorException {
        List<JXNode> rs = doc.selN("//div[@id='videobox']/table//div[@class='listchannel']");
        for (JXNode r : rs) {
            try {
                Movie movie = getMovieFromJXNode(r);
                Repository.setScannedMovie(movie);
            } catch (XpathSyntaxErrorException e) {
                e.printStackTrace();
            }
        }
    }

    private Movie getMovieFromJXNode(JXNode r) throws XpathSyntaxErrorException {
        //todo 空异常
        List<Node> nodes = r.getElement().childNodes();
        Movie movie = new Movie();
        movie.setTitle(r.sel(".//a[@target='blank']/@title").get(1).getTextVal().replaceAll("\\s*", ""))
                .setDetailURL(r.sel(".//a[@target='blank']/@href").get(0).getTextVal().replaceAll("\\s*", ""))
                .setLength(nodes.get(8).toString().replaceAll("\\s*", ""))
                .setAddTime(nodes.get(12).toString().replaceAll("\\s*", ""))
                .setAuthor(nodes.get(16).toString().replaceAll("\\s*", ""))
                .setView(nodes.get(20).toString().replaceAll("\\s*", ""))
                .setCollect(nodes.get(22).toString().replaceAll("\\s*", ""))
                .setMessageNumber(nodes.get(26).toString().replaceAll("\\s*", ""))
                .setIntegration(nodes.get(28).toString().replaceAll("\\s*", ""));
        log.info(movie.toString());
        return movie;
    }

    private KeyContent getKeyContentMap(String key) throws Exception {
        return new KeyContent(key, HttpRequester.get(MOVIE_DATA.get(key).getDetailURL()));
    }

    private LinkedBlockingDeque<KeyContent> getDetailContentsQueue() {
        LinkedBlockingDeque<KeyContent> contentQueue = new LinkedBlockingDeque<>();
        FILTERED_MOVIES.forEach(5, (k, v) -> {
            if (v) {
                return;
            }
            try {
                contentQueue.offer(getKeyContentMap(k));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return contentQueue;
    }

    private LinkedBlockingDeque<Future<String>> getContentsQueue() {
        LinkedBlockingDeque<Future<String>> contentQueue = new LinkedBlockingDeque<>();
        for (String url : Scanner.URLS_FOR_SCAN) {
            try {
                contentQueue.offer(HttpRequester.get(url));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return contentQueue;
    }
}
