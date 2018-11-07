package com.bluetroy.crawler91.crawler.filter;

import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-11-07
 * Time: 12:44 PM
 */
public class FilterUpdateListenerTest {

    @Test
    public void test() {
        ConcurrentHashMap<Integer, Boolean> map = new ConcurrentHashMap<Integer, Boolean>();
        for (int i = 0; i < 20; i++) {
            map.put(i, true);
        }
    }

}