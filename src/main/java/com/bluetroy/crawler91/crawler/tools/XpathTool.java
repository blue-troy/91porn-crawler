package com.bluetroy.crawler91.crawler.tools;

import com.bluetroy.crawler91.crawler.dao.BaseDao;
import com.bluetroy.crawler91.crawler.dao.entity.KeyContent;
import com.bluetroy.crawler91.crawler.dao.entity.Movie;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-07-11
 * Time: 下午2:06
 */
@Log4j2
@Component
public class XpathTool {
    private static final String LOGIN_ERROR_MESSAGE_XPATH = "//div[@class='errorbox']";
    @Autowired
    private BaseDao dao;

    public void scanDownloadUrl(KeyContent keyContent) {
        try {
            String content = keyContent.getContent().get();
            Document document = Jsoup.parse(content);
            Movie movie = dao.getMovieData().get(keyContent.getKey());
            String downloadURL = document.selectFirst("source").attr("src");
            movie.setDownloadURL(downloadURL);
            log.info("扫描到了 {} 的下载链接：{}", movie.getTitle(), movie.getDownloadURL());
            dao.addToDownloadMoviesByKey(movie.getKey());
        } catch (InterruptedException | ExecutionException e) {
            Movie movie = dao.getMovieData().get(keyContent.getKey());
            log.warn("搜索不到 {} {} 的下载地址，应该是被ban了", movie.getTitle(), movie.getDetailURL(), e);
            //todo 应该得有被ban的策略
        }
    }

    public String getLoginError(String loginResult) {
        return "";
    }

    private Movie getMovie(Element elementMovie) {
        Movie movie = new Movie();
        movie.setTitle(elementMovie.select("a").attr("title").replaceAll("\\s*", ""))
                .setDetailURL(elementMovie.select("a").attr("href").replaceAll("\\s*", ""))
                .setLength(elementMovie.childNode(8).toString().replaceAll("\\s*", ""))
                .setAddTime(elementMovie.childNode(12).toString().replaceAll("\\s*", ""))
                .setAuthor(elementMovie.childNode(16).toString().replaceAll("\\s*", ""))
                .setView(elementMovie.childNode(20).toString().replaceAll("\\s*", ""))
                .setCollect(elementMovie.childNode(22).toString().replaceAll("\\s*", ""))
                .setMessageNumber(elementMovie.childNode(26).toString().replaceAll("\\s*", ""))
                .setIntegration(elementMovie.childNode(28).toString().replaceAll("\\s*", ""));
        log.info("扫描到了视频：{} ", movie.toString());
        return movie;
    }

    public void setMovie(Future<String> future) {
        try {
            setMovie(future.get());
        } catch (InterruptedException | ExecutionException e) {
            log.warn("网络访问错误", e);
        }
    }

    private void setMovie(String contentString) {
        Document document = Jsoup.parse(contentString);
        Elements movies = document.select("#videobox > table > tbody > tr > td > div.listchannel");
        for (Element movieElement : movies) {
            Movie movie = getMovie(movieElement);
            dao.setScannedMovie(movie);
        }
    }
}
