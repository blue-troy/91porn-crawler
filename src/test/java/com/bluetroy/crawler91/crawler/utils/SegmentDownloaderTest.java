package com.bluetroy.crawler91.crawler.utils;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-10-27
 * Time: 11:43 AM
 */
public class SegmentDownloaderTest {

    @Test
    public void download() throws Exception {
        SegmentDownloader.download("http://ws4.sinaimg.cn/large/6b5a0580ly1fwiddba2x1j20g81iuju9.jpg");
    }
}