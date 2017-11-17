package com.wollon.tourgass.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.wollon.tourgass.dao.User;

/**
 * Created by 漫聆默 on 2017/11/16 0016.
 */

public abstract class BaseActivity extends AppCompatActivity {
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
}
