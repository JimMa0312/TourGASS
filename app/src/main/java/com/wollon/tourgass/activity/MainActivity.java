package com.wollon.tourgass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.wollon.tourgass.R;

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

    @Override
    protected void init() {
        setContentView(R.layout.activity_main);

        btAddData=(Button) findViewById(R.id.test_Add_bt);
        btLogin=(Button) findViewById(R.id.test_login);
        btAddData.setOnClickListener(clickListener);
        btLogin.setOnClickListener(clickListener);

       /* Intent intent=new Intent();
        intent.setClass(context,LoginActivity.class);*/
    }
}




