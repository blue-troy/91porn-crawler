package com.bluetroy.crawler91.crawler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-07-09
 * Time: 上午1:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ScannerTest {
    @Autowired
    Scanner scanner;
    @Autowired
    Filter filter;

    @Test
    public void scanMovies() {
        scanner.scanMovies();
    }

    @Test
    public void setFilteredMoviesDownloadURL() {
        //todo 修改成符合"单元"测试的测试。
        scanner.scanMovies();
        filter.doFilter();
        //scanner.setFilteredMoviesDownloadURL();
    }
}