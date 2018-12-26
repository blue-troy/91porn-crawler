package club.bluetroy.service;

import club.bluetroy.crawler.vo.ProxyInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-11-07
 * Time: 5:32 PM
 */
@Slf4j
@Component
public class ProxyService {
    static {
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "1087");
    }

    public void setProxy(ProxyInfo proxyInfo) {
        System.setProperty("http.proxyHost", proxyInfo.getProxyHost());
        System.setProperty("http.proxyPort", proxyInfo.getProxyPort());
        log.info("set proxy {}:{}", proxyInfo.getProxyHost(), proxyInfo.getProxyPort());
    }
}
