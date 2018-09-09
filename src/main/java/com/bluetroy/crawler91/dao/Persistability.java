package com.bluetroy.crawler91.dao;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-08-23
 * Time: 下午7:23
 */
public interface Persistability {
    void init(Repository repository);

    void save(Serializable serializable);
}

