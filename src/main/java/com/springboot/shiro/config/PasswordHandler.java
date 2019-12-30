package com.springboot.shiro.config;

import com.springboot.shiro.bean.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 加密处理
 */
public class PasswordHandler {

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    //基础散列算法
    public static final String MD5 = "md5";
    //加密次数
    public static final int HASH = 1024;

    /**
     * 干扰因子
     * @param user
     */
    public void encryptPassword(User user){
        // 随机字符串作为salt因子，实际参与运算的salt我们还引入其它干扰因子
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        //对密码进行加密
        String newPassword = new SimpleHash(MD5, user.getPassword(),ByteSource.Util.bytes(user.getSalt()), HASH).toHex();
        //保存密码到对应的数据库中
        user.setPassword(newPassword);
    }



}
