package club.bluetroy.crawler.dao.entity;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-11-30
 * Time: 18:16
 */
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
}