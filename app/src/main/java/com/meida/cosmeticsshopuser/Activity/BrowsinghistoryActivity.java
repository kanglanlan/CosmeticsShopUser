package com.meida.cosmeticsshopuser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.ActionBean;
import com.meida.cosmeticsshopuser.Bean.BorrowingHistoryBean;
import com.meida.cosmeticsshopuser.Bean.CollectGoodsBean;
import com.meida.cosmeticsshopuser.Bean.CollectShopBean;
import com.meida.cosmeticsshopuser.Bean.FindListBean;
import com.meida.cosmeticsshopuser.Bean.NewsItemBean;
import com.meida.cosmeticsshopuser.MyView.dialog.ActionDialog;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.BrowsingdongtaiAdapter;
import com.meida.cosmeticsshopuser.adapter.BrowsingzixunAdapter;
import com.meida.cosmeticsshopuser.adapter.CollectGoodsAdapter;
import com.meida.cosmeticsshopuser.adapter.CollectionshopAdapter;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.CommonUtil;
import com.meida.cosmeticsshopuser.utils.TabLayoutUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BrowsinghistoryActivity extends BaseActivity {
    @Bind(R.id.smart)
    SmartRefreshLayout smart;
    @Bind(R.id.tv_title_right)
    TextView tvTitleRight;
    @Bind(R.id.tablayout_mo)
    TabLayout tablayoutMo;
    @Bind(R.id.recycle_list)
    RecyclerView recycleLisst;
    @Bind(R.id.empty_hint)
    TextView emptyHint;
    @Bind(R.id.empty_view)
    LinearLayout emptyView;
    CollectGoodsAdapter adapter1;
    CollectionshopAdapter adapter2;
    BrowsingzixunAdapter adapter3;
    BrowsingdongtaiAdapter adapter4;
    ArrayList<CollectGoodsBean.CollectGoodsItemBean> data1 = new ArrayList<>();
    ArrayList<CollectShopBean.CollectShop> data2 = new ArrayList<>();
    ArrayList<NewsItemBean> data3 = new ArrayList<>();
    ArrayList<FindListBean.FindItemBean> data4 = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browsinghistory);
        ButterKnife.bind(this);
        changeTitle("浏览记录","清空");
        //tvTitleRight.setText("清空");
        init();
        loadData(true,true,1);
    }

    private void init() {
        tablayoutMo.addTab(tablayoutMo.newTab().setText("商品"));
        tablayoutMo.addTab(tablayoutMo.newTab().setText("店铺"));
        tablayoutMo.addTab(tablayoutMo.newTab().setText("资讯"));
        tablayoutMo.addTab(tablayoutMo.newTab().setText("动态"));
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        TabLayoutUtil.optimisedTabLayout(tablayoutMo,screenWidth/tablayoutMo.getTabCount());
        tablayoutMo.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        recycleLisst.setAdapter(adapter1);
                        if (adapter1.getItemCount()>0){

                        }else{
                            loadData(true,true,1);
                        }
                        break;
                    case 1:
                        recycleLisst.setAdapter(adapter2);
                        if (adapter2.getItemCount()>0){

                        }else{
                            loadData(true,true,2);
                        }

                        break;
                    case 2:
                        recycleLisst.setAdapter(adapter3);
                        if (adapter3.getItemCount()>0){

                        }else{
                            loadData(true,true,3);
                        }

                        break;
                    case 3:
                        recycleLisst.setAdapter(adapter4);
                        if (adapter4.getItemCount()>0){

                        }else{
                            loadData(true,true,4);
                        }

                        break;
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
        CommonUtil.setIndicator(tablayoutMo, 7, 7);
        linearLayoutManager = new LinearLayoutManager(baseContext);
        recycleLisst.setLayoutManager(linearLayoutManager);
        adapter1 = new CollectGoodsAdapter(baseContext, R.layout.item_swipe_collect_goods, data1);
        adapter2 = new CollectionshopAdapter(baseContext, R.layout.item_liulandianpu, data2);
        adapter3 = new BrowsingzixunAdapter(baseContext, R.layout.item_pingtaimessage_scroll, data3);
        adapter4 = new BrowsingdongtaiAdapter(baseContext, data4);
        recycleLisst.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();

        adapter1.setListener(new CollectGoodsAdapter.OnItemListener() {
            @Override
            public void onDelete(ViewHolder holder, CollectGoodsBean.CollectGoodsItemBean bean) {
                deleteSingleRecord(holder.getAdapterPosition(),bean.getLookid(),1);
            }

            @Override
            public void onClickItem(int position, CollectGoodsBean.CollectGoodsItemBean bean) {
                Intent intent = new Intent(baseContext,ProductDetailActivity.class);
                intent.putExtra("id",bean.getId());
                startActivity(intent);
            }
        });

        adapter2.setListener(new CollectionshopAdapter.OnItemListener() {
            @Override
            public void onDelete(ViewHolder holder, CollectShopBean.CollectShop bean) {
                deleteSingleRecord(holder.getAdapterPosition(),bean.getLookid(),2);
            }

            @Override
            public void onClickItem(int position, CollectShopBean.CollectShop bean) {
                Intent intent = new Intent(baseContext,StoresActivity.class);
                intent.putExtra("id",bean.getId());
                startActivity(intent);
            }
        });

        adapter3.setListener(new BrowsingzixunAdapter.OnItemListener() {
            @Override
            public void onDelete(ViewHolder holder, NewsItemBean bean) {
                deleteSingleRecord(holder.getAdapterPosition(),bean.getLookid(),3);
            }

            @Override
            public void onClickItem(int position, NewsItemBean bean) {
                Intent intent = new Intent(baseContext, MessageDetailActivity.class);
                intent.putExtra("id",bean.getId());
                intent.putExtra("ip", Params.getNewsDetail);
                startActivity(intent);
            }
        });

        adapter4.setListener(new BrowsingdongtaiAdapter.OnItemListener() {
            @Override
            public void onDelete(BrowsingdongtaiAdapter.ViewHolder holder, FindListBean.FindItemBean bean) {
                deleteSingleRecord(holder.getAdapterPosition(),bean.getLookid(),4);
            }

            @Override
            public void onClickItem(int position, FindListBean.FindItemBean bean) {
                Intent intent = new Intent(baseContext, FindInfoActivity.class);
                intent.putExtra("id",bean.getId());
                startActivity(intent);
            }
        });

        smart.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadData(false,false,tablayoutMo.getSelectedTabPosition()+1);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData(true,false,tablayoutMo.getSelectedTabPosition()+1);
            }
        });

    }


    @OnClick(R.id.tv_title_right)
    public void onViewClicked() {
        ActionDialog deleteDialog = new ActionDialog(baseContext,"确认删除？");
        final int index = tablayoutMo.getSelectedTabPosition()+1;
        switch (index){
            case 1:
                deleteDialog = new ActionDialog(baseContext,"确认清空商品浏览记录？");
                break;
            case 2:
                deleteDialog = new ActionDialog(baseContext,"确认清空店铺浏览记录？");
                break;
            case 3:
                deleteDialog = new ActionDialog(baseContext,"确认清空资讯浏览记录？");
                break;
            case 4:
                deleteDialog = new ActionDialog(baseContext,"确认清空动态浏览记录？");
                break;
        }
        deleteDialog.show();
        deleteDialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
                clearHistory(index);
            }
        });

    }

    /*获取用户浏览记录*/
    private int pageIndex1 = 1,pageIndex2 = 1,pageIndex3 = 1,pageIndex4 = 1;
    /*类别 1 商品 2 店铺 3 资讯 4 动态*/
    private void loadData(final boolean isRefresh, boolean showLoading, final int tabIndex){
        mRequest01 = getRequest(Params.getBorrowingHistory, true);
        if (isRefresh){
            switch (tabIndex){
                case 1:
                    pageIndex1 = 1;
                    break;
                case 2:
                    pageIndex2 = 1;
                    break;
                case 3:
                    pageIndex3 = 1;
                    break;
                case 4:
                    pageIndex4 = 1;
                    break;
            }
        }

        switch (tabIndex){
            case 1:
                mRequest01.add("page", pageIndex1);
                break;
            case 2:
                mRequest01.add("page", pageIndex2);
                break;
            case 3:
                mRequest01.add("page", pageIndex3);
                break;
            case 4:
                mRequest01.add("page", pageIndex4);
                break;
            default:
                mRequest01.add("page", 1);
                break;
        }

        if (tabIndex==-1){
            mRequest01.add("object_type", "");
        }else{
            mRequest01.add("object_type", tabIndex);
        }
        //mRequest01.add("object_type", tablayoutMo.getSelectedTabPosition() + 1);
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

        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<BorrowingHistoryBean>
                        (baseContext, true, BorrowingHistoryBean.class) {
                    @Override
                    public void doWork(BorrowingHistoryBean result, String code) {
                        try{


                            List<CollectGoodsBean.CollectGoodsItemBean> goodsdata = new ArrayList<>();
                            List<CollectShopBean.CollectShop> shopdata = new ArrayList<>();
                            List<NewsItemBean> newsdata = new ArrayList<>();
                            List<FindListBean.FindItemBean> dynamicdata = new ArrayList<>();

                            if (result.getData().getGoodsdata()!=null){
                                goodsdata.addAll(result.getData().getGoodsdata().getData());
                            }

                            if (result.getData().getShopdata()!=null){
                                shopdata.addAll(result.getData().getShopdata().getData());
                            }

                            if (result.getData().getNewsdata()!=null){
                                newsdata.addAll(result.getData().getNewsdata().getData());
                            }

                            if (result.getData().getDynamicdata()!=null){
                                dynamicdata.addAll(result.getData().getDynamicdata().getData());
                            }


                            if (isRefresh){
                                switch (tabIndex){
                                    case 1:
                                        data1.clear();
                                        break;
                                    case 2:
                                        data2.clear();
                                        break;
                                    case 3:
                                        data3.clear();
                                        break;
                                    case 4:
                                        data4.clear();
                                        break;
                                }
                            }


                            if (goodsdata.size()>0){
                                data1.addAll(goodsdata);
                                adapter1.notifyDataSetChanged();
                            }

                            if (shopdata.size()>0){
                                data2.addAll(shopdata);
                                adapter2.notifyDataSetChanged();
                            }

                            if (newsdata.size()>0){
                                data3.addAll(newsdata);
                                adapter3.notifyDataSetChanged();
                            }

                            if (dynamicdata.size()>0){
                                data4.addAll(dynamicdata);
                                adapter4.notifyDataSetChanged();
                            }

                            switch (tabIndex){
                                case 1:
                                    pageIndex1++;
                                    break;
                                case 2:
                                    pageIndex2++;
                                    break;
                                case 3:
                                    pageIndex3++;
                                    break;
                                case 4:
                                    pageIndex4++;
                                    break;
                                default:
                                    pageIndex1++;
                                    pageIndex2++;
                                    pageIndex3++;
                                    pageIndex4++;
                                    break;
                            }


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
                }, false, showLoading);

    }

    /*清空某项全部浏览记录*/
    private void clearHistory(final int tabIndex){
        mRequest02 = getRequest(Params.clearBrowseHistory,true);
        mRequest02.add("object_type",tabIndex);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest02,
                new CustomHttpListener<ActionBean>(baseContext,true,ActionBean.class) {
                    @Override
                    public void doWork(ActionBean result, String code) {
                        switch (tabIndex){
                            case 1:
                                data1.clear();
                                adapter1.notifyDataSetChanged();
                                break;
                            case 2:
                                data2.clear();
                                adapter2.notifyDataSetChanged();
                                break;
                            case 3:
                                data3.clear();
                                adapter3.notifyDataSetChanged();
                                break;
                            case 4:
                                data4.clear();
                                adapter4.notifyDataSetChanged();
                                break;
                        }
                    }
                },false,true);
    }

    /*TODO 删除单项浏览记录 删除失败  */
    private void deleteSingleRecord(final int position,String id, final int tabIndex){
        mRequest03 = getRequest(Params.deleteBrowseHistory,true);
        mRequest03.add("idstr",id);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest03,
                new CustomHttpListener<ActionBean>(baseContext,true,ActionBean.class) {
                    @Override
                    public void doWork(ActionBean result, String code) {
                        showtoa(result.getMsg());
                        switch (tabIndex){
                            case 1:
                                data1.remove(position);
                                if (position!= RecyclerView.NO_POSITION){
                                    adapter1.notifyItemRemoved(position);
                                    adapter1.notifyItemRangeChanged(position, data1.size());
                                }else{
                                    adapter1.notifyDataSetChanged();
                                }
                                break;
                            case 2:
                                data2.remove(position);
                                if (position!= RecyclerView.NO_POSITION){
                                    adapter2.notifyItemRemoved(position);
                                    adapter2.notifyItemRangeChanged(position, data2.size());
                                }else{
                                    adapter2.notifyDataSetChanged();
                                }
                                break;
                            case 3:
                                data3.remove(position);
                                if (position!= RecyclerView.NO_POSITION){
                                    adapter3.notifyItemRemoved(position);
                                    adapter3.notifyItemRangeChanged(position, data3.size());
                                }else{
                                    adapter3.notifyDataSetChanged();
                                }
                                break;
                            case 4:
                                data4.remove(position);
                                if (position!= RecyclerView.NO_POSITION){
                                    adapter4.notifyItemRemoved(position);
                                    adapter4.notifyItemRangeChanged(position, data4.size());
                                }else{
                                    adapter4.notifyDataSetChanged();
                                }
                                break;
                        }
                    }
                },false,true);
    }


    private void checkEmpty(){
        int itemCount = 0 ;
        switch (tablayoutMo.getSelectedTabPosition()){
            case 0:
                itemCount = adapter1.getItemCount();
                break;
            case 1:
                itemCount = adapter2.getItemCount();
                break;
            case 2:
                itemCount = adapter3.getItemCount();
                break;
            case 3:
                itemCount = adapter4.getItemCount();
                break;
        }
        if (itemCount>0){
            emptyView.setVisibility(View.GONE);
            recycleLisst.setVisibility(View.VISIBLE);
        }else{
            emptyView.setVisibility(View.VISIBLE);
            recycleLisst.setVisibility(View.GONE);
        }
    }



}
