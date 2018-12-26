package club.bluetroy.web.controller;

import club.bluetroy.crawler.Downloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-12-02
 * Time: 13:57
 */
@RestController
@RequestMapping("/download")
public class DownloaderController {
    @Autowired
    private Downloader downloader;

    @PatchMapping("/{key}")
    public Object download(@PathVariable String key) throws ExecutionException, InterruptedException {
        return downloader.downloadByKey(key).get();
    }

    @PatchMapping("/path")
    public void setPath(String path) {
        downloader.setResource(Paths.get(path));
    }
}
