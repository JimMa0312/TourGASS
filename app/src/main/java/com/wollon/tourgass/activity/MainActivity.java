package com.wollon.tourgass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;
import com.wollon.tourgass.R;
import com.wollon.tourgass.dao.User;
import com.wollon.tourgass.operator.impl.LoginImpl;
import com.wollon.tourgass.util.MD5Utils;

public class MainActivity extends BaseActivity {
    private Button btAddData;
    private Button btLogin;
    private UiSettings mUiSetting;
    private AMap aMap;

    private Toolbar toolbar;//工具栏
    private DrawerLayout mDrawerLayout;//滑动菜单
    private NavigationView navView;//拓展菜单
    private MyLocationStyle myLocationStyle;

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
            User user=new User("admin","admin","admin");
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

        setToolbar();
        setmDrawerLayout();
        navView=(NavigationView)findViewById(R.id.nav_view);
        NavigationView navView=(NavigationView)findViewById(R.id.nav_view);

        navView.setCheckedItem(R.id.nav_call);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        btAddData=(Button) findViewById(R.id.test_Add_bt);
        btLogin=(Button) findViewById(R.id.test_login);

        btAddData.setOnClickListener(addDataListener);
        btLogin.setOnClickListener(clickListener);

       /* Intent intent=new Intent();
        intent.setClass(context,LoginActivity.class);*/
        mMapView=(MapView)findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);


        //初始化地图控制器对象
        if(aMap == null){
            aMap=mMapView.getMap();
        }



        mUiSetting=aMap.getUiSettings();

        settingnUI();

        Log.d("AmapSHA",MD5Utils.sHA1(context));
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

    private void settingnUI(){
        mUiSetting.setCompassEnabled(true);//打开指南针
        myLocationStyle=new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW);//连续定位，跟随设备;
        myLocationStyle.interval(2000);
        myLocationStyle.showMyLocation(true);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);

        mUiSetting.setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);

        mUiSetting.setScaleControlsEnabled(true);
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.tool_bas);
        setSupportActionBar(toolbar);
    }

    private void setmDrawerLayout(){
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBar actionBar=getSupportActionBar();

        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                Toast.makeText(this,"You clicked Backup", Toast.LENGTH_SHORT).show();;
                break;
            case R.id.delete:
                Toast.makeText(this,"You clicked Delete",Toast.LENGTH_SHORT).show();;
                break;
            case R.id.settings:
                Toast.makeText(this,"You clicked Setings",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }
}




