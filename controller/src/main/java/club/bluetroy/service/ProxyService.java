package club.bluetroy.service;

import club.bluetroy.crawler.domain.ProxyConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author heyixin
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

    public void setProxy(ProxyConfig proxyConfig) {
        System.setProperty("http.proxyHost", proxyConfig.getProxyHost());
        System.setProperty("http.proxyPort", proxyConfig.getProxyPort());
        log.info("set proxy {}:{}", proxyConfig.getProxyHost(), proxyConfig.getProxyPort());
    }
}
