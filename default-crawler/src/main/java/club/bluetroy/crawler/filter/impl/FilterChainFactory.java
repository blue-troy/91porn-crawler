package club.bluetroy.crawler.filter.impl;

import club.bluetroy.crawler.filter.MovieFilterChain;
import club.bluetroy.crawler.util.TimeUtils;
import club.bluetroy.crawler.domain.FilterConfig;

import java.time.LocalDateTime;

/**
 * @author heyixin
 */
public class FilterChainFactory {
    public static MovieFilterChain getDefaultFilter() {
        return getHotShowFaceFilter();
    }

    //todo 其他的Filter

    public static MovieFilterChain getFilter(FilterConfig filterConfig) {
        MovieFilterChain chain = new MovieFilterChain();
        handleTitleFilter(filterConfig, chain);
        handleCollectFilter(filterConfig, chain);
        handleAddTimeFilter(filterConfig, chain);
        handleAddTimeDistanceFilter(filterConfig, chain);
        return chain;
    }

    private static void handleAddTimeDistanceFilter(FilterConfig filterConfig, MovieFilterChain chain) {
        if (filterConfig.getAddTimeDistance() != null) {
            chain.addFilter(new AddTimeDistanceMovieFilter(filterConfig.getAddTimeDistance()));
        }
    }


    private static void handleAddTimeFilter(FilterConfig filterConfig, MovieFilterChain chain) {
        String addTimeBeforeString = filterConfig.getAddTimeBefore();
        String addTimeAfterString = filterConfig.getAddTimeAfter();
        LocalDateTime addTimeBefore = null;
        LocalDateTime addTimeAfter = null;
        if (addTimeAfterString != null) {
            try {
                addTimeBefore = TimeUtils.parse(TimeUtils.getDateByTimeBefore(addTimeBeforeString));
            } catch (Exception ignored) {
            }
        }
        if (addTimeAfterString != null) {
            try {
                addTimeAfter = TimeUtils.parse(TimeUtils.getDateByTimeBefore(addTimeAfterString));
            } catch (Exception ignored) {
            }
        }
        if (addTimeAfter != null || addTimeBefore != null) {
            chain.addFilter(new AddTimeMovieFilter(addTimeBefore, addTimeAfter));
        }
    }

    private static void handleCollectFilter(FilterConfig filterConfig, MovieFilterChain chain) {
        if (null != filterConfig.getCollect()) {
            chain.addFilter(new CollectMovieFilter(filterConfig.getCollect()));
        }
    }

    private static void handleTitleFilter(FilterConfig filterConfig, MovieFilterChain chain) {
        if (null != filterConfig.getTitle() && !filterConfig.getTitle().isEmpty()) {
            chain.addFilter(new TitleMovieFilter(filterConfig.getTitle()));
        }
    }

    private static MovieFilterChain getHotShowFaceFilter() {
        FilterConfig filterConfig = new FilterConfig();
        filterConfig.setTitle("露脸");
        filterConfig.setCollect(200);
        filterConfig.setAddTimeDistance("1天");
        return getFilter(filterConfig);
    }
}
