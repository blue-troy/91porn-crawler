package com.bluetroy.crawler91.crawler;

import com.bluetroy.crawler91.crawler.impl.tools.HttpClient;
import com.bluetroy.crawler91.crawler.impl.tools.ScannerTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-07-14
 * Time: 下午8:12
 */
@Service("userAuthenticator")
class UserAuthenticatorImpl implements UserAuthenticator {
    @Autowired
    ScannerTool scannerTool;

    @Override
    public boolean login(String name, String password, String verificationCode) {
        String loginResult = "";
        try {
            //todo host统一问题
            loginResult = HttpClient.post("http://91porn.com/login.php", getLoginParams(name, password, verificationCode));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verifyLogin(loginResult, name);
    }

    private boolean verifyLogin(String loginResult, String name) {
        return loginResult.contains(name);
    }

    private String getLoginParams(String name, String password, String verificationCode) {
        return "action_login=Log+In&captcha_input=" + verificationCode + "&fingerprint=&fingerprint2=&password=" + password + "&username=" + name + "&x=40&y=6";
    }

}
