package club.bluetroy.crawler.dao;

import club.bluetroy.crawler.dao.entity.DownloadErrorInfo;
import club.bluetroy.crawler.domain.Movie;
import club.bluetroy.crawler.domain.MovieStatus;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author heyixin
 * Date: 2019-02-14
 * Time: 21:27
 */
@Primary
@Component
public class JpaDao implements BaseDao {
    private final MovieRepository repository;

    public JpaDao(MovieRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Movie> listMoviesByStatus(MovieStatus movieStatus) {
        return repository.findAllByStatus(movieStatus);
    }

    @Override
    public Movie getByKey(String key) {
        return repository.findByKey(key);
    }

    @Override
    public int updateStatus(String key, MovieStatus movieStatus) {
        return repository.updateStatusByKey(key, movieStatus);
    }

    @Override
    public int updateStatus(List<String> keys, MovieStatus movieStatus) {
        return repository.updateStatusByKeys(keys, movieStatus);
    }


    @Override
    public int saveDownloadUrl(String key, String downloadUrl) {
        return repository.updateDownloadUrlByKey(downloadUrl, key);
    }

    @Override
    public void saveScannedMovies(List<Movie> movies) {
        movies.forEach(this::saveScannedMovie);
    }

    @Override
    public void saveScannedMovie(Movie movie) {
        if (repository.existsByKey(movie.getKey())) {
            repository.updateCollectMessageNumberViewIntegrationByKey(movie);
        } else {
            repository.save(movie);
        }
    }

    @Override
    public void saveDownloadError(String key) {

    }

    //todo 保存下载成功信息
    @Override
    public int saveDownloadedMovies(String key) {
        return repository.updateStatusByKey(key, MovieStatus.DOWNLOADED);
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
