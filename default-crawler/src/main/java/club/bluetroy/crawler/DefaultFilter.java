package club.bluetroy.crawler;

import club.bluetroy.crawler.dao.BaseDao;
import club.bluetroy.crawler.dao.MovieStatus;
import club.bluetroy.crawler.domain.Movie;
import club.bluetroy.crawler.filter.impl.FilterChainFactory;
import club.bluetroy.crawler.domain.FilterConfig;
import club.bluetroy.crawler.filter.MovieFilterChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author heyixin
 */
@Slf4j
@Service
class DefaultFilter implements Filter {
    @Autowired
    private BaseDao dao;
    private MovieFilterChain filterChain;

    /**
     * 把需要的视频顾虑出来放在filtered-movies里,并没有改变scanned-movies
     */
    @Override
    public void doFilter() {
        log.info("doing filterr. filterChain : " + getFilterInfo());
        ConcurrentHashMap<String, Movie> scannedMovies = dao.listMovies(MovieStatus.SCANNED_MOVIES);
        getFilterChain().doFilter(scannedMovies);
        LinkedList<String> filteredMovies = new LinkedList<>();
        scannedMovies.forEachEntry(1, entry -> {
            filteredMovies.add(entry.getKey());
            log.info("过滤出了 ：{}", entry.getValue());
        });
        dao.saveFilteredMoviesByKeys(filteredMovies);
    }

    @Override
    public String getFilterInfo() {
        return getFilterChain().toString();
    }

    private MovieFilterChain getFilterChain() {
        if (filterChain == null) {
            filterChain = FilterChainFactory.getDefaultFilter();
        }
        return this.filterChain;
    }

    @Override
    public void setFilter(FilterConfig filterConfig) {
        MovieFilterChain newFilter = FilterChainFactory.getFilter(filterConfig);
        if (!newFilter.equals(this.filterChain)) {
            this.filterChain = newFilter;
        }
    }

}
