package club.bluetroy.crawler.filter.impl;

import club.bluetroy.crawler.filter.MovieFilter;
import club.bluetroy.crawler.domain.Movie;
import lombok.ToString;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author heyixin
 */
@ToString
public class CollectMovieFilter implements MovieFilter {
    private final Integer collectNum;

    public CollectMovieFilter(Integer collectNum) {
        this.collectNum = collectNum;
    }

    @Override
    public void doFilter(ConcurrentHashMap<String, Movie> tobeFilter) {
        tobeFilter.forEach(5, (k, v) -> {
            if (v.getCollect() >= collectNum) {
                return;
            }
            tobeFilter.remove(k);
        });
    }
}
