package club.bluetroy.crawler.config;

import club.bluetroy.crawler.Scanner;
import club.bluetroy.crawler.dao.entity.Category;
import club.bluetroy.crawler.tool.PornUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2019-01-27
 * Time: 16:46
 */
@Component
public class ScanConfig implements CommandLineRunner {
    @Autowired
    private Scanner scanner;

    @Override
    public void run(String... args) throws Exception {
        scanner.addUrl(PornUrl.getUrl(Category.HOT));
        scanner.addUrl(PornUrl.getUrl(Category.MONTH_DISCUSS));
        scanner.addUrl(PornUrl.getUrl(Category.MONTH_TOP_COLLECT));
        scanner.addUrl(PornUrl.getUrl(Category.RECENT_BOUTIQUE));
        scanner.addUrl(PornUrl.getUrl(Category.RECENT_POINT));
    }
}
