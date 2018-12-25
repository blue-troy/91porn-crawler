package club.bluetroy.crawler.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author heyixin
 */
public class TimeUtils {
    private static final String FORMAT = "y年M月d日H时";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(FORMAT);

    public static String getDate() {
        return LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }

    public static String getDateByTimeBefore(String timeBefore) throws Exception {
        return getLocalDateTime(timeBefore).format(DATE_TIME_FORMATTER);
    }

    public static LocalDateTime parse(String time) {
        return LocalDateTime.parse(time, DATE_TIME_FORMATTER);
    }

    public static String format(LocalDateTime time) {
        return time.format(DATE_TIME_FORMATTER);
    }

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static LocalDateTime getLocalDateTime(String addTimeBeforeNow) throws Exception {
        int indexOfYear = addTimeBeforeNow.indexOf("年");
        int indexOfDay = addTimeBeforeNow.indexOf("天");
        int indexOfHour = addTimeBeforeNow.indexOf("小时");
        if (indexOfYear != -1) {
            int year = Integer.parseInt(addTimeBeforeNow.substring(0, indexOfYear));
            return LocalDateTime.now().minusYears(year);
        }
        if (indexOfDay != -1) {
            int day = Integer.parseInt(addTimeBeforeNow.substring(0, indexOfDay));
            return LocalDateTime.now().minusDays(day);
        }
        if (indexOfHour != -1) {
            int hour = Integer.parseInt(addTimeBeforeNow.substring(0, indexOfHour));
            return LocalDateTime.now().minusHours(hour);
        }
        throw new Exception("time： " + addTimeBeforeNow + " parse error");
    }


    /**
     * @param addTimeDistance 时间如：1年2月3周4天5分钟
     * @return
     */
    public static Long timeToMinute(String addTimeDistance) {
        long minus = 0L;
        String[] timeArray = addTimeDistance.replace("年", " 年 ").replace("月", " 月 ").replace("周", " 周 ").replace("天", " 天 ").replace("时", " 时 ").replace("分", " 分 ").split(" ");
        for (int i = 0; i < timeArray.length; i++) {
            minus += Long.valueOf(timeArray[i]) * getF(timeArray[++i]);
        }
        return minus;
    }

    private static Long getF(String s) {
        switch (s) {
            case "年":
                return (long) (365 * 24 * 60);
            case "月":
                return (long) (30 * 24 * 60);
            case "周":
                return (long) (7 * 24 * 60);
            case "天":
                return (long) (24 * 60);
            case "时":
                return (long) (60);
            case "分":
                return 1L;
            default:
                return 1L;
        }
    }
}
