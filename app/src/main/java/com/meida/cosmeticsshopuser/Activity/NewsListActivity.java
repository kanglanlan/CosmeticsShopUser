package com.meida.cosmeticsshopuser.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.meida.cosmeticsshopuser.Bean.BaseBean;
import com.meida.cosmeticsshopuser.Bean.NewsBean;
import com.meida.cosmeticsshopuser.Bean.NewsItemBean;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.InformationAdapter;
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

public class NewsListActivity extends BaseActivity {

    private SmartRefreshLayout smart;
    private RecyclerView recyclerView;
    private View emptyView;
    private InformationAdapter adapter;
    private ArrayList<NewsItemBean> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        changeTitle("新闻资讯");
//        tv_right_title.setVisibility(View.VISIBLE);
//        tv_right_title.setText("刷新");
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
        adapter = new InformationAdapter(baseContext,R.layout.item_zixun, data);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void initEvent(){
        smart.setEnableLoadMoreWhenContentNotFull(true);
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
        mRequest01 = getRequest(Params.getNewsList,false);
        mRequest01.add("page",pager);
        mRequest01.add("size",10);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<NewsBean>(baseContext,true,NewsBean.class) {
                    @Override
                    public void doWork(NewsBean result, String code) {
                        try{
                            List<NewsItemBean> beans = result.getData().getData();
                            if (pager==1){
                                data.clear();
                            }

                            if (beans!=null && beans.size()>0){
                                data.addAll(beans);
                                smart.setNoMoreData(false);
                            }else{
                                smart.resetNoMoreData();
                            }

                            adapter.notifyDataSetChanged();
                            pager++;

                        }catch (Exception e){

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
