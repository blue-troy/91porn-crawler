package com.bluetroy.crawler91.crawler.filter.impl;

import com.bluetroy.crawler91.crawler.filter.MovieFilterChain;
import com.bluetroy.crawler91.crawler.utils.TimeUtils;
import com.bluetroy.crawler91.vo.FilterVO;

import java.time.LocalDateTime;

/**
 * @author heyixin
 */
public class FilterChainFactory {
    public static MovieFilterChain getDefaultFilter() {
        return getHotShowFaceFilter();
    }

    //todo 其他的Filter
    public static MovieFilterChain getFilter(FilterVO filterVO) {
        MovieFilterChain chain = new MovieFilterChain();
        handleTitleFilter(filterVO, chain);
        handleCollectFilter(filterVO, chain);
        handleAddTimeFilter(filterVO, chain);
        return chain;
    }

    private static void handleAddTimeFilter(FilterVO filterVO, MovieFilterChain chain) {
        String addTimeBeforeString = filterVO.getAddTimeBefore();
        String addTimeAfterString = filterVO.getAddTimeAfter();
        LocalDateTime addTimeBefore = null;
        LocalDateTime addTimeAfter = null;
        if (addTimeAfterString != null) {
            try {
                addTimeBefore = TimeUtils.parse(TimeUtils.getDateByTimeBefore(addTimeBeforeString));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (addTimeAfterString != null) {
            try {
                addTimeAfter = TimeUtils.parse(TimeUtils.getDateByTimeBefore(addTimeAfterString));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (addTimeAfter != null || addTimeBefore != null) {
            chain.addFilter(new AddTimeMovieFilter(addTimeBefore, addTimeAfter));
        }
    }

    private static void handleCollectFilter(FilterVO filterVO, MovieFilterChain chain) {
        if (null != filterVO.getCollect()) {
            chain.addFilter(new CollectMovieFilter(filterVO.getCollect()));
        }
    }

    private static void handleTitleFilter(FilterVO filterVO, MovieFilterChain chain) {
        if (null != filterVO.getTitle() && !filterVO.getTitle().isEmpty()) {
            chain.addFilter(new TitleMovieFilter(filterVO.getTitle()));
        }
    }

    private static MovieFilterChain getHotShowFaceFilter() {
        FilterVO filterVO = new FilterVO();
        filterVO.setTitle("露脸");
        filterVO.setCollect(200);
        filterVO.setAddTimeAfter("1天前");
        return getFilter(filterVO);
    }
}
