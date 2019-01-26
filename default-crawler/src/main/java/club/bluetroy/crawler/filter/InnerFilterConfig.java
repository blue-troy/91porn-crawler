package club.bluetroy.crawler.filter;

import club.bluetroy.crawler.domain.FilterConfig;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2019-01-26
 * Time: 23:55
 */
public class InnerFilterConfig {
    public static FilterConfig getDefaultFilterConfig() {
        FilterConfig currentShowFaceConfig = new FilterConfig();
        currentShowFaceConfig.setAddTimeDistance("1天");
        currentShowFaceConfig.setCollect(200);
        currentShowFaceConfig.setTitle("露脸");

        FilterConfig currentHotConfig = new FilterConfig();
        currentHotConfig.setAddTimeDistance("1天");
        currentHotConfig.setCollect(500);

        FilterConfig monthShowFace = new FilterConfig();
        monthShowFace.setAddTimeDistance("1月");
        monthShowFace.setTitle("露脸");
        monthShowFace.setCollect(1500);

        FilterConfig monthHot = new FilterConfig();
        monthHot.setAddTimeDistance("1月");
        monthHot.setCollect(2000);

        FilterConfig collectOverFourThousand = new FilterConfig();
        collectOverFourThousand.setCollect(4000);

        currentShowFaceConfig.setOrFilterConfig(currentHotConfig);
        currentHotConfig.setOrFilterConfig(monthShowFace);
        monthShowFace.setOrFilterConfig(monthHot);
        monthHot.setOrFilterConfig(collectOverFourThousand);

        return currentShowFaceConfig;
    }
}
