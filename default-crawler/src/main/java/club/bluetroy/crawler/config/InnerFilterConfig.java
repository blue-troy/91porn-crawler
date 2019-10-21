package club.bluetroy.crawler.config;

import club.bluetroy.crawler.domain.FilterConfig;

/**
 * @author heyixin
 * Date: 2019-01-26
 * Time: 23:55
 */
public class InnerFilterConfig {
    public static FilterConfig getDefaultFilterConfig() {
        FilterConfig currentShowFaceConfig = getcurrentShowFaceConfig(200);
        FilterConfig currentHotConfig = getCurrentHotConfig(500);
        FilterConfig monthShowFace = getMonthShowFaceConfig(1500);
        FilterConfig monthHot = getMonthHotConfig(2000);
        FilterConfig collectOverFourThousand = getCollectedConfig(5000);
        return or(currentShowFaceConfig, currentHotConfig, monthShowFace, monthHot, collectOverFourThousand);
    }

    public static FilterConfig getcurrentShowFaceConfig(int collect) {
        return getFilterConfig("露脸", "1天", collect);
    }

    public static FilterConfig getCurrentHotConfig(int collect) {
        return getFilterConfig(null, "1天", collect);
    }

    public static FilterConfig getMonthShowFaceConfig(int collect) {
        return getFilterConfig("露脸", "1月", collect);
    }

    public static FilterConfig getMonthHotConfig(int collect) {
        return getFilterConfig(null, "1月", collect);
    }

    public static FilterConfig getCollectedConfig(int collect) {
        return getFilterConfig(null, null, collect);
    }

    private static FilterConfig or(FilterConfig... filterConfigs) {
        if (filterConfigs.length == 1) {
            return filterConfigs[0];
        }
        for (int i = 0; i < filterConfigs.length - 1; i++) {
            filterConfigs[i].setOrFilterConfig(filterConfigs[i + 1]);
        }
        return filterConfigs[0];
    }

    private static FilterConfig getFilterConfig(String title, String addTimeDistance, int collect) {
        FilterConfig filterConfig = new FilterConfig();
        filterConfig.setTitle(title);
        filterConfig.setAddTimeDistance(addTimeDistance);
        filterConfig.setCollect(collect);
        return filterConfig;
    }

    public static FilterConfig getShowFaceConfigConfig(int collect) {
        return getFilterConfig("露脸", null, collect);
    }
}
