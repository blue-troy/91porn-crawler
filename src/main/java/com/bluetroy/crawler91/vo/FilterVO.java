package com.bluetroy.crawler91.vo;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-08-12
 * Time: 下午4:11
 */
@Data
public class FilterVO {
    String title;
    String length;
    String addTime;
    String author;
    Integer view;
    Integer collect;
    Integer messageNumber;
    Integer integration;
    String detailURL;
    String downloadURL;
    String fileName;
}
