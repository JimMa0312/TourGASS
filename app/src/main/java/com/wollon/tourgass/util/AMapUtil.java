package com.wollon.tourgass.util;

import android.widget.EditText;

/**
 * Created by JimMa on 2017/11/27.
 */

public class AMapUtil {
    public static String checkEditText(EditText editText){
        if(editText!=null && editText.getText()!=null
                && !(editText.getText().toString().trim().equals(""))){
            return editText.getText().toString().trim();
        }else{
            return "";
        }
    }

    public static boolean IsEmptyOrNullString(String s){
        return (s==null||(s.trim().length()==0));
    }
}
