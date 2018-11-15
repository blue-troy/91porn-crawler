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
    public void setAddTime() throws Exception {
        LocalDateTime time = getLocalDateTime("  42 天  前 ");
        String format = time.format(DateTimeFormatter.ofPattern("y年M月d日h时"));
        System.out.println(format);
    }

    private LocalDateTime getLocalDateTime(String addTimeBeforeNow) throws Exception {
        int yearsBefore = addTimeBeforeNow.indexOf("年");
        int daysBefore = addTimeBeforeNow.indexOf("天");
        int hoursBefore = addTimeBeforeNow.indexOf("小时");
        if (yearsBefore != -1) return LocalDateTime.now().minusYears(yearsBefore);
        if (daysBefore != -1) return LocalDateTime.now().minusDays(daysBefore);
        if (hoursBefore != -1) return LocalDateTime.now().minusHours(hoursBefore);
        throw new Exception("time： " + addTimeBeforeNow + " parse error");
    }
}