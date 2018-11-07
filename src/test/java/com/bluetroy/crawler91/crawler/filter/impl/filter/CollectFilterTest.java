package com.bluetroy.crawler91.crawler.filter.impl.filter;

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
 * Time: 11:41 AM
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CollectFilterTest {
    @Test
    public void toStringTest() {
        CollectFilter collectFilter = new CollectFilter(11);
        System.out.println(collectFilter.toString());
        CollectFilter collectFilter1 = new CollectFilter(11);
        System.out.println(collectFilter.equals(collectFilter1));
    }
}