package com.wollon.tourgass.activity.plan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.wollon.tourgass.R;
import com.wollon.tourgass.dto.Plan;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by 漫聆默 on 2017/11/23 0023.
 */
public class PlanListFragment extends Fragment {
    private RecyclerView planRecyclerView;
    private PlanAdapter planAdapter;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private List<Plan> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan_list, container, false);

        View listToolbarView = view.findViewById(R.id.plan_list_toolbar);
        toolbar = (Toolbar) listToolbarView.findViewById(R.id.app_bar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        setHasOptionsMenu(true);

        //初始化RecycleView
        planRecyclerView = (RecyclerView) view.findViewById(R.id.plan_recycler_view);
        //RecycleView设置manager
        planRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        fab=(FloatingActionButton) view.findViewById(R.id.plan_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),PlanAddActivity.class);
                startActivity(intent);
            }
        });

        updateUI();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.list_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.list_delete:
                Toast.makeText(getActivity(),"delete",Toast.LENGTH_SHORT).show();
                list=PlanLab.get(getActivity()).getPlan();
                List<UUID> indexList=new ArrayList();
                for(Plan plan:list){
                    if(plan.isSolved()){
                        indexList.add(plan.getId());
                    }
                }

                for(int i=0;i<indexList.size();i++){
                    list.remove(PlanLab.get(getActivity()).getPlan(indexList.get(i)));
                }

                updateUI();
                break;
            default:
                Toast.makeText(getActivity(),"default",Toast.LENGTH_SHORT).show();

                break;
        }

        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    //设置Adapter
    private void updateUI() {
        //获取Plan对象
        PlanLab planLab = PlanLab.get(getActivity());
        list = planLab.getPlan();
        if (planAdapter == null) {
            planAdapter = new PlanAdapter(list);
            planRecyclerView.setAdapter(planAdapter);
        } else {
            planAdapter.notifyDataSetChanged();
        }
    }

    private class PlanHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Plan mplan;

        private TextView titleTextView;
        private TextView timeTextView;
        private CheckBox solvedCheckBox;

        public PlanHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            titleTextView = (TextView) itemView.findViewById(R.id.list_item_plan_textView_title);
            timeTextView = (TextView) itemView.findViewById(R.id.list_item_plan_textView_time);
            solvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_plan_checkbox);
            solvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        mplan.setSolved(b);
                    }
                }
            });
        }

        //将Holder中的Plan数据在组件中展示
        public void bindPlan(Plan plan) {
            mplan = plan;
            titleTextView.setText(mplan.getTitle());
            timeTextView.setText(mplan.getTime().toString());
            solvedCheckBox.setChecked(mplan.isSolved());
        }

        @Override
        public void onClick(View view) {
            Intent intent = PlanEditActivity.newIntent(getActivity(), mplan.getId());
            startActivity(intent);
        }
    }

    //用于将模型层数据与item绑定
    private class PlanAdapter extends RecyclerView.Adapter<PlanHolder> {
        private List<Plan> planList;

        public PlanAdapter(List<Plan> list) {
            planList = list;
        }

        @Override
        public PlanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_plan, parent, false);

            return new PlanHolder(view);
        }

        @Override
        public void onBindViewHolder(PlanHolder holder, int position) {
            Plan plan = planList.get(position);
            //数据绑定
            holder.bindPlan(plan);
        }

        @Override
        public int getItemCount() {
            return planList.size();
        }
    }
}
