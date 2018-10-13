package com.bluetroy.crawler91.aspect;

import com.bluetroy.crawler91.controller.WebSocketController;
import com.bluetroy.crawler91.crawler.dao.Repository;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description: 统计包括：扫描视频的数量，filter 结果，download结果 的数据，并通知webSocket通知前端口
 *
 * @author: heyixin
 * Date: 2018-10-13
 * Time: 1:41 AM
 */
@Aspect
@Component
public class StatisticsAspect {
    @Autowired
    Repository repository;
    @Autowired
    WebSocketController webSocketController;

    //todo 应该考虑crawler包的闭包问题

    /**
     * 特例测试：统计扫描视频的数量并通知webSocket通知前端
     */
    @After("execution(* com.bluetroy.crawler91.crawler.Scanner.scanMovies())")
    public void gatherStatistics() throws IOException {
        webSocketController.send("/scannedMovies/count", repository.getScannedMovies().size());
    }
}
