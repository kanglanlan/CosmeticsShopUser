package com.meida.cosmeticsshopuser.Activity;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.BaseBean;
import com.meida.cosmeticsshopuser.Bean.OrderBean;
import com.meida.cosmeticsshopuser.Bean.OrderReturnAbleBean;
import com.meida.cosmeticsshopuser.Bean.ReturnHisListBean;
import com.meida.cosmeticsshopuser.Bean.eventbus.ReturnHistoryRefresh;
import com.meida.cosmeticsshopuser.Bean.eventbus.UserInfoRefresh;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.GoodsReturnAdapter;
import com.meida.cosmeticsshopuser.adapter.GoodsReturnHisAdapter;
import com.meida.cosmeticsshopuser.base.App;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.CommonUtil;
import com.meida.cosmeticsshopuser.utils.TabLayoutUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReturnGoodsActivity extends BaseActivity {

    private TabLayout tabLayout;
    private RecyclerView recyclerView1;
    private View emptyView1;
    private SmartRefreshLayout smart1;
    private List<OrderReturnAbleBean.Shop> data1 = new ArrayList<>();
    private GoodsReturnAdapter adapter1;

    private RecyclerView recyclerView2;
    private View emptyView2;
    private SmartRefreshLayout smart2;
    private List<ReturnHisListBean.Item> data2 = new ArrayList<>();
    private GoodsReturnHisAdapter adapter2;

    private int pager1 = 1,pager2 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_goods);
        EventBus.getDefault().register(this);
        changeTitle("退货申请");
        initView();
        initEvent();
        initData();

    }

    private void initView(){
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        recyclerView1 = (RecyclerView) findViewById(R.id.recycler1);
        emptyView1 = findViewById(R.id.emptyView1);
        TextView emptyHint1 = emptyView1.findViewById(R.id.empty_hint);
        emptyHint1.setText("您还没有退货申请信息哦~");
        smart1 = (SmartRefreshLayout) findViewById(R.id.smart1);
        LinearLayoutManager lm = new LinearLayoutManager(baseContext,LinearLayoutManager.VERTICAL,false);
        recyclerView1.setLayoutManager(lm);
        adapter1 = new GoodsReturnAdapter(baseContext,R.layout.item_order_return,data1);
        recyclerView1.setAdapter(adapter1);
        recyclerView1.setNestedScrollingEnabled(false);

        recyclerView2 = (RecyclerView) findViewById(R.id.recycler2);
        emptyView2 = findViewById(R.id.emptyView2);
        TextView emptyHint2 = emptyView2.findViewById(R.id.empty_hint);
        emptyHint2.setText("您还没有退货记录信息哦~");
        smart2 = (SmartRefreshLayout) findViewById(R.id.smart2);
        LinearLayoutManager lm1 = new LinearLayoutManager(baseContext,LinearLayoutManager.VERTICAL,false);
        recyclerView2.setLayoutManager(lm1);
        adapter2 = new GoodsReturnHisAdapter(baseContext,R.layout.item_order_return,data2);
        recyclerView2.setAdapter(adapter2);
        recyclerView2.setNestedScrollingEnabled(false);


        String[] tabTitle = new String[]{"退货申请","申请记录"};
        for (int i = 0; i<tabTitle.length; i++){
            TabLayout.Tab tab = tabLayout.newTab().setText(tabTitle[i]).setTag(i);
            tabLayout.addTab(tab);
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if ((int)tab.getTag()==0){
                    smart1.setVisibility(View.VISIBLE);
                    smart2.setVisibility(View.GONE);
                }else{
                    smart1.setVisibility(View.GONE);
                    smart2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        TabLayoutUtil.optimisedTabLayout(tabLayout, App.wid/2);
        
    }

    private void initEvent(){
        smart1.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadData1(false,false);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData1(true,false);
            }
        });

        smart2.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadData2(false,false);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData2(true,false);
            }
        });

    }

    private void initData(){
        loadData1(true,true);
        loadData2(true,false);
    }

    private void loadData1(boolean isRefresh, boolean showLoading){
        if (isRefresh){
            pager1 = 1;
        }
        mRequest01 = getRequest(Params.getReturnAbleOrderList,true);
        mRequest01.add("page",pager1);
        mRequest01.add("user_type","2");
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<OrderReturnAbleBean>(baseContext,true,OrderReturnAbleBean.class) {
                    @Override
                    public void doWork(OrderReturnAbleBean result, String code) {
                        try{
                            List<OrderReturnAbleBean.Shop> beans = result.getData().getData();
                            if (pager1==1){
                                data1.clear();
                            }
                            if (beans!=null && beans.size()>0){
                                data1.addAll(beans);
                            }
                            adapter1.notifyDataSetChanged();
                            pager1++;
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFinally(JSONObject obj, String code, boolean isNetSucceed) {
                        super.onFinally(obj, code, isNetSucceed);
                        smart1.finishLoadMore(true);
                        smart1.finishRefresh(true);
                        checkEmpty();
                    }
                },false,showLoading);

    }


    private void loadData2(boolean isRefresh, boolean showLoading){
        if (isRefresh){
            pager2 = 1;
        }
        mRequest02 = getRequest(Params.getReturnHisList,true);
        mRequest02.add("page",pager2);
        mRequest02.add("user_type","2");
        CallServer.getRequestInstance().add(baseContext, 0, mRequest02,
                new CustomHttpListener<ReturnHisListBean>(baseContext,true,ReturnHisListBean.class) {
                    @Override
                    public void doWork(ReturnHisListBean result, String code) {
                        try{
                            List<ReturnHisListBean.Item> beans = result.getData().getData();
                            if (pager2==1){
                                data2.clear();
                            }
                            if (beans!=null && beans.size()>0){
                                data2.addAll(beans);
                            }
                            adapter2.notifyDataSetChanged();
                            pager2++;
                        }catch (Exception e){

                        }
                    }

                    @Override
                    public void onFinally(JSONObject obj, String code, boolean isNetSucceed) {
                        super.onFinally(obj, code, isNetSucceed);
                        smart2.finishLoadMore(true);
                        smart2.finishRefresh(true);
                        checkEmpty();
                    }
                },false,showLoading);

    }


    private void checkEmpty(){

        if (adapter1.getItemCount()>0){
            emptyView1.setVisibility(View.GONE);
        }else{
            emptyView1.setVisibility(View.VISIBLE);
        }

        if (adapter2.getItemCount()>0){
            emptyView2.setVisibility(View.GONE);
        }else{
            emptyView2.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(ReturnHistoryRefresh refresh){
        loadData2(true,true);
        loadData1(true,false);
    }




}
