package club.bluetroy.crawler.dao;

import club.bluetroy.crawler.domain.Movie;
import club.bluetroy.crawler.domain.MovieStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author heyixin
 * Date: 2019-02-14
 * Time: 15:26
 */
public interface MovieRepository extends CrudRepository<Movie, Long> {

    Movie findByKey(String key);

    List<Movie> findAllByStatus(MovieStatus status);

    @Transactional
    @Modifying
    @Query("update Movie m set m.status = :status where m.key in :keys")
    int updateStatusByKeys( List<String> keys,MovieStatus status);

    @Transactional
    @Modifying
    @Query("update Movie m set m.status = :status where m.key = :key")
    int updateStatusByKey(String key, MovieStatus status);

    @Transactional
    @Modifying
    @Query("update Movie m set m = :movie where m.key = :#{#movie.key}")
    int updateByKey(Movie movie);

    @Transactional
    @Modifying
    @Query("update Movie m set m.collect =:#{#movie.collect} ,m.messageNumber=:#{#movie.messageNumber},m.view = :#{#movie.view},m.integration = :#{#movie.integration}  where m.key = :#{#movie.key}")
    void updateCollectMessageNumberViewIntegrationByKey(Movie movie);

    @Transactional
    @Modifying
    @Query("update Movie  m set m.downloadUrl= :downloadUrl where m.key = :key")
    int updateDownloadUrlByKey(String downloadUrl, String key);

    @Transactional
    @Modifying
    @Query("update Movie  m set m.status='FILTERED' where m.key in :keys")
    int updateFilteredByKeys(List<String> keys);

    @Transactional
    @Modifying
    @Query("update Movie  m set m.status='DOWNLOADED' where m.key = :key")
    int updateDownloadedMovieByKey(String key);

    boolean existsByKey(String key);
}
