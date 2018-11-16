package com.bluetroy.crawler91.vo;

import com.bluetroy.crawler91.crawler.dao.entity.Movie;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-08-12
 * Time: 下午4:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FilterVO extends Movie {
    private String addTimeBefore;
    private String addTimeAfter;
}
