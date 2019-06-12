package com.meida.cosmeticsshopuser.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.MapChoiceRecyclerAdapter;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.LoggerUtil;

import java.util.ArrayList;
import java.util.List;

public class ChoiceAddrActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private MapChoiceRecyclerAdapter adapter;
    private List<PoiItem> data = new ArrayList<>();
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_addr);
        changeTitle("选择位置","确定");
        initView();
        initEvent();
        initData();
    }

    private void initView(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(baseContext,LinearLayout.VERTICAL,false);
        recyclerView.setLayoutManager(lm);
        adapter = new MapChoiceRecyclerAdapter(baseContext,data);
        recyclerView.setAdapter(adapter);
        editText = (EditText) findViewById(R.id.editText);
    }

    private void initEvent(){
        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Params.aMapLocation!=null){
                    LoggerUtil.e("aMapLocation",Params.aMapLocation.toString());
                    //searchAddress(new LatLonPoint(Params.aMapLocation.getLatitude(),Params.aMapLocation.getLongitude()));
                    String keyWord = editText.getText().toString().trim();
                    search(keyWord,Params.aMapLocation.getCityCode(),new LatLonPoint(Params.aMapLocation.getLatitude(),Params.aMapLocation.getLongitude()));
                }
            }
        });

        tv_right_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PoiItem poiItem = adapter.getCheck();
                if (poiItem == null){
                    showtoa("请选择位置");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("detailAddr", poiItem.getTitle());
                intent.putExtra("lat", poiItem.getLatLonPoint().getLatitude()+"");
                intent.putExtra("lng", poiItem.getLatLonPoint().getLongitude()+"");
                setResult(100, intent);
                finish();
            }
        });

    }

    private void initData(){
        if (Params.aMapLocation!=null){
            LoggerUtil.e("aMapLocation",Params.aMapLocation.toString());
            searchAddress(new LatLonPoint(Params.aMapLocation.getLatitude(),Params.aMapLocation.getLongitude()));
            /*String keyWord = editText.getText().toString().trim();
            search(keyWord,Params.aMapLocation.getCityCode(),new LatLonPoint(Params.aMapLocation.getLatitude(),Params.aMapLocation.getLongitude()));*/
        }
    }


    private void searchAddress(LatLonPoint latLonPoint) {
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
        GeocodeSearch search = new GeocodeSearch(this);
        search.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                adapter.refresh(regeocodeResult.getRegeocodeAddress().getPois());
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });
        search.getFromLocationAsyn(query);
    }


    private void search(String keyWord,String cityCode,LatLonPoint latLonPoint){
        PoiSearch.Query query = new PoiSearch.Query(keyWord, "", cityCode);
        query.setLocation(latLonPoint);
        query.setPageSize(30);
        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                adapter.refresh(poiResult.getPois());
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        poiSearch.searchPOIAsyn();
    }

}
