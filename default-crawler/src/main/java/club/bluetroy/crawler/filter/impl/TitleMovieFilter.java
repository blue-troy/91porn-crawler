package club.bluetroy.crawler.filter.impl;

import club.bluetroy.crawler.domain.Movie;
import club.bluetroy.crawler.filter.AbstractMovieFilter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author heyixin
 */
@ToString
@EqualsAndHashCode(callSuper = false)
class TitleMovieFilter extends AbstractMovieFilter {
    private String keyword;

    TitleMovieFilter(String keyword) {
        this.keyword = keyword;
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
