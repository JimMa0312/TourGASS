package com.wollon.tourgass.util;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.wollon.tourgass.R;

/**
 * Created by JimMa on 2017/11/24.
 */

public class ToolBarUtil {
    public static void CreateToolBarMenu(AppCompatActivity app, Menu menu, int menuItems){
        menu.clear();
        app.getMenuInflater().inflate(menuItems,menu);
    }

}
