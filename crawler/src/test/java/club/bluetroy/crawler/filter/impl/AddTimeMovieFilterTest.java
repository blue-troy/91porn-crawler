package club.bluetroy.crawler.filter.impl;

import club.bluetroy.crawler.dao.entity.Movie;
import club.bluetroy.crawler.util.TimeUtils;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-12-16
 * Time: 07:31
 */
public class AddTimeMovieFilterTest {
    @Test
    public void doFilter() throws Exception {
        LocalDateTime timeAfter = LocalDateTime.now().minusDays(1);
        AddTimeMovieFilter addTimeMovieFilter = new AddTimeMovieFilter(null, timeAfter);
        ConcurrentHashMap<String, Movie> toFilter = getToFilter();
        printToFilter(toFilter);
        addTimeMovieFilter.doFilter(toFilter);
        printToFilter(toFilter);
    }

    private void printToFilter(ConcurrentHashMap<String, Movie> toFilter) {
        toFilter.forEachEntry(1, entry -> System.out.println(entry.getKey() + "  " + entry.getValue()));
    }

    private ConcurrentHashMap<String, Movie> getToFilter() throws Exception {
        ConcurrentHashMap<String, Movie> toFilter = new ConcurrentHashMap<>();
        Movie movie = new Movie();
        movie.setAddTime(TimeUtils.getDateByTimeBefore("1天"));
        movie.setTitle("第一个");

        Movie movie1 = new Movie();
        movie1.setAddTime(TimeUtils.getDateByTimeBefore("2天"));
        movie1.setTitle("第二个");

        toFilter.put("1", movie);
        toFilter.put("2", movie1);

        return toFilter;
    }
}