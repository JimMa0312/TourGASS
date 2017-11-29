package com.wollon.tourgass.activity.plan;

import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.wollon.tourgass.R;
import com.wollon.tourgass.activity.base.SingleFragmentActivity;

/**
 * Created by 漫聆默 on 2017/11/23 0023.
 */

public class PlanListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new PlanListFragment();
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_toolbar,menu);

        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /**
         * 在fragment中使用ToolBar，在此处必须返回false,
         *否则Fragment中的onOptionsItemSelected会被Activity截断
         */
        return false;
    }
}
