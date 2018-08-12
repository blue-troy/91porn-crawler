package com.bluetroy.crawler91.crawler;

import com.bluetroy.crawler91.utils.HttpRequester;
import com.bluetroy.crawler91.utils.ScannerUtils;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-07-14
 * Time: 下午8:12
 */
public class UserAuthenticator {
    public static boolean login(String name, String password, String verificationCode) {
        String loginResult = null;
        try {
            //todo host统一问题
            loginResult = HttpRequester.post("http://92.91p08.space/login.php", getLoginParams(name, password, verificationCode));
            //todo 这个一个测试 提取测试文件
            try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("loginResult.txt"),"utf-8")){
                writer.write(loginResult);
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verifyLogin(loginResult);
    }

    private static boolean verifyLogin(String loginResult) {
        return !ScannerUtils.scanLoginState(loginResult).contains("我的状态");
    }

    private static String getLoginParams(String name, String password, String verificationCode) {
        return "user=" + name + "&password=" + password + "&captcha_input=" + verificationCode;
    }

}
