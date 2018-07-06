package com.bluetroy.crawler91.repository;

import com.bluetroy.crawler91.utils.TimeUtils;

import java.util.LinkedList;
import java.util.List;

public class DownloadErrorInfo {
    private String key;
    private Integer errorTime;
    private List<String> errorTimes = new LinkedList<>();

    public DownloadErrorInfo(String key) {
        this.key = key;
    }

    public void update() {
        errorTime++;
        errorTimes.add(TimeUtils.getDate());
    }
}
