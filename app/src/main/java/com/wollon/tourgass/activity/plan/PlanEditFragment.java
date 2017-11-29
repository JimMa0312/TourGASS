package com.wollon.tourgass.activity.plan;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.wollon.tourgass.R;
import com.wollon.tourgass.dto.Plan;
import com.wollon.tourgass.util.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by 漫聆默 on 2017/11/23 0023.
 */

public class PlanEditFragment extends Fragment {
    private static final String ARG_PLAN_ID="plan_id";

    private Plan plan;
    private EditText titleEditText;
    private EditText detailsEditText;
    private Button timeTextView;
    private Button submitButton;

    private Calendar cal;
    private int year,month,day;

    public static PlanEditFragment newInstance(UUID planId){
        Bundle args=new Bundle();
        args.putSerializable(ARG_PLAN_ID,planId);
        PlanEditFragment planEditFragment=new PlanEditFragment();
        planEditFragment.setArguments(args);

        return planEditFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID planId= (UUID) getArguments().getSerializable(ARG_PLAN_ID);
        getDate();
        plan=PlanLab.get(getActivity()).getPlan(planId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_plan_edit,container,false);
        titleEditText=(EditText) view.findViewById(R.id.plan_title);
        detailsEditText=(EditText) view.findViewById(R.id.plan_details);
        timeTextView=(Button) view.findViewById(R.id.plan_time);
        submitButton=(Button) view.findViewById(R.id.plan_submit);

        titleEditText.setText(plan.getTitle());
        detailsEditText.setText(plan.getDetails());

        timeTextView.setText(plan.getTime());
        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String time=year+"-"+(month+1)+"-"+day;
                        Date date= DateUtil.strToDateShort(time);
                        plan.setTime(date);
                        timeTextView.setText(plan.getTime());
                    }
                };
                DatePickerDialog dialog=new DatePickerDialog(getActivity(),dateSetListener,year,month,day);
                dialog.show();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plan.setTitle(titleEditText.getText().toString());
                plan.setDetails(detailsEditText.getText().toString());

                getActivity().finish();
            }
        });

        return view;
    }

    private void getDate() {
        cal=Calendar.getInstance();
        year=cal.get(Calendar.YEAR);       //获取年月日时分秒
        Log.i("wxy","year"+year);
        month=cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        day=cal.get(Calendar.DAY_OF_MONTH);
    }
}
