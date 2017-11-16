package com.wollon.tourgass.operator;

import android.content.Context;

import com.wollon.tourgass.dao.User;
import com.wollon.tourgass.dto.UserLogin;

/**
 * Created by JimMa on 2017/11/15.
 */

public interface IIndenity {
    /**
     * 用户登陆业务
     * @param userLogin
     * @return
     */
    public User login(UserLogin userLogin);

    /**
     * 用户注册业务
     * @param user
     * @return
     */
    public boolean register(User user);

    /**
     * 记住用户
     * 将用户相关信息保存到本地文件中
     * @param user
     * @param context
     */
    public void rememberUser(User user, Context context);

    /**
     * 验证用户
     * 验证本地文件中的用户是否符合正确
     * @param context
     * @return
     */
    public User verificationUser(Context context);
}