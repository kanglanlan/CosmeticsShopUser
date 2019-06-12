package com.meida.cosmeticsshopuser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Activity.AddFindActivity;
import com.meida.cosmeticsshopuser.Bean.ActionBean;
import com.meida.cosmeticsshopuser.Bean.FindListBean;
import com.meida.cosmeticsshopuser.Bean.eventbus.SuccessEvent;
import com.meida.cosmeticsshopuser.MyView.dialog.ActionDialog;
import com.meida.cosmeticsshopuser.R;

import com.meida.cosmeticsshopuser.adapter.FindAdapter2;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyShareActivity extends BaseActivity {
    @Bind(R.id.smart)
    SmartRefreshLayout smart;
    @Bind(R.id.recycle_list)
    RecyclerView recycleList;
    @Bind(R.id.empty_view)
    LinearLayout emptyView;
    FindAdapter2 adapter;
    @Bind(R.id.tv_title_right)
    TextView tvTitleRight;
    ArrayList<FindListBean.FindItemBean> datas = new ArrayList<>();

    int tag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_share);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        tag = getIntent().getIntExtra("tag",0);
        if (tag==0){
            changeTitle("我的分享");
            tvTitleRight.setText("发布分享");
            tvTitleRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(baseContext,AddFindActivity.class);
                    startActivity(intent);
                }
            });
            tvTitleRight.setVisibility(View.VISIBLE);
        }else{
            changeTitle("我的关注分享");
        }

        initview();
        loadData(true,true);
    }

    private void initview() {
        linearLayoutManager = new LinearLayoutManager(baseContext);
        recycleList.setLayoutManager(linearLayoutManager);
        recycleList.setItemAnimator(new DefaultItemAnimator());
        recycleList.setNestedScrollingEnabled(false);
        adapter = new FindAdapter2(baseContext, R.layout.item_faxianlist, datas);
        adapter.setCanDelete(tag==0);
        recycleList.setAdapter(adapter);

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

        adapter.setListener(new FindAdapter2.OnItemActionListener() {
            @Override
            public void onDelete(FindAdapter2.ViewHolder holder, FindListBean.FindItemBean bean) {
                delete(holder.getAdapterPosition(),bean);
            }
        });

    }

    @OnClick(R.id.tv_title_right)
    public void onViewClicked() {
        StartActivity(AddFindActivity.class);
    }

    /*获取数据*/
    private void loadData(final boolean isRefresh, boolean showLoading){
        if (isRefresh){
            pager = 1;
        }
        String httpIp = "";
        if (tag==0){
            httpIp = Params.getMyShareList;
        }else{
            httpIp = Params.getFindList;
        }
        mRequest01 = getRequest(httpIp,true);
        mRequest01.add("page",pager);

        if (tag==1){
            if ((Params.aMapLocation!=null)){
                mRequest01.add("longitude",Params.aMapLocation.getLongitude());
                mRequest01.add("latitude",Params.aMapLocation.getLatitude());
            }/*else if (Params.locateType==2){
                mRequest01.add("longitude",Params.chooseArea.getLng());
                mRequest01.add("latitude",Params.chooseArea.getLat());
            }*/else{
                mRequest01.add("longitude",Params.LOCATE_LNG);
                mRequest01.add("latitude",Params.LOCATE_LAT);
            }
            mRequest01.add("fl1","1");
        }

        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<FindListBean>(baseContext,true,FindListBean.class) {
                    @Override
                    public void doWork(FindListBean result, String code) {
                        try{
                            List<FindListBean.FindItemBean> beans = result.getData().getData();

                            if (isRefresh){
                                datas.clear();
                            }

                            if (beans!=null && beans.size()>0){
                                datas.addAll(beans);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(SuccessEvent event){
        if (event.isB()){
            loadData(true,false);
        }

    }


    private void delete(int position, final FindListBean.FindItemBean bean){
        ActionDialog dialog = new ActionDialog(baseContext,"确认删除？");
        dialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
            @Override
            public void onLeftClick() {


            }

            @Override
            public void onRightClick() {
                mRequest05 = getRequest(Params.deleteShare,true);
                mRequest05.add("id",bean.getId());
                CallServer.getRequestInstance().add(baseContext, 0, mRequest05,
                        new CustomHttpListener<ActionBean>(baseContext,true,ActionBean.class) {
                            @Override
                            public void doWork(ActionBean result, String code) {
                                showtoa(result.getMsg());
                                loadData(true,true);
                            }

                            @Override
                            public void onFinally(JSONObject obj, String code, boolean isNetSucceed) {
                                super.onFinally(obj, code, isNetSucceed);
                                checkEmpty();
                            }
                        },false,true);
            }
        });
        dialog.show();

    }


    private void checkEmpty(){
        if (adapter.getItemCount()>0){
            emptyView.setVisibility(View.GONE);
        }else{
            emptyView.setVisibility(View.VISIBLE);
        }
    }



}
