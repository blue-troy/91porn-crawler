package com.bluetroy.crawler91.crawler.dao;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-08-23
 * Time: 下午5:40
 */
public enum MovieStatus {
    //Movies的不同状态
    SCANNED_MOVIES, TO_DOWNLOAD_MOVIES, FILTERED_MOVIES, DOWNLOADED_MOVIES, DOWNLOAD_ERROR;
}
