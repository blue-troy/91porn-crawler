package com.bluetroy.crawler91.command;

import com.bluetroy.crawler91.repository.Repository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-07-09
 * Time: 下午7:49
 */
@Component
@Log4j2
public class PreDestroyCommand {
    @PreDestroy
    public void destroy() {
        System.out.println("马上要被销毁了 要把数据存下来");
        try (
                ObjectOutputStream scannedMoviesOutputStream = new ObjectOutputStream(new FileOutputStream("SCANNED_MOVIES.dat"));
                ObjectOutputStream toDownloadMoviesOutputStream = new ObjectOutputStream(new FileOutputStream("TO_DOWNLOAD_MOVIES.dat"));
                ObjectOutputStream filteredMoviesOutputStream = new ObjectOutputStream(new FileOutputStream("FILTERED_MOVIES.dat"));
                ObjectOutputStream movieDataOutputStream = new ObjectOutputStream(new FileOutputStream("MOVIE_DATA.dat"));
                ObjectOutputStream downloadedMoviesOutputStream = new ObjectOutputStream(new FileOutputStream("DOWNLOADED_MOVIES.dat"));
                ObjectOutputStream downloadErrorOutputStream = new ObjectOutputStream(new FileOutputStream("DOWNLOAD_ERROR.dat"))
        ) {
            Repository.writeObject(scannedMoviesOutputStream, toDownloadMoviesOutputStream, filteredMoviesOutputStream, movieDataOutputStream, downloadedMoviesOutputStream, downloadErrorOutputStream);
        } catch (IOException e) {
            log.warn("数据保存失败",e);
        }
    }
}
