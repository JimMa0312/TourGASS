package com.wollon.tourgass.util.overlay;

/**
 * Created by JimMa on 2017/11/27.
 */

import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.services.core.PoiItem;
import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Poi图层类。在高德地图中，可以用此类来创建Poi图层。
 */
public class PoiOverlay {
    private List<PoiItem> mPois;
    private AMap aAmap;
    private ArrayList<Marker> mPoiMarks=new ArrayList<>();

    /**
     * 通过此构造函数创建Poi图层
     * @param mPois
     * @param aAmap
     */
    public PoiOverlay(List<PoiItem> mPois, AMap aAmap) {
        this.mPois = mPois;
        this.aAmap = aAmap;
    }

    /**
     * 添加Marker到地图中
     */
    public void addToMap(){
        try {
            for(int i=0;i<mPois.size();i++){
                Marker marker=aAmap.addMarker(getMarkePotions(i));
                marker.setObject(i);
                mPoiMarks.add(marker);
            }
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    /**
     * 去掉PoiOverlay上所有的Marker
     */
    public void removeFromMap(){
        for(Marker marker:mPoiMarks){
            marker.remove();
        }
    }

    /**
     * 移动镜头到当前的视角
     */
    public void zommToSpan(){
        try {
            if (mPois!=null&&mPois.size()>0){
                if(aAmap==null)
                    return;
                if(mPois.size()==1){
                    aAmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mPois.get(0).getLatLonPoint().getLatitude(),
                            mPois.get(0).getLatLonPoint().getLongitude()),18f));
                }else{
                    LatLngBounds bounds=getLatLngBouds();
                    aAmap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds,5));
                }
            }
        }catch (Throwable e){
            e.printStackTrace();
        }
    }



    private MarkerOptions getMarkePotions(int index){
        return  new MarkerOptions()
                .position(
                        new LatLng(mPois.get(index).getLatLonPoint().getLatitude(),mPois.get(index).getLatLonPoint().getLongitude())).title(getTitle(index)).snippet(getSnippet(index));
    }

    private String getSnippet(int index) {
        return mPois.get(index).getSnippet();
    }

    private String getTitle(int index) {
        return mPois.get(index).getTitle();
    }

    public LatLngBounds getLatLngBouds() {
        LatLngBounds.Builder b=LatLngBounds.builder();
        for(int i=0;i<mPois.size();i++){
            b.include(new LatLng(mPois.get(i).getLatLonPoint().getLatitude(),mPois.get(i).getLatLonPoint().getLongitude()));
        }
        return b.build();
    }
}
