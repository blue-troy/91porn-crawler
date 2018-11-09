package com.bluetroy.crawler91.crawler.impl.filter;

import com.bluetroy.crawler91.crawler.impl.dao.Repository;
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
    private Repository repository;

    public void update(FilterVO filterVO) {
        repository.getScannedMovies().entrySet().forEach(entry -> entry.setValue(false));
    }
}
