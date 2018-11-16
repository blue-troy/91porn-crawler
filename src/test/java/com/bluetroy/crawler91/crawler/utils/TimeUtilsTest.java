package com.bluetroy.crawler91.crawler.utils;

import org.junit.Test;

import java.time.LocalDateTime;


/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-11-16
 * Time: 2:10 AM
 */
public class TimeUtilsTest {

    @Test
    public void getDate() throws Exception {
//        String time = TimeUtils.getDateByTimeBefore("1天前");
//        System.out.println(time);
        String time1 = TimeUtils.getDateByTimeBefore("2天前");
        System.out.println(time1);
        LocalDateTime parse = TimeUtils.parse(time1);
        System.out.println(parse);
        System.out.println(TimeUtils.format(parse));
    }
}