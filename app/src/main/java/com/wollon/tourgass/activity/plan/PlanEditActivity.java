package com.wollon.tourgass.activity.plan;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.wollon.tourgass.activity.base.SingleFragmentActivity;

import java.util.UUID;

/**
 * Created by 漫聆默 on 2017/11/23 0023.
 */

public class PlanEditActivity extends SingleFragmentActivity {
    private static final String EXTRA_PLAN_ID="com.wollon.tourgass.plan_id";

    public static Intent newIntent(Context packageContext, UUID planId){
        Intent intent=new Intent(packageContext,PlanEditActivity.class);
        intent.putExtra(EXTRA_PLAN_ID,planId);

        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID planId= (UUID) getIntent().getSerializableExtra(EXTRA_PLAN_ID);

        return PlanEditFragment.newInstance(planId);
    }

}
