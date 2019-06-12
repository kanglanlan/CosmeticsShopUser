package com.meida.cosmeticsshopuser.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.DistanceRangeBean;
import com.meida.cosmeticsshopuser.Bean.ShopGoodsBean;
import com.meida.cosmeticsshopuser.MyView.ClearEditText;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.StorelistAdapter;
import com.meida.cosmeticsshopuser.adapter.viewpager.RadioAdapter;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.PreferencesUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StoreGoodsListActivity extends BaseActivity {

    @Bind(R.id.tv_search)
    ClearEditText editText;
    @Bind(R.id.smart)
    SmartRefreshLayout smart;
    @Bind(R.id.recyclerView)
    RecyclerView recycleList;
    @Bind(R.id.empty_view)
    LinearLayout emptyView;
    StorelistAdapter adapter;
    private int pager = 1;
    ArrayList<ShopGoodsBean.ShopBean> datas = new ArrayList<>();

    /*筛选弹窗相关  附近 销量 配送方式*/
    private View sortNearView,sortOptionView,sortDeliveryView;
    private TextView sortNearTv,sortSaleNum,sortDeliveryTv;

    private View maskView;
    private View downView;
    private RecyclerView typeNearRecycler;
    private RecyclerView typeOptionRecycler;
    private RecyclerView typeFreightRecycler;
    private List<String> typeNearData = new ArrayList<>();
    private List<String> typeOptionData = new ArrayList<>();
    private List<String> typeFreightData = new ArrayList<>();
    private RadioAdapter typeNearAdapter,typeOptionAdapter,typeFreightAdapter;
    private int currentShowTag = -1;
    private Animation hideAnimation,inAnimation;

    /*距离分类原始数据*/
    private List<DistanceRangeBean.RangeBean> rangeBeans = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storelist);
        ButterKnife.bind(this);
        String str = getIntent().getStringExtra("str");
        if (!TextUtils.isEmpty(str)){
            editText.setText(str);
            editText.setSelection(str.length());
        }
        initview();
        getdata();
    }

    private void getdata() {
        List<DistanceRangeBean.RangeBean> beans = PreferencesUtils.getList(baseContext,Params.KEY_DISTANCE_RANGE);
        if (beans!=null && beans.size()>0){
            rangeBeans.clear();
            rangeBeans.addAll(beans);
            typeNearData.clear();
            for (int i = 0; i< rangeBeans.size(); i++){
                typeNearData.add(rangeBeans.get(i).getTitle());
            }
            typeNearAdapter.notifyDataSetChanged();
        }else{
            getDistanceRange();
        }
        adapter.notifyDataSetChanged();
    }

    private void initview() {
        linearLayoutManager = new LinearLayoutManager(baseContext);
        recycleList.setLayoutManager(linearLayoutManager);
        recycleList.setItemAnimator(new DefaultItemAnimator());
        adapter = new StorelistAdapter(baseContext, R.layout.item_listdianpu, datas);
        recycleList.setAdapter(adapter);
        recycleList.setNestedScrollingEnabled(false);
        initDownView();
        smart.setEnableRefresh(false);
        smart.setEnableLoadMore(true);
        smart.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadData(false,false);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData(true,false);
            }
        });

        typeFreightData.clear();
        typeFreightData.add("同城配送");
        typeFreightData.add("商家自送");
        typeFreightData.add("快递配送");
        typeFreightAdapter.notifyDataSetChanged();

        typeOptionData.clear();
        typeOptionData.add("综合");
        typeOptionData.add("销量");
        typeOptionData.add("评分");
      /*  typeOptionData.add("价格由低至高");
        typeOptionData.add("价格由高至低");
        typeOptionData.add("包邮");
        typeOptionData.add("免自送费");
        typeOptionData.add("免配送费");*/

        loadData(true,true);

    }

    private String srh_salesvolume = "0";/*1 综合 2 销量 3 评分 4 价格由低至高 5 价格高低*/
    private String srh_distribution1 = "0";
    private String srh_distribution2 = "0";
    private String srh_distribution3 = "0";
    private String srh_range = "0";
    private void initDownView(){
        sortNearView = findViewById(R.id.sort_nearView);
        sortOptionView = findViewById(R.id.sort_option_view);
        sortDeliveryView = findViewById(R.id.sort_delivery_view);
        sortNearTv = (TextView)findViewById(R.id.sort_near_tv);
        sortSaleNum = (TextView)findViewById(R.id.sort_saleNum);
        sortDeliveryTv = (TextView) findViewById(R.id.sort_delivery_tv);

        maskView = findViewById(R.id.mask);
        downView = findViewById(R.id.downView);
        inAnimation = AnimationUtils.loadAnimation(StoreGoodsListActivity.this,R.anim.top_in);
        hideAnimation = AnimationUtils.loadAnimation(StoreGoodsListActivity.this,R.anim.top_out);
        typeNearRecycler = downView.findViewById(R.id.recyclerView1);
        typeOptionRecycler = downView.findViewById(R.id.recyclerView2);
        typeFreightRecycler = downView.findViewById(R.id.recyclerView3);
        LinearLayoutManager lm1 = new LinearLayoutManager(baseContext,LinearLayoutManager.VERTICAL,false);
        typeNearRecycler.setLayoutManager(lm1);
        LinearLayoutManager lm2 = new LinearLayoutManager(baseContext,LinearLayoutManager.VERTICAL,false);
        typeOptionRecycler.setLayoutManager(lm2);
        LinearLayoutManager lm3 = new LinearLayoutManager(baseContext,LinearLayoutManager.VERTICAL,false);
        typeFreightRecycler.setLayoutManager(lm3);
        typeNearAdapter = new RadioAdapter(baseContext,R.layout.item_radio_type,typeNearData);
        typeNearRecycler.setAdapter(typeNearAdapter);
        typeOptionAdapter = new RadioAdapter(baseContext,R.layout.item_radio_type,typeOptionData);
        typeOptionRecycler.setAdapter(typeOptionAdapter);
        typeFreightAdapter = new RadioAdapter(baseContext,R.layout.item_radio_type,typeFreightData);
        typeFreightRecycler.setAdapter(typeFreightAdapter);

        inAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                findViewById(R.id.mask).setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        hideAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                findViewById(R.id.mask).setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                /*loadNetData(true,false);*/
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        findViewById(R.id.mask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downView.startAnimation(hideAnimation);
                downView.setVisibility(View.GONE);
                currentShowTag = -1;
            }
        });

        typeNearAdapter.setListener(new RadioAdapter.OnCheckChangeListener() {
            @Override
            public void onCheckChange(int position) {
                sortNearTv.setText(typeNearData.get(position));
                downView.startAnimation(hideAnimation);
                downView.setVisibility(View.GONE);
                currentShowTag = -1;
                srh_range = rangeBeans.get(position).getId();
                //srh_salesvolume = "0";
                srh_distribution1 = "0";
                srh_distribution2 = "0";
                srh_distribution3 = "0";
                loadData(true,true);
            }
        });

        typeOptionAdapter.setListener(new RadioAdapter.OnCheckChangeListener() {
            @Override
            public void onCheckChange(int position) {
                sortSaleNum.setText(typeOptionData.get(position));
                downView.startAnimation(hideAnimation);
                downView.setVisibility(View.GONE);
                currentShowTag = -1;
                //srh_range = "0";
                srh_salesvolume = String.valueOf(position+1);
               /* srh_distribution1 = "0";
                srh_distribution2 = "0";
                srh_distribution3 = "0";*/
                loadData(true,true);
            }
        });

        typeFreightAdapter.setListener(new RadioAdapter.OnCheckChangeListener() {
            @Override
            public void onCheckChange(int position) {
                sortDeliveryTv.setText(typeFreightData.get(position));
                downView.startAnimation(hideAnimation);
                downView.setVisibility(View.GONE);
                currentShowTag = -1;
                srh_range = "0";
                //srh_salesvolume = "0";
                switch (position){
                    case 0:
                        srh_distribution1 = "1";
                        srh_distribution2 = "0";
                        srh_distribution3 = "0";
                        break;
                    case 1:
                        srh_distribution1 = "0";
                        srh_distribution2 = "1";
                        srh_distribution3 = "0";
                        break;
                    case 2:
                        srh_distribution1 = "0";
                        srh_distribution2 = "0";
                        srh_distribution3 = "1";
                        break;
                }
                loadData(true,true);
            }
        });

        sortNearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortNearTv.setTextColor(getResources().getColor(R.color.main));
                sortDeliveryTv.setText("配送方式");
                //sortSaleNum.setText("综合");
                //sortSaleNum.setTextColor(Color.parseColor("#333333"));
                sortDeliveryTv.setTextColor(Color.parseColor("#333333"));
                typeFreightAdapter.setSelectIndex(-1);
                //typeOptionAdapter.setSelectIndex(-1);
                if (currentShowTag==1){
                    //隐藏下拉
                    downView.startAnimation(hideAnimation);
                    downView.setVisibility(View.GONE);
                    currentShowTag = -1;
                }else if (currentShowTag == -1){
                    /*显示下拉*/
                    typeNearRecycler.setVisibility(View.VISIBLE);
                    typeOptionRecycler.setVisibility(View.GONE);
                    typeFreightRecycler.setVisibility(View.GONE);
                    downView.setVisibility(View.VISIBLE);
                    downView.startAnimation(inAnimation);
                    currentShowTag = 1;
                }else {
                    //显示下拉  转移
                    typeNearAdapter.setSelectIndex(-1);
                    typeNearRecycler.setVisibility(View.VISIBLE);
                    typeFreightRecycler.setVisibility(View.GONE);
                    typeOptionRecycler.setVisibility(View.GONE);
                    currentShowTag = 1;

                }
            }
        });

        sortOptionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortSaleNum.setTextColor(getResources().getColor(R.color.main));
               /* sortDeliveryTv.setText("配送方式");
                sortNearTv.setText("附近");
                sortDeliveryTv.setTextColor(Color.parseColor("#333333"));
                sortNearTv.setTextColor(Color.parseColor("#333333"));
                typeFreightAdapter.setSelectIndex(-1);
                typeNearAdapter.setSelectIndex(-1);*/
                if (currentShowTag==2){
                    //隐藏下拉
                    downView.startAnimation(hideAnimation);
                    downView.setVisibility(View.GONE);
                    currentShowTag = -1;
                }else if (currentShowTag == -1){
                    /*显示下拉*/
                    typeNearRecycler.setVisibility(View.GONE);
                    typeOptionRecycler.setVisibility(View.VISIBLE);
                    typeFreightRecycler.setVisibility(View.GONE);
                    downView.setVisibility(View.VISIBLE);
                    downView.startAnimation(inAnimation);
                    currentShowTag = 2;
                }else {
                    //显示下拉  转移
                    typeOptionAdapter.setSelectIndex(-1);
                    typeNearRecycler.setVisibility(View.GONE);
                    typeOptionRecycler.setVisibility(View.VISIBLE);
                    typeFreightRecycler.setVisibility(View.GONE);
                    currentShowTag = 2;
                }


            }
        });

        sortDeliveryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortDeliveryTv.setTextColor(getResources().getColor(R.color.main));
                sortNearTv.setText("附近");
                //sortSaleNum.setText("综合");
                sortNearTv.setTextColor(Color.parseColor("#333333"));
                //sortSaleNum.setTextColor(Color.parseColor("#333333"));
                typeNearAdapter.setSelectIndex(-1);
                //typeOptionAdapter.setSelectIndex(-1);
                if (currentShowTag==3){
                    //隐藏下拉
                    downView.startAnimation(hideAnimation);
                    downView.setVisibility(View.GONE);
                    currentShowTag = -1;
                    typeNearAdapter.setSelectIndex(-1);
                }else if (currentShowTag == -1){
                    /*显示下拉*/
                    typeNearRecycler.setVisibility(View.GONE);
                    typeOptionRecycler.setVisibility(View.GONE);
                    typeFreightRecycler.setVisibility(View.VISIBLE);
                    downView.setVisibility(View.VISIBLE);
                    downView.startAnimation(inAnimation);
                    currentShowTag = 3;
                }else {
                    //显示下拉  转移
                    typeFreightAdapter.setSelectIndex(-1);
                    typeNearRecycler.setVisibility(View.GONE);
                    typeOptionRecycler.setVisibility(View.GONE);
                    typeFreightRecycler.setVisibility(View.VISIBLE);
                    currentShowTag = 3;
                }
            }
        });


    }

    @OnClick({R.id.img_storeback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_storeback:
                finish();
                break;
        }
    }

    /*获取商家距离分类*/
    private void getDistanceRange() {
        mRequest01 = getRequest(Params.getDistanceRange, false);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<DistanceRangeBean>(baseContext, true, DistanceRangeBean.class) {
                    @Override
                    public void doWork(DistanceRangeBean data, String code) {
                        try {
                            List<DistanceRangeBean.RangeBean> beans = data.getData().getData();
                            if (beans!=null){
                                rangeBeans.clear();
                                rangeBeans.addAll(beans);
                                PreferencesUtils.putList(baseContext,Params.KEY_DISTANCE_RANGE,beans);
                                typeNearData.clear();
                                for (int i = 0; i< rangeBeans.size(); i++){
                                    typeNearData.add(rangeBeans.get(i).getTitle());
                                }
                                typeNearAdapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {

                        }
                    }
                }, false, false);
    }

    /*获取店铺列表*/
    private void loadData(final boolean isRefresh,boolean showLoading){
        if (isRefresh){
            pager = 1;
        }else{
            pager = pager+1;
        }
        mRequest02 = getRequest(Params.getShopGoodsList,false);
        if ((Params.aMapLocation!=null)){
            mRequest02.add("longitude",Params.aMapLocation.getLongitude());
            mRequest02.add("latitude",Params.aMapLocation.getLatitude());
        }/*else if (Params.locateType==2){
            mRequest02.add("longitude",Params.chooseArea.getLng());
            mRequest02.add("latitude",Params.chooseArea.getLat());
        }*/else{
            mRequest02.add("longitude",Params.LOCATE_LNG);
            mRequest02.add("latitude",Params.LOCATE_LAT);
        }
        mRequest02.add("page",pager);
        mRequest02.add("salesvolume",srh_salesvolume);
        mRequest02.add("distribution1",srh_distribution1);
        mRequest02.add("distribution2",srh_distribution2);
        mRequest02.add("distribution3",srh_distribution3);
        mRequest02.add("range",srh_range);
        mRequest02.add("keywords",editText.getText().toString().trim());
        CallServer.getRequestInstance().add(baseContext, 0, mRequest02,
                new CustomHttpListener<ShopGoodsBean>(baseContext, true, ShopGoodsBean.class) {
                    @Override
                    public void doWork(ShopGoodsBean data, String code) {
                        try {
                            List<ShopGoodsBean.ShopBean> beans = data.getData().getData();
                            if (isRefresh) {
                                datas.clear();
                            }
                            if (beans != null && beans.size() > 0) {
                                datas.addAll(beans);
                                smart.setNoMoreData(false);
                            }else{
                                smart.setNoMoreData(true);
                            }
                            adapter.notifyDataSetChanged();

                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onFinally(JSONObject obj, String code, boolean isNetSucceed) {
                        super.onFinally(obj, code, isNetSucceed);
                        smart.finishLoadMore();
                        smart.finishRefresh();
                        checkEmpty();
                    }
                }, true, showLoading);


    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction()) {
            String edStr = editText.getText().toString().trim();
            if (TextUtils.isEmpty(edStr)){
                showtoa("请输入关键词");
            }else{
               loadData(true,true);
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    private void checkEmpty(){
        if (adapter.getItemCount()>0){
            emptyView.setVisibility(View.GONE);
            recycleList.setVisibility(View.VISIBLE);
        }else{
            emptyView.setVisibility(View.VISIBLE);
            recycleList.setVisibility(View.GONE);
        }
    }


}
