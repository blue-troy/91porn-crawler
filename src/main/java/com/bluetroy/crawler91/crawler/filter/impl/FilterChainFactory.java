package com.bluetroy.crawler91.crawler.filter.impl;

import com.bluetroy.crawler91.crawler.filter.MovieFilterChain;
import com.bluetroy.crawler91.vo.FilterVO;

/**
 * @author heyixin
 */
public class FilterChainFactory {
    public static MovieFilterChain getShowFaceCollectFilterChain(Integer collectNum) {
        return new MovieFilterChain()
                .addFilter(new TitleMovieFilter("露脸"))
                .addFilter(new CollectMovieFilter(collectNum));
    }

    //todo 其他的Filter
    public static MovieFilterChain getFilter(FilterVO filterVO) {
        MovieFilterChain chain = new MovieFilterChain();
        if (null != filterVO.getTitle() && !filterVO.getTitle().isEmpty()) {
            chain.addFilter(new TitleMovieFilter(filterVO.getTitle()));
        }
        if (null != filterVO.getCollect()) {
            chain.addFilter(new CollectMovieFilter(filterVO.getCollect()));
        }
        return chain;
    }
}
