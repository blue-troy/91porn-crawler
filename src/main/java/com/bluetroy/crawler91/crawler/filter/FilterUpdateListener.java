package com.bluetroy.crawler91.crawler.filter;

import com.bluetroy.crawler91.crawler.dao.BaseDao;
import com.bluetroy.crawler91.vo.FilterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-11-07
 * Time: 11:05 AM
 */
@Component
public class FilterUpdateListener {
    @Autowired
    private BaseDao dao;

    public void update(FilterVO filterVO) {
        dao.resetFilteredStatus();
    }
}
