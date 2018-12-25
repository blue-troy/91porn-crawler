package club.bluetroy.crawler;

import club.bluetroy.crawler.tool.HttpClient;
import club.bluetroy.crawler.tool.PornUrl;
import club.bluetroy.crawler.tool.Selector;
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
    private Selector selector;

    @Override
    public void login(String name, String password, String verificationCode) throws Exception {
        String loginResult = HttpClient.post(PornUrl.getLoginUrl(), PornUrl.getLoginParams(name, password, verificationCode));
        if (!verifyLogin(loginResult, name)) {
            throw new Exception(selector.getLoginErrorMessage(loginResult));
        }
    }

    private boolean verifyLogin(String loginResult, String name) {
        return loginResult.contains(name);
    }

}
