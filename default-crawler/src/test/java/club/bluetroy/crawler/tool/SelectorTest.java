package club.bluetroy.crawler.tool;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-12-16
 * Time: 07:32
 */
@Disabled
class SelectorTest {
    private static final String loginErrorHtmlFile = "/Users/heyixin/IdeaProjects/crawler91/src/main/resources/test/loginError.html";

    @Test
    void getLoginError() throws Exception {
        String loginResult = new String(Files.readAllBytes(Paths.get(loginErrorHtmlFile)));
        String loginError = new Selector().getLoginErrorMessage(loginResult);
        System.out.println(loginError);
    }

    @Test
    void select() throws IOException {
        Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
        System.out.println(doc.title());
        Elements newsHeadlines = doc.select("#mp-itn b a");
        for (Element headline : newsHeadlines) {
            System.out.println(headline.attr("title"));
            System.out.println(headline.absUrl("href"));
        }
    }

    @Test
    void getDownloadUrl() throws IOException {
        Document document = Jsoup.connect("http://91porn.com/view_video.php?viewkey=6c2ca4fb7e9f4df85f42").get();
        Element element = document.selectFirst("source");
        System.out.println(element.attr("src"));
    }

    @Test
    void getMovieList() throws IOException {
        Document document = Jsoup.connect("http://91porn.com/v.php?category=hot&viewtype=basic").get();
        Elements Movies = document.select("#videobox > table > tbody > tr > td > div.listchannel");
        for (Element movie : Movies) {
            getMovie(movie);
            break;
        }
    }

    @Test
    void getMovie() {
        String s = "158921&nbsp;";
        System.out.println(s.replaceAll("&nbsp;",""));
    }

    void getMovie(Element movie) {
        String name = movie.select("a").attr("title");
        String url = movie.select("a").attr("href");
        System.out.println(url);
        int index = 0;
        String s = "index 8  :  <span class=\"info\">Runtime:</span>\n" +
                "index 9  :  11:13 \n" +
                "index 10  :  <br>\n" +
                "index 11  :   \n" +
                "index 12  :  <span class=\"info\">Added:</span>\n" +
                "index 13  :   21 hours ago \n" +
                "index 14  :  <br>\n" +
                "index 15  :   \n" +
                "index 16  :  <span class=\"info\">From:</span>\n" +
                "index 17  :   samhuangsam\n" +
                "index 18  :  <br>\n" +
                "index 19  :   \n" +
                "index 20  :  <span class=\"info\">Views:</span>\n" +
                "index 21  :   65919&nbsp; \n" +
                "index 22  :  <span class=\"info\">Favorites:</span>\n" +
                "index 23  :   338\n" +
                "index 24  :  <br>\n" +
                "index 25  :   \n" +
                "index 26  :  <span class=\"info\">Comments:</span>\n" +
                "index 27  :   10&nbsp;\n" +
                "index 28  :  <span class=\"info\">Point:</span>\n" +
                "index 29  :   1000 \n" +
                "index 30  :  <br>";
    }
}