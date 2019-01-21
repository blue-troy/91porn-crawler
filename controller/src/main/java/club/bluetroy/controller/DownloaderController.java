package club.bluetroy.controller;

import club.bluetroy.crawler.Downloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
@Controller
@RequestMapping("/download")
public class DownloaderController {
    @Autowired
    private Downloader downloader;

    @PatchMapping("/{key}")
    public @ResponseBody
    Object download(@PathVariable String key) throws ExecutionException, InterruptedException {
        return downloader.downloadByKey(key).get();
    }

    @GetMapping("/path")
    public String getPath() {
        return "forward:/native/dirChooser";
    }

    @PatchMapping("/path")
    public @ResponseBody
    void setPath(@RequestBody String path) {
        downloader.setResource(Paths.get(path));
    }
}
