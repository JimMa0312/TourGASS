package com.wollon.tourgass.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.wollon.tourgass.R;

/**
 * Created by 漫聆默 on 2017/11/23 0023.
 */

public abstract class SingleFragmentActivity extends BaseActivity {
    protected abstract Fragment createFragment();

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_fragment);

        //设置管理fragment的对象
        FragmentManager fragmentManager=getSupportFragmentManager();
        //将获取CrimeFragment,如果存在的话（在activity_crime.xml中），通过R.id.fragment_container获取与其绑定的fragment，如果有的话
        Fragment fragment=fragmentManager.findFragmentById(R.id.fragment_container);

        if(fragment ==null){
            fragment=createFragment();

            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container ,fragment)
                    .commit();
        }
    }
}
