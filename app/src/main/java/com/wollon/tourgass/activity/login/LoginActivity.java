package com.wollon.tourgass.activity.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wollon.tourgass.R;
import com.wollon.tourgass.activity.BaseActivity;
import com.wollon.tourgass.dto.UserLogin;
import com.wollon.tourgass.operator.IIndenity;
import com.wollon.tourgass.operator.impl.LoginImpl;

/**
 * Created by 漫聆默 on 2017/11/16 0016.
 */

public class LoginActivity extends BaseActivity {
    //用户 自动登录 状态 0为同意，1为拒绝
    private static String AutoLoginState=null;
    //界面组件
    private LinearLayout llAccount;
    private LinearLayout llPassword;
    private EditText etAccount;
    private EditText etPassword;
    private Button btnLogin;
    private CheckBox cbAutoLogin;
    //暂不使用消息队列
    //private Handler postHandler;
    private String account;
    private String password;
    private IIndenity iIndenity;

    @Override
    protected void init(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);

        llAccount=(LinearLayout) findViewById(R.id.account_ll);
        llPassword=(LinearLayout) findViewById(R.id.password_ll);
        etAccount=(EditText) findViewById(R.id.account_et);
        etPassword=(EditText) findViewById(R.id.password_et);
        btnLogin=(Button) findViewById(R.id.login_btn);
        cbAutoLogin=(CheckBox) findViewById(R.id.autoLogin_cb);

        iIndenity=new LoginImpl();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                account=etAccount.getText().toString();
                password=etPassword.getText().toString();
                UserLogin userLogin=new UserLogin(account,password);
                user=iIndenity.login(userLogin);
                if(user !=null){
                    if(cbAutoLogin.isChecked()){
                        iIndenity.rememberUser(user,context);
                    }
                    LoginActivity.this.finish();
                    isLogin=true;
                    RemoveLogionItem();
                    Log.d("LoginActicvity","认证成功！");
                }else{
                    Toast.makeText(context,R.string.message_login,Toast.LENGTH_LONG).show();
                    Log.w("LoginActicvity","认证失败！");
                }
            }
        });

        if(user!=null){
            LoginActivity.this.finish();
            Log.w("LoginActicvity","成功！");
        }else{
            Log.w("LoginActicvity","失败！");
        }
    }
}
