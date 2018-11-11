package com.bluetroy.crawler91.crawler;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-11-11
 * Time: 4:31 PM
 */
public interface Adviser {
    /**
     * 发送消息通知
     *
     * @param message 消息
     * @throws Exception 消息发送失败
     */
    void message(String message) throws Exception;

    /**
     * 给对应uri地址发送消息
     *
     * @param message 消息
     * @param t       消息附带内容
     * @param <T>     消息附带内容
     * @throws Exception 消息发送失败
     */
    <T> void message(String message, T t) throws Exception;

}
