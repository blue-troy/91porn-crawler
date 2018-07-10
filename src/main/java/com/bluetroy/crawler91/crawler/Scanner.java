package com.bluetroy.crawler91.crawler;

import com.bluetroy.crawler91.repository.CrawlerList;
import com.bluetroy.crawler91.repository.Movie;
import com.bluetroy.crawler91.utils.HttpRequester;
import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Node;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.seimicrawler.xpath.exception.XpathSyntaxErrorException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.bluetroy.crawler91.repository.CrawlerList.FILTERED_MOVIES;
import static com.bluetroy.crawler91.repository.CrawlerList.MOVIE_DATA;

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

    public void setFilteredMoviesDownloadURL() {
        FILTERED_MOVIES.forEach(5, (k, v) -> {
            if (v) {
                return;
            }
            //todo 名称很奇怪不是吗
            setMovieDownloadURL(MOVIE_DATA.get(k));
        });
    }

    private String getMovieDownloadURLbyDetailURL(String string) throws Exception {
        String responseContent = HttpRequester.get(string);
        JXDocument doc = JXDocument.create(responseContent);
        List<JXNode> rs = doc.selN("//source");
        //todo 如果得不到就会 java.lang.IndexOutOfBoundsException 可以增加一个判断是否被ban了
        return rs.get(0).getElement().attributes().get("src");
    }

    private void scanMoviesByUrlList() {
        for (String url : Scanner.URLS_FOR_SCAN) {
            try {
                scanMoviesByUrlString(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void scanMoviesByUrlString(String url) throws Exception {
        String content = HttpRequester.get(url);
        JXDocument doc = JXDocument.create(content);
        List<JXNode> rs = doc.selN("//div[@id='videobox']/table//div[@class='listchannel']");
        for (JXNode r : rs) {
            try {
                Movie movie = getMovieFromJXNode(r);
                CrawlerList.setScannedMovie(movie);
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

    private void setMovieDownloadURL(Movie movie) {
        try {
            String downloadURL = getMovieDownloadURLbyDetailURL(movie.getDetailURL());
            movie.setDownloadURL(downloadURL);
            log.info(movie.toString());
            CrawlerList.addToDownloadMoviesByKey(movie.getKey());
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("搜索不到下载地址，应该是被ban了");
        }
    }
}
