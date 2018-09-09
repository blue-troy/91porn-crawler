package com.bluetroy.crawler91.controller;

import com.bluetroy.crawler91.dao.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-08-17
 * Time: 下午2:38
 */
@RestController
@RequestMapping("/info")
public class InfoController {
    @Autowired
    WebSocketController webSocketController;
    @Autowired
    Repository repository;

    @RequestMapping("/get")
    public String getInfo() throws IOException {
        webSocketController.send("/filteredMovies/get", repository.getFilteredMoviesMap());
        return "success";
    }
}
