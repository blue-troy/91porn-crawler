package club.bluetroy.crawler.filter.impl;

import club.bluetroy.crawler.domain.Movie;
import club.bluetroy.crawler.filter.AbstractMovieFilter;
import club.bluetroy.crawler.util.TimeUtils;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:过滤出发布多久内的视频，如过滤出发布时间一周内的视频
 *
 * @author heyixin
 * Date: 2018-11-30
 * Time: 23:34
 */
@ToString
@EqualsAndHashCode(callSuper = false)
class AddTimeDistanceMovieFilter extends AbstractMovieFilter {
    /**
     * 以分钟为单位的时间
     */
    private Long timeAfter;

    /**
     * @param timeAfter 以秒为单位的时间，距离发布多少分钟
     */
    AddTimeDistanceMovieFilter(Long timeAfter) {
        this.timeAfter = timeAfter;
    }

    @Override
    public void doFilter(List<Movie> tobeFilter) {
        Iterator<Movie> iterator = tobeFilter.iterator();
        while (iterator.hasNext()) {
            Movie movie = iterator.next();
            LocalDateTime addTime = movie.getAddDateTime();
            if (TimeUtils.now().minusMinutes(timeAfter).minusMinutes(1).isAfter(addTime)) {
                iterator.remove();
            }
        }
    }
}
