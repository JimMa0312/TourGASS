package com.wollon.tourgass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.wollon.tourgass.R;
import com.wollon.tourgass.dao.User;
import com.wollon.tourgass.operator.impl.LoginImpl;

public class MainActivity extends BaseActivity {
    private Button btAddData;
    private Button btLogin;

    private MapView mMapView=null;

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
    protected void init(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        btAddData=(Button) findViewById(R.id.test_Add_bt);
        btLogin=(Button) findViewById(R.id.test_login);

        btAddData.setOnClickListener(addDataListener);
        btLogin.setOnClickListener(clickListener);

       /* Intent intent=new Intent();
        intent.setClass(context,LoginActivity.class);*/
        mMapView=(MapView)findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);


        //初始化地图控制器对象
        AMap aMap = null;
        if(aMap == null){
            aMap=mMapView.getMap();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}




