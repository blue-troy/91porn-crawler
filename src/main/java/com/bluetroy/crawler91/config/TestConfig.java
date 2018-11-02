package com.bluetroy.crawler91.config;

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
public class TestConfig {

    private static final String TEST_RUNNING_MODE = "test";
    @Value("${running.mode}")
    String runningMode;

    @PreDestroy
    public void cleanTestFile() {
        if (TEST_RUNNING_MODE.equals(runningMode)) {
            new File("crawler91.dat").delete();
            File[] files = new File("").listFiles();
            for (File file : Objects.requireNonNull(files)) {
                if (file.getName().endsWith("mp4")) {
                    file.delete();
                }
            }
        }
    }

}
