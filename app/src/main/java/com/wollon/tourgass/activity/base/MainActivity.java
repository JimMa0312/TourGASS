package com.wollon.tourgass.activity.base;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.wollon.tourgass.R;
import com.wollon.tourgass.util.AMapUtil;
import com.wollon.tourgass.util.MD5Utils;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.wollon.tourgass.util.overlay.PoiOverlay;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements AMap.OnMyLocationChangeListener,AMap.InfoWindowAdapter,Inputtips.InputtipsListener,TextWatcher,PoiSearch.OnPoiSearchListener,INaviInfoCallback{
    private UiSettings mUiSetting;
    private AMap aMap;
    private AMapLocationClient mapLocationClient=null;//调用AMapLocationCLient对象

    private MyLocationStyle myLocationStyle;
    private AMapLocationClientOption mLocationOption=null;//声明AMapLocationClientOption对象

    private MapView mMapView=null;

    private AutoCompleteTextView searchText;// 输入搜索关键字
    private String keyWord = "";// 要输入的poi搜索关键字
    private ProgressDialog progDialog = null;// 搜索时进度条
    private PoiResult poiResult; // poi返回的结果
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索

    private LatLng myLocationLat;

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
        setUpPOIMap();
    }

    /**
     *
     */
    private void setUpPOIMap(){
        final Button searButton=(Button)findViewById(R.id.poi_select_btn);//POI查询按钮
        //TODO searButton 绑定监听
        searButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchButton();
            }
        });

        searchText=(AutoCompleteTextView)findViewById(R.id.poi_findkey);
        searchText.addTextChangedListener(this);
    }



    @Override
    public void onMyLocationChange(Location location) {
        if(location != null) {
            Log.e("amap", "onMyLocationChange 定位成功， lat: " + location.getLatitude() + " lon: " + location.getLongitude());
            myLocationLat=new LatLng(location.getLatitude(),location.getLongitude());
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
                        startMapNav(marker);
                        break;
                }
            }
        };

        addPlanBtn.setOnClickListener(inforWindowClickListener);
        routeBtn.setOnClickListener(inforWindowClickListener);
    }

    /**
     * 点击搜索按钮
     */
    public void searchButton(){
        keyWord= AMapUtil.checkEditText(searchText);
        if ("".equals(keyWord)){
            Toast.makeText(this,"请输入搜索关键字",Toast.LENGTH_SHORT).show();
            return;
        }else{
            doSearchQuery();
        }
    }

    /**
     * 开始进行POI搜索
     */
    private void doSearchQuery(){
        showProgressDialog();
        currentPage=0;
        query=new PoiSearch.Query(keyWord,"110000","");
        query.setPageSize(10);
        query.setPageNum(currentPage);

        poiSearch=new PoiSearch(this,query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog==null){
            progDialog=new ProgressDialog(this);
        }
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        progDialog.setMessage("正在搜索:\n"+keyWord);
        progDialog.show();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String newText= charSequence.toString().trim();
        if(!AMapUtil.IsEmptyOrNullString(newText)){
            InputtipsQuery inputQuery=new InputtipsQuery(newText, "".trim());
            Inputtips inputtips=new Inputtips(MainActivity.this,inputQuery);
            inputtips.setInputtipsListener(this);
            inputtips.requestInputtipsAsyn();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        if(i== AMapException.CODE_AMAP_SUCCESS){//返回成功
            List<String> listString=new ArrayList<>();
            for(int j=0;j<list.size();j++){
                listString.add(list.get(j).getName());
            }
            ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(
                    getApplicationContext(),
                    R.layout.route_inputs,listString);
            searchText.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();
        }else{
            Log.e("AMapPOI","Error->"+i);
            Toast.makeText(this,"Error: "+i,Toast.LENGTH_SHORT).show();
        }
        }

        @Override
        public void onPoiSearched(PoiResult result, int i) {
            dissmissProgressDialog();//隐藏对话框
            if(i==AMapException.CODE_AMAP_SUCCESS){
                if (result!=null && result.getQuery()!=null){
                    if(result.getQuery().equals(query)){
                        this.poiResult=result;
                        //取得搜索到的poiitems有多少页
                        List<PoiItem> poiItems=poiResult.getPois(); //取得第一页的poition数据，页数从0开始
                        List<SuggestionCity>suggestionCities=poiResult.getSearchSuggestionCitys();//当搜索不到poiitem数据时，会返回有搜索关键字的城市

                        if(poiItems!=null && poiItems.size()>0){
                            aMap.clear();//清理之前的图标
                            PoiOverlay poiOverlay=new PoiOverlay(poiItems,aMap);
                            poiOverlay.removeFromMap();
                            poiOverlay.addToMap();
                            poiOverlay.zommToSpan();
                        }else if(suggestionCities !=null
                                && suggestionCities.size()>0){
                            //显示推荐城市
                        }else{
                            Toast.makeText(this,"对不起，没有相关数据",Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(this,"对不起，没有相关数据",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this,"错误： "+i,Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 隐藏对话框
     */
    private void dissmissProgressDialog() {
        if (progDialog!=null){
            progDialog.dismiss();
        }
    }

    private void startMapNav(Marker endMarker){
        Poi start=new Poi("当前位置",myLocationLat,"");
        Poi endPoi=new Poi(endMarker.getTitle(),endMarker.getPosition(),"");
        AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(),new AmapNaviParams(start,null,endPoi, AmapNaviType.DRIVER),MainActivity.this);
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    /**
     * 导航初始化失败时回调方法
     */
    @Override
    public void onInitNaviFailure() {

    }

    /**
     * 导航播报信息回调函数。s
     * @param  s 语音播报文字
     **/
    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onArriveDestination(boolean b) {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onStopSpeaking() {

    }
}