package club.bluetroy.controller;

import club.bluetroy.crawler.dao.MovieRepository;
import club.bluetroy.crawler.domain.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Collections;

import static junit.framework.TestCase.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2019-02-14
 * Time: 15:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Transactional
public class MovieRepositoryTest {
    @Autowired
    private MovieRepository repository;
    private String key = "111111111111";
    private Movie movie;

    //@Before
    public void setUp() {
        movie = new Movie("布里斯班小姐姐", "00:43", "2019年2月11日21时", "澳洲小哥哥可国内", 22995, 172, 2, 0, "http://91porn.com/view_video.php?viewkey=2ae67ee3fde76ac96077&page=1&viewtype=basic&category=hot");
        repository.save(movie);
    }

    @Test
    public void testUpdateFilteredByKeys() {
        String key = "2ae67ee3fde76ac96077";
        int updateCount = repository.updateFilteredByKeys(Collections.singletonList(key));
        assertEquals(1, updateCount);
    }

    @Test
    public void testSave() {
        movie = new Movie("布里斯班小姐姐", "00:43", "2019年2月11日21时", "澳洲小哥哥可国内", 22995, 172, 2, 0, "http://91porn.com/view_video.php?viewkey=2ae67ee3fde76ac96077&page=1&viewtype=basic&category=hot");
        Movie save = repository.save(movie);
        assertEquals(movie.getKey(), save.getKey());
    }

}
