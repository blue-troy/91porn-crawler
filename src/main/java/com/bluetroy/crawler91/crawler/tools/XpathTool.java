package com.bluetroy.crawler91.crawler.tools;

import com.bluetroy.crawler91.crawler.dao.Repository;
import com.bluetroy.crawler91.crawler.dao.entity.KeyContent;
import com.bluetroy.crawler91.crawler.dao.entity.Movie;
import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Node;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.seimicrawler.xpath.exception.XpathSyntaxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-07-11
 * Time: 下午2:06
 */
@Log4j2
@Component
public class XpathTool {
    private static final String MOVIE_LIST_CHANNEL_XPATH = "//div[@id='videobox']/table//div[@class='listchannel']";
    private static final String TITLE_IN_MOVIE_XPATH = ".//a[@target='blank']/@title";
    private static final String DETAIL_URL_IN_MOVIE_XPATH = ".//a[@target='blank']/@href";
    private static final String DOWNLOAD_URL_XPATH = "//source";
    @Autowired
    private Repository repository;

    private Movie getMovie(JXNode r) throws XpathSyntaxErrorException {
        //todo 空异常
        List<Node> nodes = r.getElement().childNodes();
        Movie movie = new Movie();
        movie.setTitle(r.sel(TITLE_IN_MOVIE_XPATH).get(1).getTextVal().replaceAll("\\s*", ""))
                .setDetailURL(r.sel(DETAIL_URL_IN_MOVIE_XPATH).get(0).getTextVal().replaceAll("\\s*", ""))
                .setLength(nodes.get(8).toString().replaceAll("\\s*", ""))
                .setAddTime(nodes.get(12).toString().replaceAll("\\s*", ""))
                .setAuthor(nodes.get(16).toString().replaceAll("\\s*", ""))
                .setView(nodes.get(20).toString().replaceAll("\\s*", ""))
                .setCollect(nodes.get(22).toString().replaceAll("\\s*", ""))
                .setMessageNumber(nodes.get(26).toString().replaceAll("\\s*", ""))
                .setIntegration(nodes.get(28).toString().replaceAll("\\s*", ""));
        log.info("扫描到了视频：{} ", movie.toString());
        return movie;
    }


    private void scanDownloadUrlInDoc(JXDocument doc, KeyContent keyContent) throws XpathSyntaxErrorException {
        Movie movie = repository.getMovieData().get(keyContent.getKey());
        List<JXNode> rs = doc.selN(DOWNLOAD_URL_XPATH);
        //todo 如果得不到就会 java.lang.IndexOutOfBoundsException 可以增加一个判断是否被ban了
        String downloadURL = rs.get(0).getElement().attributes().get("src");
        movie.setDownloadURL(downloadURL);
        log.info("扫描到了 {} 的下载链接：{}", movie.getTitle(), movie.getDownloadURL());
        repository.addToDownloadMoviesByKey(movie.getKey());
    }

    public void scanDownloadUrl(KeyContent keyContent) {
        try {
            String content = keyContent.getContent().get();
            JXDocument doc = JXDocument.create(content);
            scanDownloadUrlInDoc(doc, keyContent);
        } catch (InterruptedException | ExecutionException | XpathSyntaxErrorException e) {
            Movie movie = repository.getMovieData().get(keyContent.getKey());
            log.warn("搜索不到 {} {} 的下载地址，应该是被ban了", movie.getTitle(), movie.getDetailURL(), e);
            //todo 应该得有被ban的策略
        }
    }

    public void setMovie(Future<String> future) {
        try {
            setMovie(future.get());
        } catch (InterruptedException | ExecutionException | XpathSyntaxErrorException e) {
            log.warn("网络访问错误", e);
        }
    }

    private void setMovie(String contentString) throws XpathSyntaxErrorException {
        JXDocument doc = JXDocument.create(contentString);
        List<JXNode> rs = doc.selN(MOVIE_LIST_CHANNEL_XPATH);
        for (JXNode r : rs) {
            try {
                Movie movie = getMovie(r);
                repository.setScannedMovie(movie);
            } catch (XpathSyntaxErrorException e) {
                log.warn("xpath 解析错误", e);
            }
        }
    }
}
