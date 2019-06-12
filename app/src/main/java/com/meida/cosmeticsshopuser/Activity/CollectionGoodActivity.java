package com.meida.cosmeticsshopuser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.meida.cosmeticsshopuser.Bean.ActionBean;
import com.meida.cosmeticsshopuser.Bean.CollectGoodsBean;
import com.meida.cosmeticsshopuser.MyView.dialog.ActionDialog;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.CollectGoodsAdapter;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CollectionGoodActivity extends BaseActivity {
    @Bind(R.id.smart)
    SmartRefreshLayout smart;
    @Bind(R.id.recycle_list)
    RecyclerView recycleList;
    @Bind(R.id.empty_view)
    LinearLayout emptyView;
    ArrayList<CollectGoodsBean.CollectGoodsItemBean> datas = new ArrayList<>();
    private CollectGoodsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_good);
        ButterKnife.bind(this);
        changeTitle("收藏商品","清空");
        initview();
        getdata(true);
    }

    private void getdata(boolean b) {

        loadData(true,true);

    }

    private void initview() {
        linearLayoutManager = new LinearLayoutManager(baseContext);
        recycleList.setLayoutManager(linearLayoutManager);
        recycleList.setItemAnimator(new DefaultItemAnimator());
        adapter = new CollectGoodsAdapter(baseContext,R.layout.item_swipe_collect_goods,datas);
        recycleList.setAdapter(adapter);
        adapter.setListener(new CollectGoodsAdapter.OnItemListener() {
            @Override
            public void onDelete(ViewHolder holder, CollectGoodsBean.CollectGoodsItemBean bean) {
                deleteSingle(holder,bean);
            }

            @Override
            public void onClickItem(int position, CollectGoodsBean.CollectGoodsItemBean bean) {
                Intent intent = new Intent(baseContext,ProductDetailActivity.class);
                intent.putExtra("id",bean.getId());
                startActivity(intent);
            }
        });

        tv_right_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanAll();
            }
        });

        /*findViewById(R.id.rootView).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    SwipeMenuLayout viewCache = SwipeMenuLayout.getViewCache();
                    if (null != viewCache) {
                        viewCache.smoothClose();
                    }
                }
                return false;
            }
        });*/


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


    private void checkEmpty(){
        if (adapter.getItemCount()>0){
            tv_right_title.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }else{
            tv_right_title.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    private void cleanAll(){
        ActionDialog dialog = new ActionDialog(baseContext,"确定要清空所有商品吗？");
        dialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
                clearData();
            }
        });
        dialog.show();
    }

    private void deleteSingle(final ViewHolder holder, final CollectGoodsBean.CollectGoodsItemBean bean){
        final int index = holder.getAdapterPosition();
        ActionDialog deleteDialog = new ActionDialog(baseContext,"确认删除？");
        deleteDialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
                deleteSingle(index);
            }
        });
        deleteDialog.show();
    }


    private int pager = 1;
    /*获取收藏店铺列表*/
    private void loadData(final boolean isRefresh, boolean showLoading){
        if (isRefresh){
            pager = 1;
        }
        mRequest01 = getRequest(Params.getCollectGoodsList,true);
        mRequest01.add("page",pager);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<CollectGoodsBean>(baseContext,true,CollectGoodsBean.class) {
                    @Override
                    public void doWork(CollectGoodsBean result, String code) {
                        try{
                            List<CollectGoodsBean.CollectGoodsItemBean> beans = result.getData().getData();
                            if (isRefresh){
                                datas.clear();
                            }
                            if (beans!=null && beans.size()>0){
                                datas.addAll(beans);
                                smart.setNoMoreData(false);
                            }else{
                                smart.setNoMoreData(true);
                            }

                            adapter.notifyDataSetChanged();
                            checkEmpty();
                            pager++;

                        }catch (Exception e){

                        }
                    }

                    @Override
                    public void onFinally(JSONObject obj, String code, boolean isNetSucceed) {
                        super.onFinally(obj, code, isNetSucceed);
                        smart.finishLoadMore(true);
                        smart.finishRefresh(true);
                    }
                },false,showLoading);

    }

    /*清空店铺收藏*/
    private void clearData(){
        mRequest02 = getRequest(Params.clearCollectGoods,true);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest02,
                new CustomHttpListener<ActionBean>(baseContext,true,ActionBean.class) {
                    @Override
                    public void doWork(ActionBean result, String code) {
                        try{
                            datas.clear();
                            adapter.notifyDataSetChanged();
                            checkEmpty();
                        }catch (Exception e){

                        }
                    }
                },false,true);

    }

    /*取消收藏单个店铺*/
    private void deleteSingle(final int index){
        mRequest03 = getRequest(Params.deleteCollectGoods,true);
        mRequest03.add("id",datas.get(index).getColledtid());
        CallServer.getRequestInstance().add(baseContext, 0, mRequest03,
                new CustomHttpListener<ActionBean>(baseContext,true,ActionBean.class) {
                    @Override
                    public void doWork(ActionBean result, String code) {
                        showtoa(result.getMsg());
                        datas.remove(index);
                        if (index!= RecyclerView.NO_POSITION){
                            adapter.notifyItemRemoved(index);
                            adapter.notifyItemRangeChanged(index, datas.size());
                        }else{
                            adapter.notifyDataSetChanged();
                        }
                        checkEmpty();
                    }
                },false,true);

    }



}
