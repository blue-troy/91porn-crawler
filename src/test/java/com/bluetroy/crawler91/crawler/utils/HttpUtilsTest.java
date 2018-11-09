package com.bluetroy.crawler91.crawler.utils;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-11-01
 * Time: 11:54 AM
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Log4j2
public class HttpUtilsTest {

    @Test
    public void getConnection() throws IOException {
        HttpURLConnection connection = HttpUtils.getConnection("http://91porn.com/index.php");
        log.info("getInFuture code ", connection.getResponseCode());
        Map<String, List<String>> headerFields = connection.getHeaderFields();
        for (Map.Entry<String, List<String>> entry : headerFields.entrySet()) {
            log.info("key = {} ,value = {}", entry.getKey(), entry.getValue().toString());
        }
    }
}