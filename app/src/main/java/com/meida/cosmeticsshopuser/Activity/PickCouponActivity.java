package com.meida.cosmeticsshopuser.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.meida.cosmeticsshopuser.Bean.CouponBean;
import com.meida.cosmeticsshopuser.Bean.NewsBean;
import com.meida.cosmeticsshopuser.Bean.NewsItemBean;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.CouponsAdapter;
import com.meida.cosmeticsshopuser.adapter.InformationAdapter;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PickCouponActivity extends BaseActivity {

    private SmartRefreshLayout smart;
    private RecyclerView recyclerView;
    private View emptyView;
    private CouponsAdapter adapter;
    private ArrayList<CouponBean.CouponItemBean> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_coupon);
        changeTitle("优惠券");
        initView();
        initEvent();
        initData();

    }


    private void initView(){
        smart = (SmartRefreshLayout) findViewById(R.id.smart);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        emptyView = findViewById(R.id.empty_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(baseContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CouponsAdapter(baseContext, R.layout.item_coupons, data);
        adapter.setPick(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent();
                intent.putExtra("data",data.get(position));
                setResult(RESULT_OK,intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    private void initEvent(){
        smart.setEnableLoadMore(false);
        smart.setEnableRefresh(true);
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData(true,true);
            }
        });
    }

    private void initData(){
        loadData(true,true);
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

    private void loadData(boolean isRefresh,boolean showLoading){
        if (isRefresh){
            pager = 1;
        }
        mRequest01 = getRequest(Params.getCouponForBuy,true);
        mRequest01.add("shopid",getIntent().getStringExtra("shopid"));
        mRequest01.add("goodsspec",getIntent().getStringExtra("goodsspec"));
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<CouponBean.DataBean>(baseContext,true,CouponBean.DataBean.class) {
                    @Override
                    public void doWork(CouponBean.DataBean result, String code) {
                        try{
                            List<CouponBean.CouponItemBean> beans = result.getData();
                            if (pager==1){
                                data.clear();
                            }

                            if (beans!=null && beans.size()>0){
                                data.addAll(beans);
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


}
