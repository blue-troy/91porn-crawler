package club.bluetroy.crawler.filter.impl;

import club.bluetroy.crawler.domain.FilterConfig;
import club.bluetroy.crawler.filter.AbstractMovieFilter;
import club.bluetroy.crawler.config.InnerFilterConfig;
import club.bluetroy.crawler.util.TimeUtils;

import java.time.LocalDateTime;
import java.util.Stack;

/**
 * @author heyixin
 */
public class MovieFilterFactory {
    private static Stack<AbstractMovieFilter> movieFilterStack = new Stack<>();

    public static AbstractMovieFilter getDefaultFilter() {
        return getFilter(InnerFilterConfig.getDefaultFilterConfig());
    }

    synchronized
    public static AbstractMovieFilter getFilter(FilterConfig filterConfig) {
        movieFilterStack.push(null);
        handleTitleFilter(filterConfig);
        handleCollectFilter(filterConfig);
        handleAddTimeFilter(filterConfig);
        handleAddTimeDistanceFilter(filterConfig);
        handleOrFilter(filterConfig);
        return movieFilterStack.pop();
    }

    private static void handleOrFilter(FilterConfig filterConfig) {
        if (null != filterConfig.getOrFilterConfig()) {
            OrMovieFilter movieFilter = new OrMovieFilter();
            AbstractMovieFilter movieFilterTemp = movieFilterStack.pop();
            movieFilterStack.push(movieFilter);
            movieFilter.addFilter(movieFilterTemp);
            movieFilter.addFilter(getFilter(filterConfig.getOrFilterConfig()));
        }
    }

    private static void handleTitleFilter(FilterConfig filterConfig) {
        if (null != filterConfig.getTitle() && !filterConfig.getTitle().isEmpty()) {
            TitleMovieFilter titleMovieFilter = new TitleMovieFilter(filterConfig.getTitle());
            setMovieFilter(titleMovieFilter);
        }
    }

    private static void handleCollectFilter(FilterConfig filterConfig) {
        if (null != filterConfig.getCollect()) {
            setMovieFilter(new CollectMovieFilter(filterConfig.getCollect()));
        }
    }

    private static void handleAddTimeFilter(FilterConfig filterConfig) {
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
            setMovieFilter(new AddTimeMovieFilter(addTimeBefore, addTimeAfter));
        }
    }

    private static void handleAddTimeDistanceFilter(FilterConfig filterConfig) {
        Long addTimeDistance = filterConfig.getAddTimeDistance();
        if (addTimeDistance != null) {
            setMovieFilter(new AddTimeDistanceMovieFilter(addTimeDistance));
        }
    }


    private static void setMovieFilter(AbstractMovieFilter newMovieFilter) {
        AbstractMovieFilter movieFilter = movieFilterStack.peek();
        if (movieFilter == null) {
            movieFilterStack.pop();
            movieFilterStack.push(newMovieFilter);
        } else {
            if (movieFilter instanceof MovieFilterChain) {
                ((MovieFilterChain) movieFilter).addFilter(newMovieFilter);
            } else {
                AbstractMovieFilter pop = movieFilterStack.pop();
                MovieFilterChain movieFilterChain = new MovieFilterChain();
                movieFilterChain.addFilter(pop);
                movieFilterChain.addFilter(newMovieFilter);
                movieFilterStack.push(movieFilterChain);
            }
        }

    }
}
