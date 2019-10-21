package club.bluetroy.crawler.util;


import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


/**
 * @author heyixin
 * Date: 2018-11-16
 * Time: 2:10 AM
 */
public class TimeUtilsTest {
    private Long aLong;
    private long blong;

    @Test
    void getDate() throws Exception {
//        String time = TimeUtils.getDateByTimeBefore("1天前");
//        System.out.println(time);
        String time1 = TimeUtils.getDateByTimeBefore("2天前");
        System.out.println(time1);
        LocalDateTime parse = TimeUtils.parse(time1);
        System.out.println(parse);
        System.out.println(TimeUtils.format(parse));
    }

    @Test
    void timeToMinute() {
        System.out.println(TimeUtils.timeToMinute("1时1分"));
    }

    @Test
    void testlong() {
        System.out.println(aLong);
        System.out.println(blong);
    }
}