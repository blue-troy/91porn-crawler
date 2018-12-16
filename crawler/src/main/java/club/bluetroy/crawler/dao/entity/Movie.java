package club.bluetroy.crawler.dao.entity;

import club.bluetroy.crawler.utils.TimeUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author heyixin
 */
@Data
@NoArgsConstructor
public class Movie implements Serializable, Comparable<Movie> {
    private String title;
    private String length;
    private String addTime;
    private String author;
    private Integer view;
    private Integer collect;
    private Integer messageNumber;
    private Integer integration;
    private String detailURL;
    private String downloadURL;
    private String fileName;
    private String key;
    private LocalDateTime addDateTime;

    public Movie(String title, String length, String addTime, String author, Integer view, Integer collect, Integer messageNumber, Integer integration, String detailURL) {
        this.title = title;
        this.length = length;
        setAddTime(addTime);
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

    public void setAddTime(String addTime) {
        try {
            this.addDateTime = TimeUtils.getLocalDateTime(addTime);
        } catch (Exception e) {
            this.addDateTime = TimeUtils.now();
            e.printStackTrace();
        }
        this.addTime = TimeUtils.format(this.addDateTime);
    }
}
