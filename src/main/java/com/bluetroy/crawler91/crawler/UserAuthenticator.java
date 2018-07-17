package com.bluetroy.crawler91.crawler;

import com.bluetroy.crawler91.utils.HttpRequester;
import com.bluetroy.crawler91.utils.ScannerUtils;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-07-14
 * Time: 下午8:12
 */
public class UserAuthenticator {
    public boolean login(String name, String password, String verificationCode) {
        String loginResult = null;
        try {
            //todo host统一问题
            loginResult = HttpRequester.post("http://91.91p09.space/login.php", getLoginParams(name, password, verificationCode));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verifyLogin(loginResult);
    }

    private boolean verifyLogin(String loginResult) {
        return !ScannerUtils.scanLoginState(loginResult).contains("我的状态");
    }

    private String getLoginParams(String name, String password, String verificationCode) {
        return "user=" + name + "&password=" + password + "&captcha_input=" + verificationCode;
    }

}
