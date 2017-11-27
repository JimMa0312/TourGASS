package com.wollon.tourgass.operator.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.wollon.tourgass.dao.User;
import com.wollon.tourgass.dao.UserDao;
import com.wollon.tourgass.dto.UserLogin;
import com.wollon.tourgass.operator.IIndenity;
import com.wollon.tourgass.util.MD5Utils;

import java.io.File;
import java.util.List;

/**
 * Created by JimMa on 2017/11/15.
 */

public class LoginImpl implements IIndenity {

    UserDao userDao=new UserDao();

    /**
     * 登录验证
     * 1. 获取DM5编码后的密码
     * 2.将密码和用户名相匹配
     * 3.返回User对象集
     * 4.如果List为空或者数量为0，代表登陆失败，否则代表登陆成功
     * @param userLogin
     * @return
     */
    @Override
    public User login(UserLogin userLogin)
    {
        String pwd= MD5Utils.getEnMD5(userLogin.getPassword(),MD5Utils.PASSWOELD);
        userLogin.setPassword(pwd);
        List<User> users=userDao.sleectUser(userLogin);
        if(users!=null && users.size()>0){
            return users.get(0);
        }
        return null;
    }

    @Override
    public boolean register(User user) {
        String pwd= MD5Utils.getEnMD5(user.getPassword(),MD5Utils.PASSWOELD);
        user.setPassword(pwd);
        return userDao.createUser(user);
    }

    /**
     * 记住当前用户到本地
     * @param user
     * @param context
     */
    @Override
    public void rememberUser(User user, Context context) {
        SharedPreferences.Editor editor=context.getSharedPreferences("Identity",Context.MODE_PRIVATE).edit();
        editor.putInt("id",user.getId());
        editor.putString("account",user.getAccount());
        editor.putString("password",user.getPassword());
        editor.putString("nickname",user.getNickname());
        String verStr=this.getEnVerAccount(user.getId(),user.getAccount());
        editor.putString("verstr",verStr);

        editor.apply();
    }

    /**
     * 从本地读取用户信息，并认证合法性
     * @return
     */
    @Override
    public User verificationUser(Context context) {
        SharedPreferences pref=context.getSharedPreferences("Identity",Context.MODE_PRIVATE);
        String enString=this.getEnVerAccount(pref.getInt("id",0),pref.getString("account",""));
        if (enString.equals(pref.getString("verstr","Noo!!!!"))){
            return Login(pref.getInt("id",0),pref.getString("account",""),pref.getString("password",""));
        }
        return null;
    }

    private String getEnVerAccount(int id,String accountnme){
        return MD5Utils.getEnMD5(accountnme+id,MD5Utils.USEREN);
    }

    private User Login(int id, String account, String pwd) {
        List<User> users=userDao.sleectUser(id,account,pwd);
        if(users!=null && users.size()>0){
            return users.get(0);
        }
        return null;
    }

    @Override
    public boolean loginOut(Context context) {
        File file=new File(".data/data/"+context.getPackageName().toString()+"/shared_prefs","account/xml");
        if(file.exists()){
            file.delete();
            Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}