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

import java.util.ArrayList;
import java.util.List;
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
public class Selector {

    public String getLoginErrorMessage(String html) {
        return Jsoup.parse(html).select("#container > div.errorbox").text();

    }

    public List<Movie> getMovies(String html) throws ExecutionException, InterruptedException {
        List<Movie> movieList = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements movies = document.select("#videobox > table > tbody > tr > td > div.listchannel");
        for (Element movieElement : movies) {
            movieList.add(getMovie(movieElement));
        }
        return movieList;
    }

    public String getDownloadUrl(String html) {
        Document document = Jsoup.parse(html);
        return document.selectFirst("source").attr("src");
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
}
