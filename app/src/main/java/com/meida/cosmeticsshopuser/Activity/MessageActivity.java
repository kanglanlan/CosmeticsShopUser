package com.meida.cosmeticsshopuser.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.meida.cosmeticsshopuser.Bean.MessageBean;
import com.meida.cosmeticsshopuser.Bean.MessageNewsBean;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.PingTaiMessageAdapter;
import com.meida.cosmeticsshopuser.adapter.SysMessageAdapter;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.CommonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MessageActivity extends BaseActivity {

    @Bind(R.id.smart)
    SmartRefreshLayout smart;
    @Bind(R.id.tablayout_mo)
    TabLayout tablayoutMo;
    @Bind(R.id.recycle_list1)
    RecyclerView recycleLisst1;
    @Bind(R.id.recycle_list2)
    RecyclerView recycleLisst2;
    @Bind(R.id.empty_view)
    LinearLayout emptyView;
    SysMessageAdapter sysadapter;
    PingTaiMessageAdapter pingtaiadapter;
    private ArrayList<MessageBean.MessageItemBean> sysData = new ArrayList<>();
    private ArrayList<MessageNewsBean.MessageNewsItemBean> ptData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        changeTitle("消息中心");
        init();
        loadData(true,true,"2");
    }

    boolean secondIn = false;
    private void init() {
        tablayoutMo.addTab(tablayoutMo.newTab().setText("系统消息"));
        tablayoutMo.addTab(tablayoutMo.newTab().setText("平台信息"));
        tablayoutMo.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    recycleLisst1.setVisibility(View.VISIBLE);
                    recycleLisst2.setVisibility(View.GONE);
                }
                if (tab.getPosition() == 1) {
                    recycleLisst1.setVisibility(View.GONE);
                    recycleLisst2.setVisibility(View.VISIBLE);
                    if (!secondIn){
                        secondIn = true;
                        loadData(true,true,"1");
                    }
                }
                checkEmpty();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        CommonUtil.setIndicator(tablayoutMo, 17, 17);
        linearLayoutManager = new LinearLayoutManager(baseContext);
        linearLayoutManager2 = new LinearLayoutManager(baseContext);
        recycleLisst1.setLayoutManager(linearLayoutManager);
        recycleLisst2.setLayoutManager(linearLayoutManager2);
        sysadapter = new SysMessageAdapter(baseContext, R.layout.item_sysmessage, sysData);
        pingtaiadapter = new PingTaiMessageAdapter(baseContext, R.layout.item_pingtaimessage, ptData);
        recycleLisst1.setAdapter(sysadapter);
        recycleLisst2.setAdapter(pingtaiadapter);
        sysadapter.notifyDataSetChanged();
        pingtaiadapter.notifyDataSetChanged();

        smart.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadData(false,false,String.valueOf(2-tablayoutMo.getSelectedTabPosition()));
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData(true,false,String.valueOf(2-tablayoutMo.getSelectedTabPosition()));
            }
        });

    }

    private int pager1 = 1,pager2 = 1;
    /*获取消息列表  1平台消息 2订单消息 为空不区分类别*/
    private void loadData(boolean isRefresh,boolean showLoading,String messageType){
        mRequest01 = getRequest(Params.getMessageList,true);
        mRequest01.add("message_type",messageType);
        if ("1".equals(messageType)){
            if (isRefresh){
                pager2 = 1;
            }
            mRequest01.add("page",pager2);
        }else if ("2".equals(messageType)){
            if (isRefresh){
                pager1 = 1;
            }
            mRequest01.add("page",pager1);
        }else{
            mRequest01.add("page","1");
        }

        if ("1".equals(messageType)){

            CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                    new CustomHttpListener<MessageNewsBean>(baseContext,true,MessageNewsBean.class) {
                        @Override
                        public void doWork(MessageNewsBean result, String code) {
                            try{
                                MessageNewsBean.DataBean dataBean = result.getData();
                                List<MessageNewsBean.MessageNewsItemBean> beans = dataBean.getData();
                                if (pager2==1){
                                    ptData.clear();
                                }
                                if (beans!=null && beans.size()>0){
                                    ptData.addAll(beans);
                                }
                                pingtaiadapter.notifyDataSetChanged();
                                pager2++;
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

        }else if ("2".equals(messageType)){

            CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                    new CustomHttpListener<MessageBean>(baseContext,true,MessageBean.class) {
                        @Override
                        public void doWork(MessageBean result, String code) {
                            try{
                                MessageBean.DataBean dataBean = result.getData();
                                List<MessageBean.MessageItemBean> beans = dataBean.getData();
                                if (pager1==1){
                                    sysData.clear();
                                }
                                if (beans!=null && beans.size()>0){
                                    sysData.addAll(beans);
                                }
                                sysadapter.notifyDataSetChanged();
                                pager1++;
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

    private void checkEmpty(){
        int itemCount;
        if (tablayoutMo.getSelectedTabPosition()==0){
            itemCount = sysadapter.getItemCount();
        }else{
            itemCount = pingtaiadapter.getItemCount();
        }

        if (itemCount>0){
            emptyView.setVisibility(View.GONE);
        }else{
            emptyView.setVisibility(View.VISIBLE);
        }

    }



}
