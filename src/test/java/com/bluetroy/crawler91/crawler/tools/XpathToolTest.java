package com.bluetroy.crawler91.crawler.tools;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-11-12
 * Time: 6:22 PM
 */
public class XpathToolTest {
    private static final String loginErrorHtmlFile = "/Users/heyixin/IdeaProjects/crawler91/src/main/resources/test/loginError.html";

    @Test
    public void getLoginError() throws Exception {
        String loginResult = new String(Files.readAllBytes(Paths.get(loginErrorHtmlFile)));
        String loginError = new XpathTool().getLoginErrorMessage(loginResult);
        System.out.println(loginError);
    }
}