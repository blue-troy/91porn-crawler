package club.bluetroy.crawler.filter.impl;

import club.bluetroy.crawler.domain.Movie;
import club.bluetroy.crawler.filter.AbstractMovieFilter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * @author heyixin
 */
@ToString
@EqualsAndHashCode(callSuper = false)
class CollectMovieFilter extends AbstractMovieFilter {
    private final Integer collectNum;

    CollectMovieFilter(Integer collectNum) {
        this.collectNum = collectNum;
    }

    @Override
    public void doFilter(List<Movie> tobeFilter) {
        tobeFilter.removeIf(movie -> movie.getCollect() < collectNum);
    }
}
