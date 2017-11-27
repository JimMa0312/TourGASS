package com.wollon.tourgass.activity.plan;

import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_toolbar,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                Toast.makeText(context,"delete",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        return true;
    }

}
