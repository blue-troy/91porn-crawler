package club.bluetroy.crawler.filter.impl;

import club.bluetroy.crawler.domain.Movie;
import club.bluetroy.crawler.filter.MovieFilter;
import lombok.ToString;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author heyixin
 */
@ToString
public class TitleMovieFilter implements MovieFilter {
    private String keyword;

    public TitleMovieFilter(String keyword) {
        this.keyword = keyword;
    }

    private TitleMovieFilter() {
    }

    @Override
    public void doFilter(ConcurrentHashMap<String, Movie> tobeFilter) {
        tobeFilter.forEach(1, (k, v) -> {
            if (v.getTitle().contains(keyword)) {
                return;
            }
            tobeFilter.remove(k);
        });
    }

}
