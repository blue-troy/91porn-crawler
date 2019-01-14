package club.bluetroy.crawler.dao.entity;

import club.bluetroy.crawler.util.TimeUtils;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

/**
 * @author heyixin
 */
@ToString
public class DownloadErrorInfo {
    private final String key;
    private final List<String> errorTimes = new LinkedList<>();
    private int errorTime;

    public DownloadErrorInfo(String key) {
        this.key = key;
    }

    public void update() {
        errorTime++;
        errorTimes.add(TimeUtils.getDate());
    }
}
