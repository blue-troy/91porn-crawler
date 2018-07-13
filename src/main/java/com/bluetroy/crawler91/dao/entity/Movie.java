package com.bluetroy.crawler91.dao.entity;

import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

/**
 * @author heyixin
 */
@Data
public class Movie implements Serializable {
    String title;
    String length;
    String addTime;
    String author;
    Integer view;
    Integer collect;
    Integer messageNumber;
    Integer integration;
    String detailURL;
    String downloadURL;
    String fileName;

    private static Integer stringToInteger(String s) {
        int index = s.indexOf("&");
        if (index != -1) {
            s = s.substring(0, index);
        }
        s = s.trim();
        return Integer.valueOf(s);
    }

    public String getKey() {
        Integer index = detailURL.indexOf("viewkey=");
        return detailURL.substring(index);
    }

    public String getFileName() {
        if (fileName == null || "".equals(fileName)) {
            fileName = title + ".mp4";
        }
        return fileName;
    }

    public Movie setCollect(@NonNull String collect) {
        this.collect = stringToInteger(collect);
        return this;
    }

    public Movie setMessageNumber(String messageNumber) {
        this.messageNumber = stringToInteger(messageNumber);
        return this;
    }

    public Movie setIntegration(String integration) {
        this.integration = stringToInteger(integration);
        return this;
    }

    public Movie setView(String view) {
        this.view = stringToInteger(view);
        return this;
    }

    public Movie setTitle(String title) {
        this.title = title;
        return this;
    }

    public Movie setLength(String length) {
        this.length = length;
        return this;
    }

    public Movie setAddTime(String addTime) {
        this.addTime = addTime;
        return this;
    }

    public Movie setAuthor(String author) {
        this.author = author;
        return this;
    }

    public Movie setView(Integer view) {
        this.view = view;
        return this;
    }

    public Movie setCollect(Integer collect) {
        this.collect = collect;
        return this;
    }

    public Movie setMessageNumber(Integer messageNumber) {
        this.messageNumber = messageNumber;
        return this;
    }

    public Movie setIntegration(Integer integration) {
        this.integration = integration;
        return this;
    }

    public Movie setDetailURL(String detailURL) {
        this.detailURL = detailURL;
        return this;
    }
}
