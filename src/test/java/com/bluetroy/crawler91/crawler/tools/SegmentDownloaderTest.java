package com.bluetroy.crawler91.crawler.tools;

import com.bluetroy.crawler91.crawler.utils.SegmentDownloader;
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
        String url = "http://192.240.120.34//mp43/289045.mp4?st=dNXe_8INFR16RVfR6ts4Cw&e=1541646059";
        String fileName = "一边给老公娇喘，一边被我操！她老公浑然不知自己女人在挨操（解决卡顿极.mp4";
        SegmentDownloader.download(url, fileName);
    }
}