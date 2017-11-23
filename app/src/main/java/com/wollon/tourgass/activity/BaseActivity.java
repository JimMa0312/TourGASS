package com.wollon.tourgass.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.wollon.tourgass.R;
import com.wollon.tourgass.dao.User;
import com.wollon.tourgass.operator.IIndenity;
import com.wollon.tourgass.operator.impl.LoginImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 漫聆默 on 2017/11/16 0016.
 */

public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions={
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.CHANGE_WIFI_STATE
    };

    private static final int PERMISSON_REQUESTCODE=0;

    /**
     * 判断是否需要检测，防止不停的弹框
     */
    private static boolean isNeedCheck=true;

    private String TAG = getClass().getSimpleName();
    protected Context context;
    public static User user;//存储用户信息
    public static boolean isLogin=false;//确认是否已经登录,默认未登陆

    protected Toolbar toolbar;//工具栏
    protected DrawerLayout mDrawerLayout;//滑动菜单
    protected NavigationView navView;//拓展菜单

    private void printLog() {
        Log.d(TAG, getClass().getName() + "----->" + Thread.currentThread().getStackTrace()[3].getMethodName());
    }
    protected abstract void init(@Nullable Bundle savedInstanceState);

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        printLog();
        super.onCreate(savedInstanceState);
        context=this;
        init(savedInstanceState);
    }

    @Override
    protected void onStart() {
        printLog();
        super.onStart();
    }

    @Override
    protected void onRestart() {
        printLog();
        super.onRestart();
    }

    @Override
    protected void onResume() {
        printLog();
        super.onResume();
        if (isNeedCheck){
            checkPermissions(needPermissions);
        }
    }

    @Override
    protected void onPause() {
        printLog();
        super.onPause();
    }

    @Override
    protected void onStop() {
        printLog();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        printLog();
        super.onDestroy();
    }

    /**
     * 初始化时，实现自动登录功能
     * @return
     */
    protected boolean AutoLogin(){
        if(!isLogin) {
            IIndenity iIndenity = new LoginImpl();
            user = iIndenity.verificationUser(this);
            if (user != null) {
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                isLogin = true;
                return isLogin;
            } else {
                Toast.makeText(this, "登录失败！", Toast.LENGTH_SHORT).show();
                isLogin = false;
                return isLogin;
            }
        }
        return false;
    }

    protected void setmDrawerLayout(){
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBar actionBar=getSupportActionBar();

        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }

    //TODO
    private void testAddData(){
        User user=new User("admin","admin","admin");
        LoginImpl login=new LoginImpl();
        if(login.register(user)){
            Toast.makeText(context,"注册成功！",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"注册失败！",Toast.LENGTH_SHORT).show();
        }
    }

    private void jumpLoginActivity(){
        Intent intent=new Intent();
        intent.setClass(context,LoginActivity.class);
        startActivity(intent);
    }

    protected void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.tool_bas);
        setSupportActionBar(toolbar);
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
                testAddData();
                break;
            case R.id.login:
                jumpLoginActivity();
                break;
            case R.id.delete:
                Toast.makeText(this,"You clicked Delete",Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this,"You clicked Setings",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

    //-----------权限问题

    /**
     * 检查权限
     * @param needPermissions
     */
    private void checkPermissions(String... needPermissions){
        List<String> needRequestPermissionList=findDeniedPermissions(needPermissions);
        if(null!=needRequestPermissionList && needRequestPermissionList.size()>0){
            //list.toarray将ihe传化为数组
            ActivityCompat.requestPermissions(this,needRequestPermissionList.toArray(new String[needRequestPermissionList.size()]),PERMISSON_REQUESTCODE);
        }
    }


    /**
     * 获取权限集中需要的申请权限的列表
     * @param permission
     * @return
     */
    private List<String> findDeniedPermissions(String[] permission){
        List<String> needRequestPermissionList=new ArrayList<>();
        for(String perm:permission){
            if(ContextCompat.checkSelfPermission(this,perm)!= PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(this,perm)){
                needRequestPermissionList.add(perm);
            }
        }
        return needRequestPermissionList;
    }
}
