package club.bluetroy.crawler;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-11-09
 * Time: 3:15 PM
 */
public interface UserAuthenticator {
    /**
     * 输入账户信息登陆
     *
     * @param name             账户名
     * @param password         密码
     * @param verificationCode 验证码
     * @throws Exception 登陆失败则抛出异常
     */
    void login(String name, String password, String verificationCode) throws Exception;
}
