package club.bluetroy.crawler.utils;

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
    Long aLong;
    long blong;

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

    @Test
    public void timeToMinute() {
        System.out.println(TimeUtils.timeToMinute("1时1分"));
    }

    @Test
    public void testlong() {
        System.out.println(aLong);
        System.out.println(blong);
    }
}