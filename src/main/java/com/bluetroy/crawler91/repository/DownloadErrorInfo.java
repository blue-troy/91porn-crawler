package com.bluetroy.crawler91.repository;

import com.bluetroy.crawler91.utils.TimeUtils;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

/**
 * @author heyixin
 */
@ToString
public class DownloadErrorInfo {
    private final String key;
    private Integer errorTime;

    private final List<String> errorTimes = new LinkedList<>();

    public DownloadErrorInfo(String key) {
        this.key = key;
    }

    public void update() {
        errorTime++;
        errorTimes.add(TimeUtils.getDate());
    }
}
