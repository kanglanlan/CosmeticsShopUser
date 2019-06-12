package com.meida.cosmeticsshopuser.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.BaseBean;
import com.meida.cosmeticsshopuser.Bean.CouponBean;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.CouponsAdapter;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.CommonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.meida.cosmeticsshopuser.share.Datas.alldatas;

/**
 * youhuiquan
 */
public class CouponsActivity extends BaseActivity {

    @Bind(R.id.smart)
    SmartRefreshLayout smart;
    @Bind(R.id.tablayout_mo)
    TabLayout tablayoutMo;
    @Bind(R.id.recycle_list)
    RecyclerView recycleLisst;
    @Bind(R.id.empty_hint)
    TextView emptyHint;
    @Bind(R.id.empty_view)
    LinearLayout emptyView;
    CouponsAdapter adapter;
    private ArrayList<CouponBean.CouponItemBean> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        changeTitle("我的优惠券");
        init();
        getData(true,1);
    }

    private void init() {
        tablayoutMo.addTab(tablayoutMo.newTab().setText("待使用").setTag(1));
        tablayoutMo.addTab(tablayoutMo.newTab().setText("已使用").setTag(2));
        tablayoutMo.addTab(tablayoutMo.newTab().setText("已失效").setTag(4));
        tablayoutMo.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getData(true,(int)tab.getTag());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        CommonUtil.setIndicator(tablayoutMo, 10, 10);
        linearLayoutManager = new LinearLayoutManager(baseContext);
        recycleLisst.setLayoutManager(linearLayoutManager);
        adapter = new CouponsAdapter(baseContext, R.layout.item_coupons, data);
        recycleLisst.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recycleLisst.setNestedScrollingEnabled(false);
        smart.setEnableLoadMore(false);
        smart.setEnableRefresh(true);
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                TabLayout.Tab tab = tablayoutMo.getTabAt(tablayoutMo.getSelectedTabPosition());
                getData(true,(int)tab.getTag());
            }
        });

    }


    /*状态 1可用 2已使用 4过期*/
    private void getData(boolean showLoading, final int status){
        mRequest01 = getRequest(Params.getMyCouponList,true);
        mRequest01.add("status",status);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<CouponBean>(baseContext,true,CouponBean.class) {
                    @Override
                    public void doWork(CouponBean result, String code) {
                        try{
                            List<CouponBean.CouponItemBean> beans = result.getData().getData();
                            data.clear();
                            if (beans!=null && beans.size()>0){
                                data.addAll(beans);
                            }
                            adapter.setTabIndex(status);
                            adapter.notifyDataSetChanged();

                            if (result.getData().getTj().getKsy()>99){
                                tablayoutMo.getTabAt(0).setText("待使用(99+)");
                            }else{
                                tablayoutMo.getTabAt(0).setText("待使用("+result.getData().getTj().getKsy()+")");
                            }

                            if (result.getData().getTj().getYsy()>99){
                                tablayoutMo.getTabAt(1).setText("已使用(99+)");
                            }else{
                                tablayoutMo.getTabAt(1).setText("已使用("+result.getData().getTj().getYsy()+")");
                            }

                            if (result.getData().getTj().getYgq()>99){
                                tablayoutMo.getTabAt(2).setText("已失效(99+)");
                            }else{
                                tablayoutMo.getTabAt(2).setText("已失效("+result.getData().getTj().getYgq()+")");
                            }


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
            recycleLisst.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }else{
            recycleLisst.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
    }


}
