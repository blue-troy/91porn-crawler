package com.bluetroy.crawler91.crawler.filter.impl.filter;

import com.bluetroy.crawler91.crawler.dao.Repository;
import com.bluetroy.crawler91.crawler.filter.Filter;
import com.bluetroy.crawler91.crawler.untils.ApplicationContextProvider;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author heyixin
 */
@ToString
public class TitleFilter implements Filter {
    @ToString.Exclude
    private Repository repository;
    private String keyword;

    public TitleFilter(String keyword) {
        this.keyword = keyword;
        repository = ApplicationContextProvider.getBean("repository", Repository.class);
    }

    private TitleFilter() {
    }

    @Override
    public void doFilter(ConcurrentHashMap<String, Boolean> tobeFilter) {
        tobeFilter.forEach(5, (k, v) -> {
            if (repository.getMovieData().get(k).getTitle().contains(keyword)) {
                return;
            }
            tobeFilter.remove(k);
        });
    }
}
