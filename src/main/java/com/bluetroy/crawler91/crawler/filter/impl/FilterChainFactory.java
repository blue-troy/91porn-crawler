package com.bluetroy.crawler91.crawler.filter.impl;

import com.bluetroy.crawler91.crawler.filter.Filter;
import com.bluetroy.crawler91.crawler.filter.FilterChain;
import com.bluetroy.crawler91.crawler.filter.impl.filter.CollectFilter;
import com.bluetroy.crawler91.crawler.filter.impl.filter.ShowFaceFilter;

public class FilterChainFactory {
    public static Filter getShowFaceCollectFilterChain(Integer collectNum) {
        return new FilterChain()
                .addFilter(new ShowFaceFilter())
                .addFilter(new CollectFilter(collectNum));
    }
}
