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
 * @author heyixin
 * Date: 2018-11-16
 * Time: 5:19 PM
 */
@ToString
@EqualsAndHashCode(callSuper = false)
class AddTimeMovieFilter extends AbstractMovieFilter {
    private LocalDateTime addTimeBefore;
    private LocalDateTime addTimeAfter;

    AddTimeMovieFilter(LocalDateTime addTimeBefore, LocalDateTime addTimeAfter) {
        this.addTimeBefore = addTimeBefore;
        this.addTimeAfter = addTimeAfter;
    }

    @Override
    public void doFilter(List<Movie> tobeFilter) {
        Iterator<Movie> iterator = tobeFilter.iterator();
        while (iterator.hasNext()) {
            Movie movie = iterator.next();
            LocalDateTime addTime = TimeUtils.parse(movie.getAddTime());
            if (null != addTimeBefore && !addTimeBefore.isBefore(addTime)) {
                iterator.remove();
                continue;
            }
            if (null != addTimeAfter && !addTimeAfter.isAfter(addTime)) {
                iterator.remove();
            }
        }
    }
}
