package com.bluetroy.crawler91.crawler.dao;

import com.bluetroy.crawler91.crawler.dao.entity.DownloadErrorInfo;
import com.bluetroy.crawler91.crawler.dao.entity.Movie;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
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
@Log4j2
@Order(1)
@Component
class Persistence implements Persistability, CommandLineRunner {
    @Autowired
    private PersistentDao dao;

    @Override
    public void init(Persistability persistability) {
        init((PersistentDao) persistability);
    }

    @Override
    public void save(Persistability persistability) {
        log.info("马上要被销毁了 要把数据存下来");
        try (ObjectOutputStream outPutStream = new ObjectOutputStream(new FileOutputStream("crawler91.dat"));
        ) {
            outPutStream.writeObject(persistability);
        } catch (IOException e) {
            log.warn("数据保存失败", e);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        dao.init();
    }

    private void init(PersistentDao persistentRepository) {
        log.info("初始化repository数据");
        try {
            initDataFromFile(persistentRepository);
        } catch (IOException | ClassNotFoundException e) {
            log.warn("无法从文件中读取repository初始化信息");
            initDataWithEmpty(persistentRepository);
        }
    }

    private void initDataWithEmpty(PersistentDao persistentRepository) {
        log.warn("repository数据初始化为空");
        persistentRepository.scannedMovies = new ConcurrentHashMap<String, Boolean>();
        persistentRepository.filteredMovies = new ConcurrentHashMap<String, Boolean>();
        persistentRepository.toDownloadMovies = new LinkedBlockingDeque<String>();
        persistentRepository.downloadedMovies = new ConcurrentHashMap<String, String>();
        persistentRepository.downloadError = new ConcurrentHashMap<String, DownloadErrorInfo>();
        persistentRepository.movieData = new ConcurrentHashMap<String, Movie>();
    }

    private void initDataFromFile(PersistentDao persistentRepository) throws IOException, ClassNotFoundException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("crawler91.dat"))) {
            PersistentDao persistentRepositoryGet = (PersistentDao) inputStream.readObject();
            persistentRepository.scannedMovies = persistentRepositoryGet.scannedMovies;
            persistentRepository.filteredMovies = persistentRepositoryGet.filteredMovies;
            persistentRepository.toDownloadMovies = persistentRepositoryGet.toDownloadMovies;
            persistentRepository.downloadedMovies = persistentRepositoryGet.downloadedMovies;
            persistentRepository.downloadError = persistentRepositoryGet.downloadError;
            persistentRepository.movieData = persistentRepositoryGet.movieData;
        }
    }
}
