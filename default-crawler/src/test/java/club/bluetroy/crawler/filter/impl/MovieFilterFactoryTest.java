package club.bluetroy.crawler.filter.impl;

import club.bluetroy.crawler.domain.FilterConfig;
import club.bluetroy.crawler.domain.Movie;
import club.bluetroy.crawler.filter.AbstractMovieFilter;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2019-01-26
 * Time: 19:10
 */
public class MovieFilterFactoryTest {

    @Test
    public void getDefaultFilter() {
    }

    @Test
    public void getFilter() {
        FilterConfig currentShowFaceConfig = new FilterConfig();
        currentShowFaceConfig.setAddTimeDistance("1天");
        currentShowFaceConfig.setCollect(200);
        currentShowFaceConfig.setTitle("露脸");

        FilterConfig currentHotConfig = new FilterConfig();
        currentHotConfig.setAddTimeDistance("1天");
        currentHotConfig.setCollect(500);

        FilterConfig monthShowFace = new FilterConfig();
        monthShowFace.setAddTimeDistance("1月");
        monthShowFace.setTitle("露脸");
        monthShowFace.setCollect(1500);

        FilterConfig monthHot = new FilterConfig();
        monthHot.setAddTimeDistance("1月");
        monthHot.setCollect(2000);

        FilterConfig collectOverFourThousand = new FilterConfig();
        collectOverFourThousand.setCollect(4000);

        currentShowFaceConfig.setOrFilterConfig(currentHotConfig);
        currentHotConfig.setOrFilterConfig(monthShowFace);
        monthShowFace.setOrFilterConfig(monthHot);
        monthHot.setOrFilterConfig(collectOverFourThousand);

        AbstractMovieFilter filter = MovieFilterFactory.getFilter(currentShowFaceConfig);

        ConcurrentHashMap<String, Movie> tofilter = new ConcurrentHashMap<>();
        tofilter.put("1", getMovieInstance("露脸", 100, "1天"));
        tofilter.put("2", getMovieInstance("露脸", 200, "1天"));
        tofilter.put("3", getMovieInstance("人", 600, "1天"));
        tofilter.put("4", getMovieInstance("露脸", 1500, "1月"));
        tofilter.put("5", getMovieInstance("露脸", 1400, "1月"));
        tofilter.put("6", getMovieInstance("人", 500, "29天"));
        tofilter.put("7", getMovieInstance("人", 3000, "29天"));
        tofilter.put("8", getMovieInstance("人", 5000, "1年"));
        filter.doFilter(tofilter);
        tofilter.forEachValue(1, System.out::println);
    }

    private Movie getMovieInstance(String title, int collect, String month) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setCollect(collect);
        movie.setAddTime(month);
        return movie;
    }
}