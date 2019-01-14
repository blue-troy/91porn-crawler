package club.bluetroy.crawler.filter.impl;

import club.bluetroy.crawler.vo.Movie;
import club.bluetroy.crawler.filter.MovieFilter;
import club.bluetroy.crawler.util.TimeUtils;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * Description: 过滤出某个时间范围内的视频
 *
 * @author: heyixin
 * Date: 2018-11-16
 * Time: 5:19 PM
 */
@ToString
public class AddTimeMovieFilter implements MovieFilter {
    private LocalDateTime addTimeBefore;
    private LocalDateTime addTimeAfter;

    AddTimeMovieFilter(LocalDateTime addTimeBefore, LocalDateTime addTimeAfter) {
        this.addTimeBefore = addTimeBefore;
        this.addTimeAfter = addTimeAfter;
    }

    @Override
    public void doFilter(ConcurrentHashMap<String, Movie> tobeFilter) {
        tobeFilter.forEach(1, (k, v) -> {
            LocalDateTime addTime = TimeUtils.parse(v.getAddTime());
            if (null != addTimeBefore && !addTimeBefore.isBefore(addTime)) {
                tobeFilter.remove(k);
            }
            if (null != addTimeAfter && !addTimeAfter.isAfter(addTime)) {
                tobeFilter.remove(k);
            }
        });
    }
}
