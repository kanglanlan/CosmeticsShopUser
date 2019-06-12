package com.meida.cosmeticsshopuser.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.MapChoiceRecyclerAdapter;
import com.meida.cosmeticsshopuser.adapter.MapChoiceTipRecyclerAdapter;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.utils.LoggerUtil;
import com.meida.cosmeticsshopuser.utils.PermissionHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MapChoiceActivity extends BaseActivity implements AMapLocationListener,
        GeocodeSearch.OnGeocodeSearchListener{


    @Bind(R.id.mapView)
    MapView map;

    @Bind(R.id.recyclerView)
    RecyclerView recycler_view;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private MapChoiceRecyclerAdapter adapter;

    private AMap aMap;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationOption = null;

    Marker screenMarker = null;
    //是否是第一次定位
    private boolean isloaction = true;

    private boolean isClickMove = false;

    private LatLonPoint latLonPoint;

    private RegeocodeResult regeocodeResult;


    private EditText editText;
    private View cancel;
    private View mapArea;
    private RecyclerView inputRecycler;
    private MapChoiceTipRecyclerAdapter inputAdapter;
    private GeocodeSearch geocodeSearch;
    private Tip tip;


    /**
     * 检查权限
     */
    private void checkPermission() {
        PermissionHelper helper = new PermissionHelper(this);
        helper.requestPermissions(new PermissionHelper.PermissionListener() {
                                      @Override
                                      public void doAfterGrand(String... permission) {
                                          initMap();
                                      }

                                      @Override
                                      public void doAfterDenied(String... permission) {

                                      }
                                  }, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE);
    }

    private void initMap() {
        if (aMap == null) {
            aMap = map.getMap();
        }

        aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                addMarkersToMap();
            }
        });

        adapter = new MapChoiceRecyclerAdapter(this, null);
        recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(adapter);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                searchAddress(latLonPoint);
            }
        });
        adapter.setItemOnItemClickListener(new MapChoiceRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, PoiItem poiItem) {
                isClickMove = true;
                changeCamera(new LatLng(poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude()), new AMap.CancelableCallback() {
                    @Override
                    public void onFinish() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isClickMove = false;
                            }
                        }, 200);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });

        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                if (!isClickMove) {
                    latLonPoint = new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude);
                    refreshLayout.setEnableRefresh(true);
                    recycler_view.scrollToPosition(0);
                    refreshLayout.autoRefresh(100);
                }
            }
        });

        addMarkersToMap();
        initMyLocation();
    }

    private void initMyLocation() {
        locationClient = new AMapLocationClient(this);
        locationOption = new AMapLocationClientOption();
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        locationOption.setInterval(10000);

        locationClient.setLocationListener(this);
        locationClient.setLocationOption(locationOption);
        locationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                aMapLocation.getLatitude();//获取纬度
                aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);//定位时间
                LatLng latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                if (isloaction) {
                    isloaction = false;
                    locationClient.stopLocation();
                    changeCamera(latLng, null);
                } else {
                    //绘制当前定位位置点
                }
            } else {
                Toast.makeText(this, "定位失败" + aMapLocation.getErrorCode() + " " + aMapLocation.getErrorInfo(), Toast.LENGTH_SHORT).show();
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(LatLng latLng, AMap.CancelableCallback callback) {
        aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                latLng, 15, 0, 0)), 500, callback);
    }

    private void searchAddress(LatLonPoint latLonPoint) {
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
        GeocodeSearch search = new GeocodeSearch(this);
        search.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                MapChoiceActivity.this.regeocodeResult = regeocodeResult;
                refreshLayout.finishRefresh();
                refreshLayout.setEnableRefresh(false);
                adapter.refresh(regeocodeResult.getRegeocodeAddress().getPois());
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });
        search.getFromLocationAsyn(query);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_choice);
        changeTitle("地图选点","确定");


        ButterKnife.bind(this);
        map.onCreate(savedInstanceState);
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        map.getLayoutParams().height = height / 2;

        tv_right_title.setText("确定");
        tv_right_title.setVisibility(View.VISIBLE);
        tv_right_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isloaction)
                    return;
                PoiItem poiItem = adapter.getCheck();
                if (poiItem == null)
                    return;

                RegeocodeAddress ad = regeocodeResult.getRegeocodeAddress();
                String province = ad.getProvince();
                String city = ad.getCity();
                String district = ad.getDistrict();

                Intent intent = new Intent();
                //intent.putExtra("poi", poiItem);
                intent.putExtra("detailAddr", poiItem.getTitle());
                intent.putExtra("lat", poiItem.getLatLonPoint().getLatitude()+"");
                intent.putExtra("lng", poiItem.getLatLonPoint().getLongitude()+"");
                intent.putExtra("province", province);
                intent.putExtra("city", city);
                intent.putExtra("district", district);
                setResult(100, intent);
                finish();
            }
        });

        editText = (EditText) findViewById(R.id.editText);
        cancel = findViewById(R.id.cancel);
        mapArea = findViewById(R.id.mapArea);
        inputRecycler = (RecyclerView) findViewById(R.id.inputRecycler);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        inputRecycler.setLayoutManager(lm);
        inputAdapter = new MapChoiceTipRecyclerAdapter(this,false);
        inputRecycler.setAdapter(inputAdapter);
        geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);
        checkPermission();

        initEvent();


    }


    private void initEvent(){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                InputtipsQuery query = new InputtipsQuery(s.toString(),"");
                Inputtips inputtips = new Inputtips(MapChoiceActivity.this,query);
                query.setCityLimit(false);
                inputtips.setInputtipsListener(inputtipsListener);
                inputtips.requestInputtipsAsyn();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    cancel.setVisibility(View.VISIBLE);
                    mapArea.setVisibility(View.GONE);
                    inputRecycler.setVisibility(View.VISIBLE);
                    tv_right_title.setVisibility(View.GONE);
                }else{
                    cancel.setVisibility(View.GONE);
                    mapArea.setVisibility(View.VISIBLE);
                    inputRecycler.setVisibility(View.GONE);
                    tv_right_title.setVisibility(View.VISIBLE);
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
                editText.clearFocus();
                hideInputWindow();
            }
        });

        inputAdapter.setItemOnItemClickListener(new MapChoiceTipRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Tip tempTip) {

                tip = tempTip;
                RegeocodeQuery query = new RegeocodeQuery(tip.getPoint(), 200,GeocodeSearch.AMAP);
                geocodeSearch.getFromLocationAsyn(query);
                //setResult(100, intent);
                //finish();

            }
        });
    }


    Inputtips.InputtipsListener inputtipsListener = new Inputtips.InputtipsListener() {

        @Override
        public void onGetInputtips(List<Tip> list, int rCode) {
            List<Tip> result = new ArrayList<>();
            if (rCode == AMapException.CODE_AMAP_SUCCESS) {// 正确返回
                for (Tip t:list){
                    if (t.getPoint()==null){

                    }else{
                        result.add(t);
                    }
                }
                inputAdapter.refresh(result);

            }
        }
    };

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        map.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.onDestroy();
    }


    /**
     * 在屏幕中心添加一个Marker
     */
    private void addMarkerInScreenCenter() {
        LatLng latLng = aMap.getCameraPosition().target;
        Point screenPosition = aMap.getProjection().toScreenLocation(latLng);
        screenMarker = aMap.addMarker(new MarkerOptions()
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.locate)));/*TODO 大头针图片*/
        //设置Marker在屏幕上,不跟随地图移动
        screenMarker.setPositionByPixels(screenPosition.x, screenPosition.y);
    }

    /**
     * 在地图上添加marker
     */
    private void addMarkersToMap() {
        addMarkerInScreenCenter();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requestCode == PermissionHelper.REQUEST_PERMISSION_CODE) {
                if (PermissionHelper.verifyPermissions(grantResults)) {
                    checkPermission();
                }
            }
        }
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        LoggerUtil.e("onRegeocodeSearched",regeocodeResult.getRegeocodeAddress().getProvince()
                +regeocodeResult.getRegeocodeAddress().getCity()+
                regeocodeResult.getRegeocodeAddress().getDistrict());
        Intent intent = new Intent();
        intent.putExtra("detailAddr", tip.getName());
        //intent.putExtra("detailAddr", regeocodeResult.getRegeocodeAddress().);
        intent.putExtra("lat", tip.getPoint().getLatitude()+"");
        intent.putExtra("lng", tip.getPoint().getLongitude()+"");
        intent.putExtra("province", regeocodeResult.getRegeocodeAddress().getProvince());
        intent.putExtra("city", regeocodeResult.getRegeocodeAddress().getCity());
        intent.putExtra("district", regeocodeResult.getRegeocodeAddress().getDistrict());
        setResult(100, intent);
        finish();
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }







}
