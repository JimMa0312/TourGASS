package com.wollon.tourgass.operator.impl;

import com.wollon.tourgass.dao.User;
import com.wollon.tourgass.dto.UserLogin;
import com.wollon.tourgass.operator.IIndenity;

/**
 * Created by JimMa on 2017/11/15.
 */

public class LoginImpl implements IIndenity {
    @Override
    public User login(UserLogin userLogin) {
        return null;
    }

    @Override
    public boolean register(User user) {
        return false;
    }

    @Override
    public void rememberUser(User user) {

    }
}