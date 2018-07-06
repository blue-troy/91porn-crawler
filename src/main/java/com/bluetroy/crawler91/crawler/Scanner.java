package com.bluetroy.crawler91.crawler;

import com.bluetroy.crawler91.utils.HttpRequestor;
import com.bluetroy.crawler91.repository.CrawlerList;
import com.bluetroy.crawler91.repository.Movie;
import org.jsoup.nodes.Node;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.seimicrawler.xpath.exception.XpathSyntaxErrorException;

import java.util.List;

import static com.bluetroy.crawler91.repository.CrawlerList.*;

public class Scanner {
    public boolean crawHotMovieList() throws Exception {
        String content = HttpRequestor.get("http://91porn.com/v.php?category=hot&viewtype=basic");
        JXDocument doc = JXDocument.create(content);
        List<JXNode> rs = doc.selN("//div[@id='videobox']/table//div[@class='listchannel']");
        for (JXNode r : rs) {
            Movie movie = getMovie(r);
            CrawlerList.setScannedMovie(movie);
        }
        return true;
    }

    private Movie getMovie(JXNode r) throws XpathSyntaxErrorException {
        List<Node> nodes = r.getElement().childNodes();
        Movie movie = new Movie().setTitle(r.sel(".//a[@target='blank']/@title").get(1).getTextVal())
                .setDetailURL(r.sel(".//a[@target='blank']/@href").get(0).getTextVal())
                .setLength(nodes.get(8).toString())
                .setAddTime(nodes.get(12).toString())
                .setAuthor(nodes.get(16).toString())
                .setView(nodes.get(20).toString())
                .setCollect(nodes.get(22).toString())
                .setMessageNumber(nodes.get(26).toString())
                .setIntegration(nodes.get(28).toString());
        System.out.println(movie.toString());
        return movie;
    }

    public boolean crawMovieDetail() {
        FILTED_MOVIES.forEach(5, (k, v) -> {
            if (v) return;
            Movie movie = MOVIE_DATA.get(k);
            String content = null;
            try {
                content = HttpRequestor.get(movie.getDetailURL());
                JXDocument doc = JXDocument.create(content);
                List<JXNode> rs = doc.selN("//source");
                String src = rs.get(0).getElement().attributes().get("src");
                movie.setDownloadURL(src.trim());
                TO_DOWNLOAD_MOVIES.offer(k);
                FILTED_MOVIES.replace(k, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return true;
    }
}
