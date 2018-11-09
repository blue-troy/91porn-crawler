package com.bluetroy.crawler91.crawler;

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
     * @return 登陆是否成功
     */
    boolean login(String name, String password, String verificationCode);
}
