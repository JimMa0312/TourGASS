package com.wollon.tourgass.dto;

import com.wollon.tourgass.dao.User;

/**
 * Created by JimMa on 2017/11/15.
 */

public class UserLogin {
    private String userName;
    private String password;

    public UserLogin(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
