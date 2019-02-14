package club.bluetroy.crawler.dao;

import club.bluetroy.crawler.domain.Movie;
import club.bluetroy.crawler.domain.MovieStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2019-02-14
 * Time: 15:26
 */
public interface MovieRepository extends CrudRepository<Movie, Long> {

    Movie findByKey(String key);

    List<Movie> findAllByStatus(MovieStatus status);

    @Transactional
    @Modifying
    @Query("update Movie  m set m.status='FILTERED' where m.key in :keys")
    int updateFilteredByKeys(List<String> keys);
}
