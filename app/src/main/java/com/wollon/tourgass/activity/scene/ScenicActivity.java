package com.wollon.tourgass.activity.scene;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.amap.api.services.core.PoiItem;
import com.google.gson.Gson;
import com.wollon.tourgass.R;
import com.wollon.tourgass.activity.base.BaseActivity;
import com.wollon.tourgass.dto.PoiListZb;

import java.util.List;

/**
 * Created by JimMa on 2017/11/30.
 */

public class ScenicActivity extends BaseActivity {

    private List<PoiItem> poiItemList;

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_scenic_list);
        String poiItemListString=getIntent().getStringExtra("pois");
        poiItemList=new Gson().fromJson(poiItemListString,PoiListZb.class).getPoiItemList();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.sceneic_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        SceneAdapter sceneAdapter=new SceneAdapter(poiItemList);
        recyclerView.setAdapter(sceneAdapter);
    }
}
