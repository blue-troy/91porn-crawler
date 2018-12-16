package club.bluetroy.crawler.tools;

import club.bluetroy.crawler.dao.entity.Movie;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
        String title = cleanString(elementMovie.select("a").attr("title"));
        String detailUrl = cleanString(elementMovie.select("a").attr("href"));
        String length = cleanString(elementMovie.childNode(8).toString());
        String addTimeBefore = cleanString(elementMovie.childNode(12).toString());
        String author = cleanString(elementMovie.childNode(16).toString());
        Integer view = Integer.valueOf(cleanString(elementMovie.childNode(20).toString()));
        Integer collect = Integer.valueOf(cleanString(elementMovie.childNode(22).toString()));
        Integer messageNumber = Integer.valueOf(cleanString(elementMovie.childNode(26).toString()));
        Integer integration = Integer.valueOf(cleanString(elementMovie.childNode(28).toString()));
        Movie movie = new Movie(title, length, addTimeBefore, author, view, collect, messageNumber, integration, detailUrl);
        log.info("扫描到了视频：{} ", movie.toString());
        return movie;
    }

    private String cleanString(String dirtyString) {
        return dirtyString.replaceAll("\\s*", "").replaceAll("&nbsp;", "");
    }
}
