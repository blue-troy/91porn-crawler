package club.bluetroy.crawler.filter.impl;

import club.bluetroy.crawler.domain.Movie;
import club.bluetroy.crawler.filter.AbstractMovieFilter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2019-01-26
 * Time: 16:39
 */
@ToString
@EqualsAndHashCode(callSuper = false)
class OrMovieFilter extends AbstractCompositeMovieFilter {
    private List<AbstractMovieFilter> movieFilters = new LinkedList<>();

    OrMovieFilter(List<AbstractMovieFilter> movieFilters) {
        this.movieFilters = movieFilters;
    }

    OrMovieFilter() {
    }

    @Override
    public void doFilter(ConcurrentHashMap<String, Movie> tobeFilter) {
        ConcurrentHashMap<String, Movie> tobeFilterTemp = new ConcurrentHashMap<>(tobeFilter);
        tobeFilter.clear();
        for (AbstractMovieFilter movieFilter : movieFilters) {
            ConcurrentHashMap<String, Movie> subFilterResult = subDoFilter(tobeFilterTemp, movieFilter);
            tobeFilter.putAll(subFilterResult);
        }
    }

    private ConcurrentHashMap<String, Movie> subDoFilter(ConcurrentHashMap<String, Movie> tempTobeFilter, AbstractMovieFilter movieFilter) {
        ConcurrentHashMap<String, Movie> subTobeFilter = new ConcurrentHashMap<>(tempTobeFilter);
        movieFilter.doFilter(subTobeFilter);
        return subTobeFilter;
    }

    @Override
    void addFilter(AbstractMovieFilter newFilter) {
        if (!movieFilters.contains(newFilter)) {
            movieFilters.add(newFilter);
        } else {
            throw new IllegalArgumentException("不能添加已存在的movieFilter");
        }
    }
}
