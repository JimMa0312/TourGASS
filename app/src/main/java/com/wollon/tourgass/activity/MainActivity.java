package com.wollon.tourgass.activity;

import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Text;
import com.wollon.tourgass.R;
import com.wollon.tourgass.util.MD5Utils;

import java.io.BufferedWriter;

public class MainActivity extends BaseActivity implements AMap.OnMyLocationChangeListener,AMap.InfoWindowAdapter{
    private UiSettings mUiSetting;
    private AMap aMap;
    private AMapLocationClient mapLocationClient=null;//调用AMapLocationCLient对象

    private MyLocationStyle myLocationStyle;
    private AMapLocationClientOption mLocationOption=null;//声明AMapLocationClientOption对象

    private MapView mMapView=null;

    @Override
    protected void init(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

       /* Intent intent=new Intent();
        intent.setClass(context,LoginActivity.class);*/
        mMapView=(MapView)findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        setToolbar();
        setmDrawerLayout();
        navView=(NavigationView)findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(navigationListener);

        //TODO
        /*navView.setCheckedItem(R.id.nav_call);*/

        //初始化地图控制器对象
        if(aMap == null){
            aMap=mMapView.getMap();
        }
        mUiSetting=aMap.getUiSettings();

        settingnUI();

        Log.d("AmapSHA",MD5Utils.sHA1(context));

        AutoLogin();//实现自动登陆

        registerNavElement();

        //TODO
        LatLng latLng= new LatLng(28.652556966145834,115.82443901909723);
        Marker marker=aMap.addMarker(new MarkerOptions().position(latLng).title("南昌断电断水大学").snippet("DefaultMarker"));

        LatLng latLng1=new LatLng(28.652,115.824);
        Marker marker1=aMap.addMarker(new MarkerOptions().position(latLng1).title("自定义").snippet("自定义自定义自定义自定义自定义"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    private void settingnUI(){
        mUiSetting.setCompassEnabled(true);//打开指南针



        myLocationStyle=new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，跟随设备;
        myLocationStyle.interval(2000);
        myLocationStyle.showMyLocation(true);
        myLocationStyle.strokeColor(R.color.green_focused);
        myLocationStyle.radiusFillColor(R.color.green_focused);

        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);

        mUiSetting.setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);

        mUiSetting.setScaleControlsEnabled(true);

        aMap.setOnMyLocationChangeListener(this);

        aMap.setInfoWindowAdapter(this);
    }




    @Override
    public void onMyLocationChange(Location location) {
        if(location != null) {
            Log.e("amap", "onMyLocationChange 定位成功， lat: " + location.getLatitude() + " lon: " + location.getLongitude());
            Bundle bundle = location.getExtras();
            if(bundle != null) {
                int errorCode = bundle.getInt(MyLocationStyle.ERROR_CODE);
                String errorInfo = bundle.getString(MyLocationStyle.ERROR_INFO);
                // 定位类型，可能为GPS WIFI等，具体可以参考官网的定位SDK介绍
                int locationType = bundle.getInt(MyLocationStyle.LOCATION_TYPE);

                /*
                errorCode
                errorInfo
                locationType
                */
                Log.e("amap", "定位信息， code: " + errorCode + " errorInfo: " + errorInfo + " locationType: " + locationType );
            } else {
                Log.e("amap", "定位信息， bundle is null ");

            }

        } else {
            Log.e("amap", "定位失败");
        }
    }

    /**
     * 注册Nav中的组建
     */
    private void registerNavElement(){
        View layoutHeader=(View) navView.inflateHeaderView(R.layout.nav_header);
        navImage=layoutHeader.findViewById(R.id.icon_image);
        navUserName=layoutHeader.findViewById(R.id.nav_username);
        navLOginButton=layoutHeader.findViewById(R.id.nav_login_btn);
    }


    @Override
    public View getInfoWindow(Marker marker) {
        View infoWindow=null;
            infoWindow= LayoutInflater.from(this).inflate(R.layout.custom_info_window,null);
        render(marker,infoWindow);
        return infoWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    /**
     * 自定義infowindow窗口
     * @param marker
     * @param view
     */
    public void render(final Marker marker, View view){

        Button addPlanBtn=(Button)view.findViewById(R.id.add_plan_btn);
        Button routeBtn=(Button)view.findViewById(R.id.add_route_btn);

        TextView title=(TextView)view.findViewById(R.id.poi_title);
        TextView support=(TextView)view.findViewById(R.id.poi_snippet);

        title.setText(marker.getTitle());
        support.setText(marker.getSnippet());

        /**
         * MarkInforWindow 事件处理器
         */
        View.OnClickListener inforWindowClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.add_plan_btn:
                        Toast.makeText(getApplicationContext(),"You Click Add Plane BTN "+marker.getTitle(),Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.add_route_btn:
                        Toast.makeText(getApplicationContext(),"You Click Add Route BTN 经度: "+ marker.getPosition().latitude+" 维度："+marker.getPosition().longitude,Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        addPlanBtn.setOnClickListener(inforWindowClickListener);
        routeBtn.setOnClickListener(inforWindowClickListener);
    }
}