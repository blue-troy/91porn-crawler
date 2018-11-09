package com.bluetroy.crawler91.crawler.dao;

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
    void init(Persistability persistability);

    void save(Persistability persistability);
}

