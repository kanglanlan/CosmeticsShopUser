package com.meida.cosmeticsshopuser.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.meida.cosmeticsshopuser.Bean.BaseBean;
import com.meida.cosmeticsshopuser.Bean.OrderEvalBean;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.CommentAdapter;
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
import butterknife.OnClick;

import static com.meida.cosmeticsshopuser.share.Datas.alldatas;

public class MyCommentActivity extends BaseActivity {

    @Bind(R.id.smart)
    SmartRefreshLayout smart;
    @Bind(R.id.rb_1)
    RadioButton rb1;
    @Bind(R.id.recycle_list)
    RecyclerView recycleList;
    @Bind(R.id.empty_view)
    LinearLayout emptyView;

    CommentAdapter adapter;
    private int pager = 1;
    private ArrayList<OrderEvalBean.OrderEvalItemBean> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comment);
        ButterKnife.bind(this);
        changeTitle("我的评价");
        initview();
        rb1.performClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(true,true);
    }

    private void initview() {
        linearLayoutManager = new LinearLayoutManager(baseContext);
        recycleList.setLayoutManager(linearLayoutManager);
        recycleList.setItemAnimator(new DefaultItemAnimator());
        adapter = new CommentAdapter(baseContext, R.layout.item_comment, data);
        recycleList.setAdapter(adapter);
        recycleList.setNestedScrollingEnabled(false);

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

    @OnClick({R.id.rb_1, R.id.rb_2, R.id.rb_3, R.id.rb_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_1:
                break;
            case R.id.rb_2:
                break;
            case R.id.rb_3:
                break;
            case R.id.rb_4:
                break;
        }
    }


    /*我的评价列表*/
    private void loadData(final boolean isRefresh, boolean showLoading){
        if (isRefresh){
            pager = 1;
        }
        mRequest01 = getRequest(Params.myEvalList,true);
        mRequest01.add("page",pager);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<OrderEvalBean>(baseContext,true,OrderEvalBean.class) {
                    @Override
                    public void doWork(OrderEvalBean result, String code) {
                        try{
                            List<OrderEvalBean.OrderEvalItemBean> beans = result.getData().getData();
                            if (isRefresh){
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

    private void checkEmpty(){
        if (adapter.getItemCount()>0){
            recycleList.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }else{
            recycleList.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
    }



}
