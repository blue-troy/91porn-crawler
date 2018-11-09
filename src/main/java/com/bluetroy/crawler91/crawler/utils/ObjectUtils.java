package com.bluetroy.crawler91.crawler.utils;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-08-14
 * Time: 下午3:14
 */
public class ObjectUtils {
    public static boolean isAllPropertyNull(Object o) {
        for (Field declaredField : o.getClass().getDeclaredFields()) {
            if (declaredField != null) {
                return false;
            }
        }
        return true;
    }
}
