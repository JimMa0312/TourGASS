package com.wollon.tourgass.activity.scene;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.wollon.tourgass.R;

import java.util.List;

/**
 * Created by JimMa on 2017/11/30.
 */

public class SceneAdapter extends RecyclerView.Adapter<SceneAdapter.ViewHolder>{
    private List<PoiItem> mPois;

    public SceneAdapter(List<PoiItem> mPois) {
        this.mPois = mPois;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.scene_list_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PoiItem poiItem=mPois.get(position);
        holder.sceneName.setText(poiItem.getTitle());
        holder.sceneAddress.setText(poiItem.getCityName()+" "+poiItem.getSnippet());
    }

    @Override
    public int getItemCount() {
        return mPois.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView sceneName;
        TextView sceneAddress;

        public ViewHolder(View itemView) {
            super(itemView);
            sceneName=(TextView)itemView.findViewById(R.id.scene_name);
            sceneAddress=(TextView)itemView.findViewById(R.id.scene_address);
        }
    }
}
