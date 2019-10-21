package club.bluetroy.crawler.tool;

import club.bluetroy.crawler.dao.entity.Category;
import lombok.experimental.UtilityClass;

/**
 * @author heyixin
 * Date: 2018-11-30
 * Time: 18:16
 */
@UtilityClass
public class PornUrl {
    private static String host = "http://91porn.com";

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        PornUrl.host = host;
    }

    public static String getUrl(Category category) {
        return host + "/v.php" + getCategoryQueryParam(category);
    }

    private static String getCategoryQueryParam(Category category) {
        return "?category=" + category.getCategory();
    }

    public static String getCaptchaUrl() {
        return host + "/captcha.php";
    }

    public static String getLoginUrl() {
        return host + "/login.php";
    }

    public static String getLoginParams(String name, String password, String verificationCode) {
        return "action_login=Log+In&captcha_input=" + verificationCode + "&fingerprint=&fingerprint2=&password=" + password + "&username=" + name + "&x=49&y=9";
    }
}