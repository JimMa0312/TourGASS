package com.wollon.tourgass.activity.plan;

import android.content.Context;

import com.wollon.tourgass.dto.Plan;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * use 伪造数据
 * <p>
 * Created by 漫聆默 on 2017/11/23 0023.
 */

public class PlanLab {
    private static PlanLab planLab;
    private List<Plan> plansList;

    public static PlanLab get(Context context) {
        if (planLab == null) {
            planLab = new PlanLab(context);
        }
        return planLab;
    }

    private PlanLab(Context context) {
        plansList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Plan plan = new Plan();
            plan.setTitle("标题#" + (i + 1));
            plan.setSolved(i % 2 == 0);
            plansList.add(plan);
        }
    }

    public List<Plan> getPlan() {
        return plansList;
    }

    public void setPlansList(List<Plan> plansList) {
        this.plansList = plansList;
    }

    //获取数组列表
    public Plan getPlan(UUID uuid) {
        for (Plan plan : plansList) {
            if (plan.getId().equals(uuid)) {
                return plan;
            }
        }
        return null;
    }

   /* public void delete(Plan plan){
        plansList.remove(plan);
    }*/
}
