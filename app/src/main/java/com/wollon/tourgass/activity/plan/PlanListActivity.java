package com.wollon.tourgass.activity.plan;

import android.support.v4.app.Fragment;

import com.wollon.tourgass.activity.SingleFragmentActivity;

/**
 * Created by 漫聆默 on 2017/11/23 0023.
 */

public class PlanListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new PlanListFragment();
    }
}
