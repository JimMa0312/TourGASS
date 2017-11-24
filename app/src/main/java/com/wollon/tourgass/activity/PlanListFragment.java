package com.wollon.tourgass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.wollon.tourgass.R;
import com.wollon.tourgass.dto.Plan;

import java.util.List;


/**
 * Created by 漫聆默 on 2017/11/23 0023.
 */

public class PlanListFragment extends Fragment {
    private RecyclerView planRecyclerView;
    private PlanAdapter planAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_plan_list,container,false);



        //初始化RecycleView
        planRecyclerView=view.findViewById(R.id.plan_recycler_view);
        //RecycleView设置manager
        planRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    //设置Adapter
    private void updateUI(){
        //获取Plan对象
        PlanLab planLab=PlanLab.get(getActivity());
        List<Plan> list=planLab.getPlan();
        if(planAdapter == null){
            planAdapter=new PlanAdapter(list);
            planRecyclerView.setAdapter(planAdapter);
        }else{
            planAdapter.notifyDataSetChanged();
        }

    }

    private class PlanHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Plan mplan;

        private TextView titleTextView;
        private TextView timeTextView;
        private CheckBox solvedCheckBox;

        public PlanHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            titleTextView=(TextView) itemView.findViewById(R.id.list_item_plan_textView_title);
            timeTextView=(TextView) itemView.findViewById(R.id.list_item_plan_textView_time);
            solvedCheckBox=(CheckBox) itemView.findViewById(R.id.list_item_plan_checkbox);
        }
        //将Holder中的Plan数据在组件中展示
        public void bindPlan(Plan plan){
            mplan=plan;
            titleTextView.setText(mplan.getTitle());
            timeTextView.setText(mplan.getTime().toString());
            solvedCheckBox.setChecked(mplan.isSolved());
        }

        @Override
        public void onClick(View view) {
            Intent intent=PlanEditActivity.newIntent(getActivity(),mplan.getId());
            startActivity(intent);
        }
    }

    //用于将模型层数据与item绑定
    private class PlanAdapter extends RecyclerView.Adapter<PlanHolder>{
        private List<Plan> planList;

        public PlanAdapter(List<Plan> list){
            planList=list;
        }

        @Override
        public PlanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
            View view=layoutInflater.inflate(R.layout.list_item_plan,parent,false);

            return new PlanHolder(view);
        }

        @Override
        public void onBindViewHolder(PlanHolder holder, int position) {
            Plan plan=planList.get(position);
            //数据绑定
            holder.bindPlan(plan);
        }

        @Override
        public int getItemCount() {
            return planList.size();
        }
    }
}
