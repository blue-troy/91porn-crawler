package com.bluetroy.crawler91.dao;

import com.bluetroy.crawler91.dao.entity.DownloadErrorInfo;
import com.bluetroy.crawler91.dao.entity.Movie;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-08-23
 * Time: 下午7:25
 */
@Component
@Log4j2
public class Persistence implements Persistability {

    @Override
    public void init(Repository repository) {
        log.info("初始化repository数据");
        try {
            initDataFromFile(repository);
        } catch (IOException | ClassNotFoundException e) {
            log.warn("无法从文件中读取初始化信息", e);
            initDataWithEmpty(repository);
        }
    }

    private void initDataWithEmpty(Repository repository) {
        repository.scannedMovies = new ConcurrentHashMap<String, Boolean>();
        repository.filteredMovies = new ConcurrentHashMap<String, Boolean>();
        repository.toDownloadMovies = new LinkedBlockingDeque<String>();
        repository.downloadedMovies = new ConcurrentHashMap<String, String>();
        repository.downloadError = new ConcurrentHashMap<String, DownloadErrorInfo>();
        repository.movieData = new ConcurrentHashMap<String, Movie>();
    }

    private void initDataFromFile(Repository repository) throws IOException, ClassNotFoundException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("crawler91.dat"))) {
            Repository repositoryGet = (Repository) inputStream.readObject();
            repository.scannedMovies = repositoryGet.scannedMovies;
            repository.filteredMovies = repositoryGet.filteredMovies;
            repository.toDownloadMovies = repositoryGet.toDownloadMovies;
            repository.downloadedMovies = repositoryGet.downloadedMovies;
            repository.downloadError = repositoryGet.downloadError;
            repository.movieData = repositoryGet.movieData;
        }
    }

    @Override
    public void save(Serializable serializable) {
        log.info("马上要被销毁了 要把数据存下来");
        try (ObjectOutputStream outPutStream = new ObjectOutputStream(new FileOutputStream("crawler91.dat"));
        ) {
            outPutStream.writeObject(serializable);
        } catch (IOException e) {
            log.warn("数据保存失败", e);
        }
    }
}
