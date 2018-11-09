package com.bluetroy.crawler91.crawler.impl.utils;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-11-07
 * Time: 3:17 PM
 */
public class SegmentDownloaderTest {
    @Test
    public void download() throws Exception {
        String url = "http://185.38.13.130//mp43/288972.mp4?st=qDvoY9O3_8raA0gDT9A-jA&e=1541649489";
        String fileName = "test.mp4";
        SegmentDownloader.download(url, fileName);
    }
}