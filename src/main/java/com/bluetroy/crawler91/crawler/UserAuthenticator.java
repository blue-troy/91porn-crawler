package com.bluetroy.crawler91.crawler;

import com.bluetroy.crawler91.crawler.tools.HttpTool;
import com.bluetroy.crawler91.crawler.tools.ScannerTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-07-14
 * Time: 下午8:12
 */
@Component
public class UserAuthenticator {
    @Autowired
    ScannerTool scannerTool;

    public boolean login(String name, String password, String verificationCode) {
        String loginResult = null;
        try {
            //todo host统一问题
            loginResult = HttpTool.post("http://92.91p08.space/login.php", getLoginParams(name, password, verificationCode));
            //todo 这个一个测试 提取测试文件
            try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("loginResult.txt"), "utf-8")) {
                writer.write(loginResult);
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verifyLogin(loginResult);
    }

    private boolean verifyLogin(String loginResult) {
        return !scannerTool.scanLoginState(loginResult).contains("我的状态");
    }

    private String getLoginParams(String name, String password, String verificationCode) {
        return "user=" + name + "&password=" + password + "&captcha_input=" + verificationCode;
    }

}
