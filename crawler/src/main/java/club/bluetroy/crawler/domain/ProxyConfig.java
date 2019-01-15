package club.bluetroy.crawler.domain;

import lombok.Data;

import javax.validation.constraints.Pattern;


/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-11-15
 * Time: 5:25 PM
 */

@Data
public class ProxyConfig {
    @Pattern(regexp = "((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))", message = "请输入正确的代理地址")
    private String proxyHost;
    @Pattern(regexp = "([0-9]|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-4]\\d{4}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])")
    private String proxyPort;
}
