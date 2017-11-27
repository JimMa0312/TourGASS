package com.wollon.tourgass.dao;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JimMa on 2017/11/15.
 */

public class User extends DataSupport{
    private int id;//用户ID
    private String account;//用户账号
    private String password;//用户密码
    private String nickname;//用户昵称

    private List<Plan> plans=new ArrayList<>();

    public User() {

    }

    public User(int id, String account, String password, String nickname) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.nickname = nickname;
    }

    public User(String account, String password, String nickname) {
        this.account = account;
        this.password = password;
        this.nickname = nickname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<Plan> getPlans() {
        return plans;
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }
}
