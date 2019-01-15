package club.bluetroy.crawler.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-08-23
 * Time: 下午7:25
 */
@Slf4j
@Order(1)
@Component
class Persistence implements Persistability, CommandLineRunner {
    @Autowired
    private PersistentDao dao;

    @Override
    public void initialize(Persistability persistability) {
        init((PersistentDao) persistability);
    }

    private void init(PersistentDao persistentDao) {
        log.info("初始化repository数据");
        try {
            initDataFromFile(persistentDao);
        } catch (IOException | ClassNotFoundException e) {
            log.warn("无法从文件中读取repository初始化信息");
        }
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

    @Override
    public void persistence(Persistability persistability) {
        log.info("马上要被销毁了 要把数据存下来");
        try (ObjectOutputStream outPutStream = new ObjectOutputStream(new FileOutputStream("crawler91.dat"))
        ) {
            outPutStream.writeObject(persistability);
        } catch (IOException e) {
            log.warn("数据保存失败", e);
        }
    }

    @PreDestroy
    public void save() {
        dao.persistence(dao);
    }

    @Override
    public void run(String... args) throws Exception {
        dao.initialize(dao);
    }
}
