package com.bluetroy.crawler91.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
@Aspect
@Component
@Log4j2
public class PersistenceAspect {

    private static final String DATA_FILE = "crawler91.dat";
    private static final String TEST_RUNNING_MODE = "test";
    @Value("${running.mode}")
    private String runningMode;

    @After("execution(void com.bluetroy.crawler91.crawler.dao.Persistence.save(*))")
    public void cleanTestFile() {
        if (TEST_RUNNING_MODE.equals(runningMode)) {
            log.info("开始清理测试文件");
            deleteFile(new File(DATA_FILE));
            for (File file : Objects.requireNonNull(new File(".").listFiles())) {
                if (file.getName().endsWith(".mp4")) {
                    deleteFile(file);
                }
            }
        }
    }

    private void deleteFile(File file) {
        if (file.delete()) {
            log.info("成功删除 :" + file.getName());
        } else {
            log.info("删除失败 :" + file.getName());
        }
    }
}
