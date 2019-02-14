package club.bluetroy.crawler.dao;

import club.bluetroy.crawler.dao.entity.DownloadErrorInfo;
import club.bluetroy.crawler.domain.Movie;
import club.bluetroy.crawler.domain.MovieStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2019-02-14
 * Time: 15:25
 */
@Component
public class MockDao implements BaseDao {
    @Override
    public List<Movie> listMoviesByStatus(MovieStatus movieStatus) {
        return null;
    }

    @Override
    public Movie getByKey(String key) {
        return null;
    }

    @Override
    public void updateFilteredMoviesByKeys(List<String> keys) {

    }

    @Override
    public void saveDownloadUrl(String key, String downloadUrl) {

    }

    @Override
    public void saveScannedMovies(List<Movie> movies) {

    }

    @Override
    public void saveScannedMovie(Movie movie) {

    }

    @Override
    public void saveDownloadError(String key) {

    }

    @Override
    public void saveDownloadedMovies(String key) {

    }

    @Override
    public int countMovies() {
        return 0;
    }

    @Override
    public ConcurrentHashMap<String, DownloadErrorInfo> getDownloadError() {
        return null;
    }
}
