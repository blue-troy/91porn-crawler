package com.bluetroy.crawler91.crawler;

import com.bluetroy.crawler91.crawler.tools.HttpClient;
import com.bluetroy.crawler91.crawler.tools.ScannerTool;
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
    public void login(String name, String password, String verificationCode) throws Exception {
        String loginResult = "";
        //todo host统一问题
        loginResult = HttpClient.post("http://91porn.com/login.php", getLoginParams(name, password, verificationCode));
        if (!verifyLogin(loginResult, name)) {
            throw new Exception("登陆失败");
        }
    }

    private boolean verifyLogin(String loginResult, String name) {
        return loginResult.contains(name);
    }

    private String getLoginParams(String name, String password, String verificationCode) {
        return "action_login=Log+In&captcha_input=" + verificationCode + "&fingerprint=&fingerprint2=&password=" + password + "&username=" + name + "&x=40&y=6";
    }

}
