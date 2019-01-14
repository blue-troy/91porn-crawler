package club.bluetroy.crawler.filter;

import club.bluetroy.crawler.vo.Movie;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author heyixin
 */
@Slf4j
@ToString
public class MovieFilterChain implements MovieFilter {
    private final ArrayList<MovieFilter> movieFilterList = new ArrayList<>();


    public MovieFilterChain addFilter(MovieFilter movieFilter) {
        movieFilterList.add(movieFilter);
        return this;
    }

    @Override
    public void doFilter(ConcurrentHashMap<String, Movie> tobeFilter) {
        log.info("当前责任链为：{}",this.toString());
        for (MovieFilter movieFilter : movieFilterList) {
            movieFilter.doFilter(tobeFilter);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MovieFilterChain chain = (MovieFilterChain) o;
        return chain.toString().equals(this.toString());
    }

}
