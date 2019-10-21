package club.bluetroy.crawler.config;

import club.bluetroy.crawler.domain.FilterConfig;
import org.junit.jupiter.api.Test;


/**
 * author heyixin 2019-10-15 5:50 下午.
 */
class InnerFilterConfigTest {

    @Test
    void testGetDefaultFilterConfig() {
        FilterConfig defaultFilterConfig = InnerFilterConfig.getDefaultFilterConfig();
    }
}