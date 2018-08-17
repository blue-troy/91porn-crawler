package com.bluetroy.crawler91.controller;

import com.bluetroy.crawler91.dao.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-08-17
 * Time: 下午2:38
 */
@Controller
@RequestMapping("/info")
public class InfoController {
    @Autowired
    WebSocketController webSocketController;

    @RequestMapping("/get")
    public void getInfo() throws IOException {
        webSocketController.send("/info/get", Repository.getToDownloadMoviesMap());
    }
}
