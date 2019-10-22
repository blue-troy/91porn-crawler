package club.bluetroy.crawler.filter.impl;

import club.bluetroy.crawler.domain.Movie;
import club.bluetroy.crawler.filter.AbstractMovieFilter;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author heyixin
 */
@Slf4j
@ToString
@EqualsAndHashCode(callSuper = false)
class MovieFilterChain extends AbstractCompositeMovieFilter {
    private final ArrayList<AbstractMovieFilter> movieFilterList = new ArrayList<>();

    @Override
    public void doFilter(List<Movie> tobeFilter) {
        for (AbstractMovieFilter movieFilter : movieFilterList) {
            movieFilter.doFilter(tobeFilter);
        }
    }

    @Override
    void addFilter(AbstractMovieFilter movieFilter) {
        movieFilterList.add(movieFilter);
    }
}
