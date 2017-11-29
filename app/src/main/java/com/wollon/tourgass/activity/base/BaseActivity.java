package com.wollon.tourgass.activity.base;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wollon.tourgass.R;
import com.wollon.tourgass.activity.login.LoginActivity;
import com.wollon.tourgass.activity.note.NoteListActivity;
import com.wollon.tourgass.activity.plan.PlanListActivity;
import com.wollon.tourgass.dao.User;
import com.wollon.tourgass.operator.IIndenity;
import com.wollon.tourgass.operator.impl.LoginImpl;
import com.wollon.tourgass.util.ToolBarUtil;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
    protected static Menu iMenu;//菜单；

    //导航栏用户信息显示窗口
    protected static TextView navUserName;
    protected static Button navLOginButton;
    protected static CircleImageView navImage;

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

                //RemoveLogionItem();
                return isLogin;
            } else {
                Toast.makeText(this, "登录失败！", Toast.LENGTH_SHORT).show();
                isLogin = false;
                return isLogin;
            }
        }
        return false;
    }

    /**
     * 视图优化方法：
     * 删除工具栏中，login功能标签
     */
    protected void RemoveLogionItem(){
        iMenu.removeItem(R.id.login);
        ControlLoginButtn(View.GONE);
    }

    /**
     * 控制按键显示和隐藏
     * @param visibiliity
     */
    @SuppressLint("LongLogTag")
    protected void ControlLoginButtn(int visibiliity){
        if(visibiliity==View.GONE || visibiliity==View.VISIBLE)
            navLOginButton.setVisibility(visibiliity);
        else
            Log.e("ControlLogrinButton --> BaseActicity","Input Error");
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

    protected void jumpLoginActivity(){
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
        ToolBarUtil.CreateToolBarMenu(this,menu,R.menu.item_toolbar);
        iMenu=menu;
        if(isLogin){
            RemoveLogionItem();
        }
        return true;
    }


    /**
     * 侧边工具栏 功能激活
     * @param item
     * @return
     */
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

    /**
     * 侧边工具栏的 功能实现调用方法
     */
    NavigationView.OnNavigationItemSelectedListener navigationListener=new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent=null;
            switch (item.getItemId()){
                case R.id.nav_note:
                    intent=new Intent(context, NoteListActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_plan:
                    intent=new Intent(context,PlanListActivity.class);
                    startActivity(intent);
                    break;
            }
            mDrawerLayout.closeDrawers();
            return true;
        }
    };

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
