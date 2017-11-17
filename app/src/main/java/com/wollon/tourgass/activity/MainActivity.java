package com.wollon.tourgass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.wollon.tourgass.R;
import com.wollon.tourgass.dao.User;
import com.wollon.tourgass.operator.impl.LoginImpl;

public class MainActivity extends BaseActivity {
    private Button btAddData;
    private Button btLogin;

    private OnClickListener clickListener =new OnClickListener(){
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.test_Add_bt:

                    break;
                case R.id.test_login:
                    Intent intent=new Intent();
                    intent.setClass(context,LoginActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    private OnClickListener addDataListener=new OnClickListener() {
        @Override
        public void onClick(View view) {
            User user=new User("admin","adnin","admin");
            LoginImpl login=new LoginImpl();
            if(login.register(user)){
                Toast.makeText(context,"注册成功！",Toast.LENGTH_SHORT);
            }else{
                Toast.makeText(context,"注册失败！",Toast.LENGTH_SHORT);
            }
        }
    };

    @Override
    protected void init() {
        setContentView(R.layout.activity_main);

        btAddData=(Button) findViewById(R.id.test_Add_bt);
        btLogin=(Button) findViewById(R.id.test_login);
        btAddData.setOnClickListener(addDataListener);
        btLogin.setOnClickListener(clickListener);

       /* Intent intent=new Intent();
        intent.setClass(context,LoginActivity.class);*/
    }
}




