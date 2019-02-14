package club.bluetroy.crawler.dao;

import club.bluetroy.crawler.dao.entity.DownloadErrorInfo;
import club.bluetroy.crawler.domain.Movie;
import club.bluetroy.crawler.domain.MovieStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2019-02-14
 * Time: 21:27
 */
@Primary
@Component
public class JpaDao implements BaseDao {
    @Autowired
    private MovieRepository repository;

    @Override
    public List<Movie> listMoviesByStatus(MovieStatus movieStatus) {
        return repository.findAllByStatus(movieStatus);
    }

    @Override
    public Movie getByKey(String key) {
        return repository.findByKey(key);
    }

    @Override
    public int updateFilteredMoviesByKeys(List<String> keys) {
        return repository.updateFilteredByKeys(keys);
    }

    @Override
    public int saveDownloadUrl(String key, String downloadUrl) {
        return repository.updateDownloadUrlByKey(downloadUrl, key);
    }

    @Override
    public Iterable<Movie> saveScannedMovies(List<Movie> movies) {
        return repository.saveAll(movies);
    }

    @Override
    public Movie saveScannedMovie(Movie movie) {
        return repository.save(movie);
    }

    @Override
    public void saveDownloadError(String key) {

    }

    @Override
    public int saveDownloadedMovies(String key) {
        return repository.updateDownloadedMovieByKey(key);
    }

    @Override
    public long countMovies() {
        return repository.count();
    }

    @Override
    public ConcurrentHashMap<String, DownloadErrorInfo> getDownloadError() {
        return null;
    }
}
