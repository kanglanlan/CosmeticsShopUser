package com.meida.cosmeticsshopuser.Activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.meida.cosmeticsshopuser.Bean.CityBean;
import com.meida.cosmeticsshopuser.Bean.eventbus.LocationSuccess;
import com.meida.cosmeticsshopuser.MyView.LetterBarView;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.CityAdapter;
import com.meida.cosmeticsshopuser.adapter.viewpager.HotCityAdapter;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.meida.cosmeticsshopuser.utils.PreferencesUtils;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class CitySecActivity extends BaseActivity {

    private List<CityBean.Area> hotAreaData = new ArrayList<>();

    private List<CityBean.City> cityData = new ArrayList<>();

    private ExpandableListView listView;
    private CityAdapter cityAdapter;
    private LetterBarView letterBar;

    private RecyclerView hotRecycler;
    private HotCityAdapter hotCityAdapter;

    private View locationView;
    private TextView currCity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_sec);
        EventBus.getDefault().register(this);
        changeTitle("选择城市");
        initView();
        initEvent();
        initData();
    }

    private void initView(){
        //initTestData();
        locationView = findViewById(R.id.locationView);
        currCity = (TextView) findViewById(R.id.currCity);
        if (Params.aMapLocation!=null){
            currCity.setText(Params.aMapLocation.getCity());
            currCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new LocationSuccess(Params.aMapLocation,1));
                    finish();
                }
            });
        }else{
            currCity.setText("定位失败");
            currCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.instance.relocateCity();
                }
            });
        }

        View hotView = getLayoutInflater().inflate(R.layout.layout_hot_city,null);
        hotRecycler = hotView.findViewById(R.id.hotRecycler);
        hotCityAdapter = new HotCityAdapter(baseContext,R.layout.item_hot_city,hotAreaData);
        GridLayoutManager glm = new GridLayoutManager(baseContext,3,GridLayoutManager.VERTICAL,false);
        hotRecycler.setLayoutManager(glm);
        hotRecycler.setAdapter(hotCityAdapter);
        hotRecycler.setNestedScrollingEnabled(false);
        hotCityAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Params.chooseArea = hotAreaData.get(position);
                EventBus.getDefault().post(new LocationSuccess(null,2));
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        listView = (ExpandableListView) findViewById(R.id.listView);
        cityAdapter = new CityAdapter(baseContext,cityData);
        cityAdapter.setHotView(hotView);
        listView.setAdapter(cityAdapter);
        listView.setGroupIndicator(null);
        listView.setChildIndicator(null);
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

        letterBar = (LetterBarView) findViewById(R.id.letterBar);
        letterBar.setOnLetterSelectListener(new LetterBarView.OnLetterSelectListener() {
            @Override
            public void onLetterSelect(String s) {
                /*if ("#".equals(s)){
                    listView.scrollTo(0,0);
                }else{*/
                    listView.setSelectedGroup(cityAdapter.getLetterPosition(s));
                /*}*/

            }
        });

        cityAdapter.setListener(new CityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int groupPosition, int childPosition) {
                Params.chooseArea = cityData.get(groupPosition).getLists().get(childPosition);
                EventBus.getDefault().post(new LocationSuccess(null,2));
                finish();
            }
        });


    }

    private void openExpandList() {
        for (int i = 0; i < cityAdapter.getGroupCount(); i++) {
            listView.expandGroup(i);
        }
    }

    private void initEvent(){

    }

    private void initData(){
        try{
            List<CityBean.Area> hots = (List<CityBean.Area>)PreferencesUtils.getList(baseContext,Params.KEY_HOT_CITY_LIST);
            List<CityBean.City> cities = ( List<CityBean.City>)PreferencesUtils.getList(baseContext,Params.KEY_CITY_LIST);
            if (hots!=null && hots.size()>0  && cities!=null && cities.size()>0){
                hotAreaData.clear();
                hotAreaData.addAll(hots);
                hotCityAdapter.notifyDataSetChanged();
                cityData.clear();
                cityData.addAll(cities);
                cityAdapter.refreshData(cityData);
                openExpandList();
            }else{
                getCityData();
            }

        }catch (Exception e){

        }

    }

    private void getCityData(){
        mRequest01 = getRequest(Params.getCityList,false);
        CallServer.getRequestInstance().add(baseContext, 0,mRequest01,
                new CustomHttpListener<CityBean>(baseContext,true,CityBean.class) {
            @Override
            public void doWork(CityBean data, String code) {
                try{
                    List<CityBean.Area> hots = data.getData().getHot();
                    List<CityBean.City> cities = data.getData().getCity();
                    if (hots!=null && hots.size()>0){
                        hotAreaData.clear();
                        hotAreaData.addAll(hots);
                        hotCityAdapter.notifyDataSetChanged();
                    }
                    if (cities!=null && cities.size()>0){
                        cityData.clear();
                        cityData.addAll(cities);
                        cityAdapter.refreshData(cityData);
                        openExpandList();
                    }
                    PreferencesUtils.putList(baseContext,Params.KEY_CITY_LIST,cities);
                    PreferencesUtils.putList(baseContext,Params.KEY_HOT_CITY_LIST,hots);
                }catch (Exception e){

                }
            }
        },false,true);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(LocationSuccess refresh){
        if (refresh.getaMapLocation()==null){
            currCity.setText("定位失败");
        }else{
            currCity.setText(refresh.getaMapLocation().getCity());
        }

    }


}
