package com.meida.cosmeticsshopuser.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.meida.cosmeticsshopuser.Bean.WebDetailBean;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.HelpAdapter;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.WebViewUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.meida.cosmeticsshopuser.share.Datas.alldatas;

public class HelpActivity extends BaseActivity {
    @Bind(R.id.smart)
    SmartRefreshLayout smart;
    @Bind(R.id.recycle_list)
    RecyclerView recycleList;
    @Bind(R.id.empty_view)
    LinearLayout emptyView;
    HelpAdapter adapter;
    ArrayList<WebDetailBean.DataBean> datas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);
        changeTitle("帮助中心");
        initview();
        getdata();
    }

    private void getdata() {
        loadData(true);
    }

    private void initview() {
        linearLayoutManager = new LinearLayoutManager(baseContext);
        recycleList.setLayoutManager(linearLayoutManager);
        recycleList.setItemAnimator(new DefaultItemAnimator());
        adapter = new HelpAdapter(baseContext, R.layout.item_help, datas);
        recycleList.setAdapter(adapter);
        smart.setEnableLoadMore(false);
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData(false);
            }
        });
    }


    private void loadData(boolean showLoading){
        mRequest01 = getRequest(Params.getWebDetail,false);
        mRequest01.add("flag","8");
        mRequest01.add("user_type","2");/*2用户端  3商家端*/
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<WebDetailBean>(baseContext,true,WebDetailBean.class) {
                    @Override
                    public void doWork(WebDetailBean result, String code) {
                        try{
                            /*String html = data.getData().get(0).getContent();*/
                            //WebViewUtil.setWebHtml(webview,html);
                            datas.clear();
                            List<WebDetailBean.DataBean> beans = result.getData();
                            if (beans!=null && beans.size()>0){
                                datas.addAll(beans);
                            }
                            adapter.notifyDataSetChanged();
                        }catch (Exception e){

                        }

                    }

                    @Override
                    public void onFinally(JSONObject obj, String code, boolean isNetSucceed) {
                        super.onFinally(obj, code, isNetSucceed);
                        smart.finishRefresh(true);
                        smart.finishLoadMore(true);
                        checkEmpty();
                    }
                },false,showLoading);
    }


    private void checkEmpty(){
        if (adapter.getItemCount()>0){
            emptyView.setVisibility(View.GONE);
        }else{
            emptyView.setVisibility(View.VISIBLE);
        }
    }

}
