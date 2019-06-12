package com.meida.cosmeticsshopuser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;

import com.meida.cosmeticsshopuser.Bean.ActionBean;
import com.meida.cosmeticsshopuser.Bean.GoodsItemBean;
import com.meida.cosmeticsshopuser.Bean.ShopCartBean;
import com.meida.cosmeticsshopuser.Bean.ShopCartRefresh;
import com.meida.cosmeticsshopuser.MyView.NestedExpandableListView;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.ProductAdapter;
import com.meida.cosmeticsshopuser.adapter.ShopCartAdapter;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShopCartActivity extends BaseActivity {


    private NestedExpandableListView listView;
    private ShopCartAdapter shopCartAdapter;
    private ArrayList<ShopCartBean.Shop> data = new ArrayList<>();
    private RecyclerView recommendRecycler;
    private List<GoodsItemBean> recommendData = new ArrayList<>();
    private ProductAdapter productAdapter;
    private View emptyView;

    private View middleView;
    private View bottomView,delete;
    private CheckedTextView checkAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_shop_cart);
        changeTitle("购物车","编辑");
        initView();
        initEvent();
        getData(true);
    }


    private void initView(){

        //initTestData();

        recommendRecycler = (RecyclerView) findViewById(R.id.recommendRecycler);
        productAdapter = new ProductAdapter(baseContext,R.layout.item_shangpin,recommendData);
        recommendRecycler.setAdapter(productAdapter);
        GridLayoutManager lm = new GridLayoutManager(baseContext,2,GridLayoutManager.VERTICAL,false);
        recommendRecycler.setLayoutManager(lm);
        recommendRecycler.setNestedScrollingEnabled(false);

        emptyView = findViewById(R.id.empty_view);
        listView = (NestedExpandableListView) findViewById(R.id.listView);
        shopCartAdapter = new ShopCartAdapter(baseContext,data);
        listView.setAdapter(shopCartAdapter);
        listView.setGroupIndicator(null);
        listView.setGroupIndicator(null);
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        openExpandList();

        middleView = findViewById(R.id.middleView);
        bottomView = findViewById(R.id.bottomView);
        checkAll = (CheckedTextView) findViewById(R.id.checkAll);
        delete = findViewById(R.id.delete);

        /*R.layout.item_shop_header*/
        /*R.layout.item_cart_product*/



    }

    private void initEvent(){
        tv_right_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = tv_right_title.getText().toString().trim();
                if ("编辑".equals(s)){
                    shopCartAdapter.setEditMode(true);
                    tv_right_title.setText("完成");
                    bottomView.setVisibility(View.VISIBLE);
                    recommendRecycler.setVisibility(View.GONE);
                    middleView.setVisibility(View.GONE);
                }else if ("完成".equals(s)){
                    shopCartAdapter.setEditMode(false);
                    tv_right_title.setText("编辑");
                    bottomView.setVisibility(View.GONE);
                    recommendRecycler.setVisibility(View.VISIBLE);
                    middleView.setVisibility(View.VISIBLE);
                }
            }
        });

        checkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAll.isChecked()){
                    checkAll.setChecked(false);
                    shopCartAdapter.checkAll(false);
                }else{
                    checkAll.setChecked(true);
                    shopCartAdapter.checkAll(true);
                }
            }
        });

        shopCartAdapter.setListener(new ShopCartAdapter.OnShopCartDataChangeListener() {
            @Override
            public void onDataChange(double total_price) {

            }

            @Override
            public void onDataAllSelect(boolean is_all_selet) {
                checkAll.setChecked(is_all_selet);
            }

            @Override
            public void onItemAdd(int position, int item_position, ShopCartBean.CartGoods bean,int num) {
                modifyNum(position,item_position,bean.getId(),num);
            }

            @Override
            public void onItemReduce(int position, int item_position, ShopCartBean.CartGoods bean,int num) {
                modifyNum(position,item_position,bean.getId(),num);
            }

            @Override
            public void onGoodsClick(ShopCartBean.CartGoods bean) {
                Intent intent = new Intent(baseContext, ProductDetailActivity.class);
                intent.putExtra("id", bean.getGoodsid());
                startActivity(intent);
            }

            @Override
            public void onShopClick(ShopCartBean.Shop shopBean) {
                Intent intent = new Intent(baseContext,StoresActivity.class);
                intent.putExtra("id",shopBean.getId());
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteGoods();
            }
        });

    }

    private void openExpandList() {
        for (int i = 0; i < shopCartAdapter.getGroupCount(); i++) {
            listView.expandGroup(i);
        }
    }


    private void getData(final boolean getRecommend){
        mRequest01 = getRequest(Params.getShopCartData,true);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<ShopCartBean>(baseContext,true,ShopCartBean.class) {
                    @Override
                    public void doWork(ShopCartBean result, String code) {
                        try{
                            List<ShopCartBean.Shop> shopCartData = result.getData().getGoods();
                            List<GoodsItemBean> goodsdata = result.getData().getRecommendGoods().getData();

                            data.clear();

                            if (shopCartData!=null && shopCartData.size()>0){
                                data.addAll(shopCartData);
                            }

                            if (getRecommend){
                                recommendData.clear();
                                if (goodsdata!=null && goodsdata.size()>0){
                                    recommendData.addAll(goodsdata);
                                }
                            }

                            shopCartAdapter.notifyDataSetChanged();
                            productAdapter.notifyDataSetChanged();
                            openExpandList();

                        }catch (Exception e){

                        }

                    }

                    @Override
                    public void onFinally(JSONObject obj, String code, boolean isNetSucceed) {
                        super.onFinally(obj, code, isNetSucceed);
                        checkEmpty();
                    }
                },false,true);
    }


    /*修改商品数量*/
    private void modifyNum(final int groupPosition, final int childPosition, String cartid, final int num){
        mRequest02 = getRequest(Params.modifyShopCartNum,true);
        mRequest02.add("id",cartid);
        mRequest02.add("num",num);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest02,
                new CustomHttpListener<ActionBean>(baseContext,true,ActionBean.class) {
                    @Override
                    public void doWork(ActionBean result, String code) {
                        data.get(groupPosition).getGoodslist().get(childPosition).setNum(num);
                        shopCartAdapter.updateShopPrice(groupPosition);
                        shopCartAdapter.notifyDataSetChanged();
                    }
                },false,false);
    }


    /*购物车删除商品*/
    private void deleteGoods(){
        String ids = shopCartAdapter.getSelectIds();
        if (TextUtils.isEmpty(ids)){
            return;
        }
        mRequest03 = getRequest(Params.deleteShopCart,true);
        mRequest03.add("id",ids);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest03,
                new CustomHttpListener<ActionBean>(baseContext,true,ActionBean.class) {
                    @Override
                    public void doWork(ActionBean result, String code) {
                        showtoa(result.getMsg());
                        getData(false);
                    }
                },false,true);
    }


    private void checkEmpty(){

        if (shopCartAdapter.getGroupCount()>0){
            emptyView.setVisibility(View.GONE);
            tv_right_title.setVisibility(View.VISIBLE);
        }else{
            emptyView.setVisibility(View.VISIBLE);
            tv_right_title.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(ShopCartRefresh refresh){
        getData(false);
    }

}
