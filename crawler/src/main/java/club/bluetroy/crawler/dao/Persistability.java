package club.bluetroy.crawler.dao;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-08-23
 * Time: 下午7:23
 */
public interface Persistability extends Serializable {
    /**
     * 初始化对象
     *
     * @param persistability 具有可持久化性质的对象
     */
    void initialize(Persistability persistability);

    /**
     * 对对象进行持久化
     *
     * @param persistability 具有可持久化性质的对象
     */
    void persistence(Persistability persistability);
}

