package com.meida.cosmeticsshopuser.Activity;

import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.ActionBean;
import com.meida.cosmeticsshopuser.Bean.OrderBean;
import com.meida.cosmeticsshopuser.Bean.eventbus.PaySuccess;
import com.meida.cosmeticsshopuser.MyView.dialog.ActionDialog;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.OrderAdapter;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.CommonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

public class MyOrderActivity extends BaseActivity {

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
    OrderAdapter adapter;
    private ArrayList<OrderBean.OrderItemBean> data = new ArrayList<>();

    /*配送方式弹窗*/
    @Bind(R.id.topView)
    View topView;
    private PopupWindow deliveryPop;
    private RadioGroup deliveryGroup;
    private RadioButton radio1,radio2,radio3;

    private int selectIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        selectIndex = getIntent().getIntExtra("pos",0);
        changeTitle("我的订单");
        tvTitleRight.setText("配送方式");
        tvTitleRight.setVisibility(View.VISIBLE);
        init();
        getData(true,selectIndex,true);
    }

    private void init() {
        String[] tabTitle = new String[]{"全部","待付款","待发货","待收货","待评价"};
        for (int i = 0; i<tabTitle.length; i++){
            TabLayout.Tab tab = tablayoutMo.newTab().setText(tabTitle[i]).setTag(i);
            /*if (selectIndex==i){
                tab.select();
            }else{

            }*/
            tablayoutMo.addTab(tab);
        }
        if (tablayoutMo.getTabAt(selectIndex) != null) {
            tablayoutMo.getTabAt(selectIndex).select();
        }
        tablayoutMo.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getData(true,(int)tab.getTag(),true);
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
        adapter = new OrderAdapter(baseContext, R.layout.item_orders, data);
        recycleLisst.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        initDeliveryPop();

        smart.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getData(false,tablayoutMo.getSelectedTabPosition(),false);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData(true,tablayoutMo.getSelectedTabPosition(),false);
            }
        });

        adapter.setListener(new OrderAdapter.OnActionListener() {
            @Override
            public void onAcition(int actionType, int position, OrderBean.OrderItemBean bean) {
                orderAction(actionType,bean.getId());
            }

            @Override
            public void onContactShop(int position, OrderBean.OrderItemBean bean) {
                RongIM.getInstance().refreshUserInfoCache(new UserInfo(bean.getKfid() + "",
                        bean.getTitle(), Uri.parse(bean.getAvatar())));
                RongIM.getInstance().startConversation(baseContext, Conversation.ConversationType.PRIVATE,
                        bean.getKfid(), bean.getTitle());
            }
        });

    }

    @OnClick(R.id.tv_title_right)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_title_right:
                safeShowDeliveryPop();
                break;
        }
    }

    private void initDeliveryPop(){
        deliveryPop = new PopupWindow(baseContext);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_order_type,null);
        deliveryGroup = dialogView.findViewById(R.id.radioGroup);
        radio1 = dialogView.findViewById(R.id.r1);
        radio2 = dialogView.findViewById(R.id.r2);
        radio3 = dialogView.findViewById(R.id.r3);
        deliveryGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                safeCloseDeliveryPop();
                if (radio1.isChecked()){
                    tvTitleRight.setText("同城配送");
                }else if (radio2.isChecked()){
                    tvTitleRight.setText("快递配送");
                }else if (radio3.isChecked()){
                    tvTitleRight.setText("商家自送");
                }else{
                    tvTitleRight.setText("全部");
                }
                getData(true,tablayoutMo.getSelectedTabPosition(),false);
            }
        });
        dialogView.findViewById(R.id.dismissView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                safeCloseDeliveryPop();
            }
        });
        deliveryPop.setContentView(dialogView);
        deliveryPop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        deliveryPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        deliveryPop.setBackgroundDrawable(new ColorDrawable(0x0000));
        deliveryPop.setFocusable(true);
        deliveryPop.setTouchable(true);
        deliveryPop.setOutsideTouchable(true);


    }

    private void safeShowDeliveryPop(){
        if (deliveryPop!=null && (!deliveryPop.isShowing()) ){
            deliveryPop.showAsDropDown(topView);
        }
    }

    private void safeCloseDeliveryPop(){
        if (deliveryPop!=null && (deliveryPop.isShowing()) ){
            deliveryPop.dismiss();
        }
    }


    /*获取订单列表信息*/
    private void getData(final boolean isRefresh, int status,boolean showLoading){
        if (isRefresh){
            pager = 1;
        }
        mRequest01 = getRequest(Params.getOrderList,true);
        mRequest01.add("page",pager);
        if (status<1){/*1:待支付 2:待发货 3:待收货 4:已收货 5:已评价   为空 全部*/
            mRequest01.add("status","");
        }else{
            mRequest01.add("status",status);
        }
        if (radio1.isChecked()){
            mRequest01.add("distribution","1");/*同城*/
        }else if (radio2.isChecked()){
            mRequest01.add("distribution","3");/*快递*/
        }else if (radio3.isChecked()){
            mRequest01.add("distribution","2");/*自送*/
        }else{
            mRequest01.add("distribution","");
        }


        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<OrderBean>(baseContext,true,OrderBean.class) {
                    @Override
                    public void doWork(OrderBean result, String code) {
                        try{
                            List<OrderBean.OrderItemBean> beans = result.getData().getData();
                            if (pager==1){
                                data.clear();
                            }
                            if (beans!=null && beans.size()>0){
                                data.addAll(beans);
                            }
                            adapter.notifyDataSetChanged();
                            pager++;
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

    /* 1取消订单  2发货提醒  3确认收货 4 删除订单 */
    private void orderAction(int actionType,final String id){

        String ipPath = "";
        String dialogTipStr = "";
        switch (actionType){
            case 1:
                ipPath = Params.cancelOrder;
                dialogTipStr = "确定取消订单？";
                break;
            case 2:
                ipPath = Params.remindSeller;
                dialogTipStr = "确定发送发货提醒？";
                break;
            case 3:
                ipPath = Params.confirmOrder;
                dialogTipStr = "确认收货？";
                break;
            case 4:
                ipPath = Params.deleteOrder;
                dialogTipStr = "删除订单？";
                break;
        }
        final String ipPath2Str = ipPath;
        ActionDialog dialog = new ActionDialog(baseContext,dialogTipStr);
        dialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
                mRequest02 = getRequest(ipPath2Str,true);
                mRequest02.add("id",id);
                CallServer.getRequestInstance().add(baseContext, 0, mRequest02,
                        new CustomHttpListener<ActionBean>(baseContext,true,ActionBean.class) {
                            @Override
                            public void doWork(ActionBean result, String code) {
                                showtoa(result.getMsg());
                                getData(true,tablayoutMo.getSelectedTabPosition(),false);
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
    @Override
    public void onDestroy() {
        super.onDestroy();

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(PaySuccess refresh){
        if (refresh.isB() || refresh.isEvalSuccess()){
            getData(true,tablayoutMo.getSelectedTabPosition(),false);
        }
    }




}
