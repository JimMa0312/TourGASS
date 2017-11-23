package com.wollon.tourgass.activity;

import android.icu.text.CollationKey;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DialogTitle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.wollon.tourgass.R;
import com.wollon.tourgass.dto.Plan;

import java.util.UUID;

/**
 * Created by 漫聆默 on 2017/11/23 0023.
 */

public class PlanEditFragment extends Fragment {
    private static final String ARG_PLAN_ID="plan_id";

    private Plan plan;
    private EditText titleEditText;
    private EditText detailsEditText;
    private DatePicker timeDatePicker;
    private Button submitButton;

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

        plan=PlanLab.get(getActivity()).getPlan(planId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_plan_edit,container,false);
        titleEditText=(EditText) view.findViewById(R.id.plan_title);
        detailsEditText=(EditText) view.findViewById(R.id.plan_details);
        timeDatePicker=(DatePicker) view.findViewById(R.id.plan_time);
        submitButton=(Button) view.findViewById(R.id.plan_submit);

        titleEditText.setText(plan.getTitle());
        detailsEditText.setText(plan.getDetails());

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
}
