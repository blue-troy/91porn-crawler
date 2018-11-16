package com.bluetroy.crawler91.crawler.dao.entity;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-11-16
 * Time: 2:52 AM
 */
public class MovieTest {
    @Test
    public void t() {
        String detailUrl = "http://91porn.com/view_video.php?viewkey=25a1d160346578f2edc1&page=1&viewtype=basic&category=hot";
        String key = detailUrl.substring(detailUrl.indexOf("=")+1, detailUrl.indexOf("&"));
        System.out.println(key);
    }
}