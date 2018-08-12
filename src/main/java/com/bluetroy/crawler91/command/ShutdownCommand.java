package com.bluetroy.crawler91.command;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-08-10
 * Time: 下午8:40
 */
public class ShutdownCommand {
    public static void process() {
        new Thread(() -> {
            System.exit(0);
        }).start();
    }
}
