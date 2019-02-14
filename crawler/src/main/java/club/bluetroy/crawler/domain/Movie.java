package club.bluetroy.crawler.domain;

import club.bluetroy.crawler.util.TimeUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author heyixin
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie implements Serializable, Comparable<Movie> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long docid;
    private String title;
    private String length;
    private String addTime;
    private String author;
    private Integer view;
    private Integer collect;
    private Integer messageNumber;
    private Integer integration;
    private String detailUrl;
    private String downloadUrl;
    private String fileName;
    private String key;
    private LocalDateTime addDateTime;
    @Enumerated(EnumType.STRING)
    private MovieStatus status = MovieStatus.SCANNED;

    public Movie(String title, String length, String addTime, String author, Integer view, Integer collect, Integer messageNumber, Integer integration, String detailUrl) {
        this.title = title;
        this.length = length;
        setAddTime(addTime);
        this.author = author;
        this.view = view;
        this.collect = collect;
        this.messageNumber = messageNumber;
        this.integration = integration;
        this.detailUrl = detailUrl;
        this.key = detailUrl.substring(detailUrl.indexOf("=") + 1, detailUrl.indexOf("&"));
        this.fileName = title + ".mp4";
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

    public void update(Movie newMovie) {
        this.view = newMovie.getView();
        this.collect = newMovie.collect;
        this.messageNumber = newMovie.getMessageNumber();
    }

    @Override
    public int compareTo(Movie o) {
        if (o.view.equals(this.view) && o.collect.equals(this.collect) && o.messageNumber.equals(this.messageNumber)) {
            return 0;
        }
        return 1;
    }
}
