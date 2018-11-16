package com.bluetroy.crawler91.crawler.filter.impl;

import com.bluetroy.crawler91.crawler.dao.entity.Movie;
import com.bluetroy.crawler91.crawler.filter.MovieFilter;
import com.bluetroy.crawler91.crawler.utils.TimeUtils;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-11-16
 * Time: 5:19 PM
 */
@ToString
public class AddTimeMovieFilter implements MovieFilter {
    private LocalDateTime addTimeBefore = null;
    private LocalDateTime addTimeAfter = null;

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
