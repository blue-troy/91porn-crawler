package com.bluetroy.crawler91.crawler.impl.filter.impl;

import com.bluetroy.crawler91.crawler.impl.filter.FilterChain;
import com.bluetroy.crawler91.crawler.impl.filter.impl.filter.CollectFilter;
import com.bluetroy.crawler91.crawler.impl.filter.impl.filter.TitleFilter;
import com.bluetroy.crawler91.vo.FilterVO;

/**
 * @author heyixin
 */
public class FilterChainFactory {
    public static FilterChain getShowFaceCollectFilterChain(Integer collectNum) {
        return new FilterChain()
                .addFilter(new TitleFilter("露脸"))
                .addFilter(new CollectFilter(collectNum));
    }

    //todo 其他的Filter
    public static FilterChain getFilter(FilterVO filterVO) {
        FilterChain chain = new FilterChain();
        if (null != filterVO.getTitle() && !filterVO.getTitle().isEmpty()) {
            chain.addFilter(new TitleFilter(filterVO.getTitle()));
        }
        if (null != filterVO.getCollect()) {
            chain.addFilter(new CollectFilter(filterVO.getCollect()));
        }
        return chain;
    }
}
