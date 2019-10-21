package club.bluetroy.crawler.dao.entity;

import lombok.Getter;

import java.util.concurrent.Future;

/**
 * @author heyixin
 * Date: 2018-07-10
 * Time: 下午4:15
 */
@Getter
public class KeyContent {
    private final String Key;
    private final Future<String> content;

    public KeyContent(String key, Future<String> content) {
        Key = key;
        this.content = content;
    }
}
