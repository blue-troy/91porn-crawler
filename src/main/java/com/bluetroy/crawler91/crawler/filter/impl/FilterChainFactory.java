package com.bluetroy.crawler91.crawler.filter.impl;

import com.bluetroy.crawler91.crawler.filter.Filter;
import com.bluetroy.crawler91.crawler.filter.FilterChain;
import com.bluetroy.crawler91.crawler.filter.impl.filter.CollectFilter;
import com.bluetroy.crawler91.crawler.filter.impl.filter.TitleFilter;

/**
 * @author heyixin
 */
public class FilterChainFactory {
    public static Filter getShowFaceCollectFilterChain(Integer collectNum) {
        return new FilterChain()
                .addFilter(new TitleFilter("露脸"))
                .addFilter(new CollectFilter(collectNum));
    }
}
