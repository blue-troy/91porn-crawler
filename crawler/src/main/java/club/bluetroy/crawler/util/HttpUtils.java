package club.bluetroy.crawler.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-10-29
 * Time: 10:20 AM
 */
public class HttpUtils {
    private static final int THIRTY_SECONDS = 30 * 1000;
    private static final int TEN_MINUTE = 10 * 60 * 1000;

    static {
        CookieUtils.init();
    }


    public static HttpURLConnection getConnection(String url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
        httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
        httpURLConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (iPad; CPU OS 11_0 like Mac OS X) AppleWebKit/604.1.34 (KHTML, like Gecko) Version/11.0 Mobile/15A5341f Safari/604.1");
        httpURLConnection.setConnectTimeout(THIRTY_SECONDS);
        httpURLConnection.setReadTimeout(THIRTY_SECONDS);
        return httpURLConnection;
    }

    public static HttpURLConnection getDownloadConnection(String url) throws IOException {
        HttpURLConnection httpURLConnection = getConnection(url);
        httpURLConnection.setReadTimeout(TEN_MINUTE);
        httpURLConnection.setConnectTimeout(TEN_MINUTE);
        return httpURLConnection;
    }
}
