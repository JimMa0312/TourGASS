package com.wollon.tourgass.dao;

import com.wollon.tourgass.dto.UserLogin;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by JimMa on 2017/11/15.
 */

public class UserDao {
    /**
     * 创建用户数据
     * @param user
     * @return
     */
    public boolean createUser(User user){
        return user.save();
    }

    /**
     * 查询用户数据
     * @param userLogin
     * @return
     */
    public List<User> sleectUser(UserLogin userLogin){
        List<User> users= DataSupport.where("account = ? and password= ?",userLogin.getUserName(),userLogin.getPassword()).find(User.class);
        return users;
    }
}
