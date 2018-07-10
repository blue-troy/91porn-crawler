package com.bluetroy.crawler91.utils;

import org.junit.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-07-10
 * Time: 下午7:05
 */
public class HttpRequesterTest {

    @Test
    public void download() {
        try {
            HttpRequester.download("http://ww3.sinaimg.cn/mw600/006XNEY7gy1ft3qpemh72j317e2a17wh.jpg", "中超.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}