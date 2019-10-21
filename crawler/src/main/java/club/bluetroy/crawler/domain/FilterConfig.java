package club.bluetroy.crawler.domain;

import club.bluetroy.crawler.util.TimeUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-08-12
 * Time: 下午4:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FilterConfig extends Movie {
    private String addTimeBefore;
    private String addTimeAfter;
    private Long addTimeDistance;
    private FilterConfig orFilterConfig;

    public Long getAddTimeDistance() {
        return addTimeDistance;
    }

    public void setAddTimeDistance(String addTimeDistance) {
        if (null != addTimeDistance) {
            this.addTimeDistance = TimeUtils.timeToMinute(addTimeDistance);
        }
    }
}
