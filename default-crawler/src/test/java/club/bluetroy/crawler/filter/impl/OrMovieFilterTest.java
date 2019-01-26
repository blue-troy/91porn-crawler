package club.bluetroy.crawler.filter.impl;

import club.bluetroy.crawler.domain.Movie;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

import static junit.framework.TestCase.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2019-01-26
 * Time: 17:44
 */
public class OrMovieFilterTest {

    private ConcurrentHashMap<String, Movie> tobeFilter;

    @Before
    public void setUpTobeFilter() {
        tobeFilter = new ConcurrentHashMap<>();
        tobeFilter.put("1", getTitledMovieInstance("露脸"));
        tobeFilter.put("2", getTitledMovieInstance("学生露脸"));
        tobeFilter.put("3", getTitledMovieInstance("学生"));
        tobeFilter.put("4", getTitledMovieInstance("美女"));
    }

    private Movie getTitledMovieInstance(String title) {
        Movie movie = new Movie();
        movie.setTitle(title);
        return movie;
    }

    @Test
    public void doFilter() {
        OrMovieFilter orMovieFilter = new OrMovieFilter();
        orMovieFilter.addFilter(new TitleMovieFilter("露脸"));
        orMovieFilter.addFilter(new TitleMovieFilter("学生"));

        orMovieFilter.doFilter(tobeFilter);

        assertFilterCorrect();
    }

    private void assertFilterCorrect() {
        tobeFilter.forEachValue(1, movie -> {
            String title = movie.getTitle();
            assertTrue(title.contains("露脸") || title.contains("学生"));
        });
    }
}