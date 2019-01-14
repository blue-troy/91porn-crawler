package club.bluetroy.crawler.ui;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2019-01-14
 * Time: 17:11
 */
public class NativeUiTest {

    @Test
    public void getPath() {
        String path = NativeUi.getPath();
        assertTrue(Files.isDirectory(Paths.get(path)));
    }
}