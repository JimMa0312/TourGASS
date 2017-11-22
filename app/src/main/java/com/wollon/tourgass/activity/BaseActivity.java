package com.wollon.tourgass.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.wollon.tourgass.dao.User;

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
    public static User user;

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
