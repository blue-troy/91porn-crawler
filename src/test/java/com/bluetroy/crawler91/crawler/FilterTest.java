package com.bluetroy.crawler91.crawler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-07-09
 * Time: 下午2:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Component
public class FilterTest {
    @Autowired
    Scanner scanner;
    @Autowired
    Filter filter;

    @Before
    public void prepareSanedMovieForFilter() {
        scanner.scanMovies();
    }

    @Test
    public void doFilter() {
        filter.doFilter();
    }
}