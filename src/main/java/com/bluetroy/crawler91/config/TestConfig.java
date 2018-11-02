package com.bluetroy.crawler91.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.File;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-11-01
 * Time: 12:26 PM
 */
@Component
@Log4j2
public class TestConfig {

    private static final String TEST_RUNNING_MODE = "test";
    @Value("${running.mode}")
    String runningMode;

    @PreDestroy()
    public void cleanTestFile() {
        if (TEST_RUNNING_MODE.equals(runningMode)) {
            log.info("开始清理测试文件");
            new File("crawler91.dat").delete();
            for (File file : Objects.requireNonNull(new File(".").listFiles())) {
                if (file.getName().endsWith(".mp4")) {
                    file.delete();
                }
            }
        }
    }

}
