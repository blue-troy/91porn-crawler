package club.bluetroy.crawler;

import club.bluetroy.crawler.domain.FilterConfig;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-11-09
 * Time: 2:44 PM
 */
public interface Filter {
    /**
     * 令过滤器过滤扫描到的视频信息，并将结果存入数据仓库
     */
    void doFilter();

    /**
     * 获取过滤器信息
     *
     * @return 过滤器的相关信息
     */
    String getFilterInfo();

    /**
     * 设置过滤器
     *
     * @param filter 过滤器的值类
     */
    void setFilter(FilterConfig filter);
}
