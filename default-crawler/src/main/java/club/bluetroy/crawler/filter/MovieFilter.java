package club.bluetroy.crawler.filter;

import club.bluetroy.crawler.domain.Movie;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author heyixin
 */
public interface MovieFilter {
    /**
     * 过滤map中不需要的元素，用于过滤扫描出的视频列表
     *
     * @param tobeFilter 传入一个要被过滤的map
     */
    void doFilter(ConcurrentHashMap<String, Movie> tobeFilter);

    /**
     * 显示过滤器的内容
     *
     * @return 过滤器的内容
     */
    @Override
    String toString();
}
