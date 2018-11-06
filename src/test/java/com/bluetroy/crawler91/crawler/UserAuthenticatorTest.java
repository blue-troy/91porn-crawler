package com.bluetroy.crawler91.crawler;

import com.bluetroy.crawler91.crawler.tools.HttpClient;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-09-12
 * Time: 下午4:39
 */
public class UserAuthenticatorTest {
    public static void main(String[] args) throws Exception {
        Files.deleteIfExists(Paths.get("captcha.png"));
        //HttpClient.download("http://94.91p30.space/captcha.php", "captcha.png").get();
        System.out.println("下载验证码成功");
        Scanner scanner = new Scanner(System.in);
        String cat = scanner.next();
        System.out.println(HttpClient.post("http://94.91p30.space/login.php", "action_login=Log+In&captcha_input=" + cat + "&fingerprint=&fingerprint2=&password=lx032515.jp&username=oldblueman&x=40&y=6"));

    }
}