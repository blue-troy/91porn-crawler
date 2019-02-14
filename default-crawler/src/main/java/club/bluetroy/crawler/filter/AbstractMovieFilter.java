package club.bluetroy.crawler.filter;

import club.bluetroy.crawler.domain.Movie;

import java.util.List;

/**
 * @author heyixin
 */
public abstract class AbstractMovieFilter {
    /**
     * 显示过滤器的内容
     *
     * @return 过滤器的内容
     */
    @Override
    public abstract String toString();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();

    /**
     * 过滤map中不需要的元素，用于过滤扫描出的视频列表
     *
     * @param tobeFilter 传入一个要被过滤的map
     */
    public abstract void doFilter(List<Movie> tobeFilter);
}
