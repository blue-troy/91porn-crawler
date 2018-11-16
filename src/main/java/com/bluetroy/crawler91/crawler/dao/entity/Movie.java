package com.bluetroy.crawler91.crawler.dao.entity;

import com.bluetroy.crawler91.crawler.utils.TimeUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

/**
 * @author heyixin
 */
@Data
@NoArgsConstructor
public class Movie implements Serializable, Comparable<Movie> {
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
    private String fileName;
    private String key;

    public Movie(String title, String length, String addTime, String author, Integer view, Integer collect, Integer messageNumber, Integer integration, String detailURL) {
        this.title = title;
        this.length = length;
        this.addTime = addTime;
        this.author = author;
        this.view = view;
        this.collect = collect;
        this.messageNumber = messageNumber;
        this.integration = integration;
        this.detailURL = detailURL;
        this.key = detailURL.substring(detailURL.indexOf("=") + 1, detailURL.indexOf("&"));
        this.fileName = title + ".mp4";
    }

    public void update(Movie movie) {
        this.view = movie.getView();
        this.collect = movie.collect;
        this.messageNumber = movie.getMessageNumber();
    }

    @Override
    public int compareTo(Movie o) {
        if (o.view.equals(this.view) && o.collect.equals(this.collect) && o.messageNumber.equals(this.messageNumber)) {
            return 0;
        }
        return 1;
    }
}
