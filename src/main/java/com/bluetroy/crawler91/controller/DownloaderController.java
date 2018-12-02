package com.bluetroy.crawler91.controller;

import com.bluetroy.crawler91.crawler.Crawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private Crawler crawler;

    @PatchMapping("/{key}")
    public Object download(@PathVariable String key) throws ExecutionException, InterruptedException {
        return crawler.downloadByKey(key).get();
    }
}
