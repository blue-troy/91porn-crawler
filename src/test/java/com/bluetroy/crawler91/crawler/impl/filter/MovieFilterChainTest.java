package com.bluetroy.crawler91.crawler.impl.filter;

import com.bluetroy.crawler91.crawler.filter.MovieFilterChain;
import com.bluetroy.crawler91.crawler.filter.impl.FilterChainFactory;
import com.bluetroy.crawler91.vo.FilterVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-11-07
 * Time: 11:57 AM
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MovieFilterChainTest {

    @Test
    public void equals() {
        MovieFilterChain showFaceCollectFilterChain = FilterChainFactory.getShowFaceCollectFilterChain(11);
        FilterVO filterVO = new FilterVO();
        filterVO.setCollect(11);
        filterVO.setTitle("露脸");
        MovieFilterChain filterChain = FilterChainFactory.getFilter(filterVO);
        System.out.println(showFaceCollectFilterChain);
        System.out.println(filterChain);
        System.out.println(showFaceCollectFilterChain.equals(filterChain));
    }
}