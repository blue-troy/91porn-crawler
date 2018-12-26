package club.bluetroy.crawler;

import club.bluetroy.crawler.dao.BaseDao;
import club.bluetroy.crawler.dao.MovieStatus;
import club.bluetroy.crawler.dao.entity.Movie;
import club.bluetroy.crawler.filter.impl.FilterChainFactory;
import club.bluetroy.crawler.vo.FilterVO;
import club.bluetroy.crawler.filter.MovieFilterChain;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author heyixin
 */
@Log4j2
@Service("filter")
class FilterImpl implements Filter {
    @Autowired
    private BaseDao dao;
    private MovieFilterChain filterChain;

    @Override
    public void doFilter() {
        log.info("doing filterr. filterChain : " + getFilterInfo());
        ConcurrentHashMap<String, Movie> scannedMovies = dao.getMovies(MovieStatus.SCANNED_MOVIES);
        getFilterChain().doFilter(scannedMovies);
        LinkedList<String> filteredMovies = new LinkedList<>();
        scannedMovies.forEachEntry(1, entry -> {
            filteredMovies.add(entry.getKey());
            log.info("过滤出了 ：{}", entry.getValue());
        });
        dao.addFilteredMovies(filteredMovies);
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
    public void setFilter(FilterVO filterVO) {
        MovieFilterChain newFilter = FilterChainFactory.getFilter(filterVO);
        if (!newFilter.equals(this.filterChain)) {
            this.filterChain = newFilter;
        }
    }

}
