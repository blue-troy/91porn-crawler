package com.bluetroy.crawler91.command;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-07-09
 * Time: 下午9:29
 */
public class PreStartCommandTest {

    @Test
    public void run() {
        PreStartCommand preStartCommand = new PreStartCommand();
        try {
            preStartCommand.run("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}