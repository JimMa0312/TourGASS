package com.wollon.tourgass.activity.plan;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.wollon.tourgass.R;
import com.wollon.tourgass.activity.base.BaseActivity;
import com.wollon.tourgass.dto.Plan;
import com.wollon.tourgass.util.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by 漫聆默 on 2017/11/29 0029.
 */

public class PlanAddActivity extends BaseActivity {
    private EditText titleEditText;
    private EditText detailsEditText;
    private Button timeTextView;
    private Button submitButton;

    private Plan plan;
    private String time;
    private Calendar cal;
    private int year,month,day;

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.fragment_plan_edit);
        getDate();

        titleEditText=(EditText) findViewById(R.id.plan_title);
        detailsEditText=(EditText) findViewById(R.id.plan_details);
        timeTextView=(Button) findViewById(R.id.plan_time);
        submitButton=(Button) findViewById(R.id.plan_submit);

        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        time=year+"-"+(month+1)+"-"+day;
                        timeTextView.setText(time);
                    }
                };
                DatePickerDialog dialog=new DatePickerDialog(context,dateSetListener,year,month,day);
                dialog.show();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plan=new Plan();
                plan.setTitle(titleEditText.getText().toString());
                plan.setDetails(detailsEditText.getText().toString());
                Date date= DateUtil.strToDateShort(time);
                plan.setTime(date);
                PlanLab planLab=PlanLab.get(context);
                List<Plan> list=planLab.getPlan();
                list.add(plan);
                finish();
            }
        });
    }
    private void getDate() {
        cal=Calendar.getInstance();
        year=cal.get(Calendar.YEAR);       //获取年月日时分秒
        Log.i("wxy","year"+year);
        month=cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        day=cal.get(Calendar.DAY_OF_MONTH);
    }
}
