package club.bluetroy.crawler.persistence;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-11-01
 * Time: 12:26 PM
 */
//todo resource 地址是有可能修改的
@Aspect
@Component
@Slf4j
class PersistenceAspect {

    private static final String DATA_FILE = "crawler91.dat";
    private static final String TEST_RUNNING_MODE = "test";
    @Value("${running.mode}")
    private String runningMode;
    private Path resourcePath;

    {
        resourcePath = Paths.get(Objects.requireNonNull(this.getClass().getClassLoader().getResource("")).getPath());
    }

    @After("execution(void club.bluetroy.crawler.dao.Persistability.persistence(club.bluetroy.crawler.dao.Persistability))")
    public void cleanTestFile() {
        if (TEST_RUNNING_MODE.equals(runningMode)) {
            log.info("开始清理测试文件");
            deleteFile(new File(DATA_FILE));
            for (File file : Objects.requireNonNull(resourcePath.toFile().listFiles())) {
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
