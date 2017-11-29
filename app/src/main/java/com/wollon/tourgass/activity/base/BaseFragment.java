package com.wollon.tourgass.activity.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by 漫聆默 on 2017/11/29 0029.
 */

public class BaseFragment extends Fragment {
    public Activity mActivity;
    public AppCompatActivity mAppCompatActivity;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity=getActivity();
    }


}
