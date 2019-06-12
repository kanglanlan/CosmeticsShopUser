package com.meida.cosmeticsshopuser.Activity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.GoodsItemBean;
import com.meida.cosmeticsshopuser.Bean.GoodsListBean;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.GoodsRecyclerAdapter;
import com.meida.cosmeticsshopuser.adapter.viewpager.RadioAdapter;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TuiJianActivity extends BaseActivity {

    @Bind(R.id.smart)
    SmartRefreshLayout smart;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.empty_view)
    LinearLayout emptyView;
    GoodsRecyclerAdapter adapter;
    private List<GoodsItemBean> goodsData = new ArrayList<>();

    /*筛选弹窗相关  销量 配送方式*/
    private View sortOptionView,sortDeliveryView;
    private TextView sortSaleNum,sortDeliveryTv;

    private View maskView;
    private View downView;
    private RecyclerView typeOptionRecycler;
    private RecyclerView typeFreightRecycler;
    private List<String> typeOptionData = new ArrayList<>();
    private List<String> typeFreightData = new ArrayList<>();
    private RadioAdapter typeOptionAdapter, typeFreightAdapter;
    private int currentShowTag = -1;
    private Animation hideAnimation,inAnimation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tui_jian);
        ButterKnife.bind(this);
        initView();
        initEvent();
        initData();
    }

    private void initView(){

        GridLayoutManager glm = new GridLayoutManager(baseContext,2);
        glm.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(glm);
        adapter = new GoodsRecyclerAdapter(baseContext,R.layout.item_chanpin,goodsData);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);

        typeFreightData.clear();
        typeFreightData.add("同城配送");
        typeFreightData.add("商家自送");
        typeFreightData.add("快递配送");

        typeOptionData.clear();
        typeOptionData.add("综合");
        typeOptionData.add("销量");
        typeOptionData.add("评分");
        typeOptionData.add("价格由低至高");
        typeOptionData.add("价格由高至低");
        typeOptionData.add("包邮");
        typeOptionData.add("免自送费");
        typeOptionData.add("免配送费");

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

        findViewById(R.id.img_storeback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private String srh_salesvolume = "0";
    private String srh_distribution1 = "0";
    private String srh_distribution2 = "0";
    private String srh_distribution3 = "0";
    private void initDownView(){
        sortOptionView = findViewById(R.id.sort_option_view);
        sortDeliveryView = findViewById(R.id.sort_delivery_view);
        sortSaleNum = (TextView)findViewById(R.id.sort_saleNum);
        sortDeliveryTv = (TextView) findViewById(R.id.sort_delivery_tv);

        maskView = findViewById(R.id.mask);
        downView = findViewById(R.id.downView);
        inAnimation = AnimationUtils.loadAnimation(baseContext,R.anim.top_in);
        hideAnimation = AnimationUtils.loadAnimation(baseContext,R.anim.top_out);
        typeFreightRecycler = downView.findViewById(R.id.recyclerView3);
        LinearLayoutManager lm2 = new LinearLayoutManager(baseContext,LinearLayoutManager.VERTICAL,false);
        typeFreightRecycler.setLayoutManager(lm2);
        typeFreightAdapter = new RadioAdapter(baseContext,R.layout.item_radio_type,typeFreightData);
        typeFreightRecycler.setAdapter(typeFreightAdapter);

        typeOptionRecycler = downView.findViewById(R.id.recyclerView2);
        LinearLayoutManager lm3 = new LinearLayoutManager(baseContext,LinearLayoutManager.VERTICAL,false);
        typeOptionRecycler.setLayoutManager(lm3);
        typeOptionAdapter = new RadioAdapter(baseContext,R.layout.item_radio_type,typeOptionData);
        typeOptionRecycler.setAdapter(typeOptionAdapter);

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

        typeOptionAdapter.setListener(new RadioAdapter.OnCheckChangeListener() {
            @Override
            public void onCheckChange(int position) {
                sortSaleNum.setText(typeOptionData.get(position));
                downView.startAnimation(hideAnimation);
                downView.setVisibility(View.GONE);
                currentShowTag = -1;
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



        sortOptionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortSaleNum.setTextColor(getResources().getColor(R.color.main));
               /* sortDeliveryTv.setText("配送方式");
                sortDeliveryTv.setTextColor(Color.parseColor("#333333"));
                typeFreightAdapter.setSelectIndex(-1);*/
                if (currentShowTag==2){
                    //隐藏下拉
                    downView.startAnimation(hideAnimation);
                    downView.setVisibility(View.GONE);
                    currentShowTag = -1;
                }else if (currentShowTag == -1){
                    /*显示下拉*/
                    typeOptionRecycler.setVisibility(View.VISIBLE);
                    typeFreightRecycler.setVisibility(View.GONE);
                    downView.setVisibility(View.VISIBLE);
                    downView.startAnimation(inAnimation);
                    currentShowTag = 2;
                }else {
                    //显示下拉  转移
                    typeOptionAdapter.setSelectIndex(-1);
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
                //sortSaleNum.setText("综合");
                //sortSaleNum.setTextColor(Color.parseColor("#333333"));
                //typeOptionAdapter.setSelectIndex(-1);
                if (currentShowTag==3){
                    //隐藏下拉
                    downView.startAnimation(hideAnimation);
                    downView.setVisibility(View.GONE);
                    currentShowTag = -1;
                }else if (currentShowTag == -1){
                    /*显示下拉*/
                    typeOptionRecycler.setVisibility(View.GONE);
                    typeFreightRecycler.setVisibility(View.VISIBLE);
                    downView.setVisibility(View.VISIBLE);
                    downView.startAnimation(inAnimation);
                    currentShowTag = 3;
                }else {
                    //显示下拉  转移
                    typeFreightAdapter.setSelectIndex(-1);
                    typeOptionRecycler.setVisibility(View.GONE);
                    typeFreightRecycler.setVisibility(View.VISIBLE);
                    currentShowTag = 3;
                }
            }
        });


    }

    private void initEvent(){

    }

    private void initData(){
        loadData(true,true);
    }

    private void loadData(boolean isRefresh,boolean showLoading){
        mRequest02 = getRequest(Params.getGoodsList,false);
        if (isRefresh){
            pager = 1;
        }
        mRequest02.add("size",10);
        mRequest02.add("page",pager);
        mRequest02.add("salesvolume",srh_salesvolume);
        mRequest02.add("distribution1",srh_distribution1);
        mRequest02.add("distribution2",srh_distribution2);
        mRequest02.add("distribution3",srh_distribution3);

        if ( (Params.locateType==1) && (Params.aMapLocation!=null)){
            int cityid = Integer.parseInt(Params.aMapLocation.getAdCode());
            mRequest02.add("cityid",cityid-cityid%100);
        }else if (Params.locateType==2){
            mRequest02.add("cityid",Params.chooseArea.getId());
        }else{
            mRequest02.add("cityid",Params.LOCATE_ID);
        }

        CallServer.getRequestInstance().add(baseContext, 0, mRequest02,
                new CustomHttpListener<GoodsListBean>(baseContext,true,GoodsListBean.class) {
                    @Override
                    public void doWork(GoodsListBean result, String code) {
                        try{
                            List<GoodsItemBean> beans = result.getData().getData();
                            if (pager==1){
                                goodsData.clear();
                            }

                            if (beans!=null && beans.size()>0){
                                goodsData.addAll(beans);
                                smart.setNoMoreData(false);
                            }else{
                                smart.resetNoMoreData();
                            }

                            adapter.notifyDataSetChanged();

                            pager++;

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFinally(JSONObject obj, String code, boolean isNetSucceed) {
                        super.onFinally(obj, code, isNetSucceed);
                        smart.finishLoadMore(true);
                        smart.finishRefresh(true);
                        checkEmpty();

                    }
                },false,showLoading);

    }


    private void checkEmpty(){
        if (adapter.getItemCount()>0){
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }else{
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
    }


}
