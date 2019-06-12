package com.meida.cosmeticsshopuser.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.ActionBean;
import com.meida.cosmeticsshopuser.Bean.BaseBean;
import com.meida.cosmeticsshopuser.Bean.CouponBean;
import com.meida.cosmeticsshopuser.Bean.OrderDetailBean;
import com.meida.cosmeticsshopuser.Bean.ShopCartBean;
import com.meida.cosmeticsshopuser.Bean.eventbus.PaySuccess;
import com.meida.cosmeticsshopuser.MyView.dialog.ActionDialog;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.viewpager.OrderProductAdapter;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.meida.cosmeticsshopuser.utils.PreferencesUtils;
import com.meida.cosmeticsshopuser.utils.ProjectUtils;
import com.meida.cosmeticsshopuser.utils.TypeUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderInfoActivity extends BaseActivity {

    private static final int PICK_GUARD = 0x12;

    private String id = "";

    private TextView namePhone,address;
    private TextView shopName;
    private RecyclerView recyclerView;
    private ArrayList<ShopCartBean.CartGoods> productData = new ArrayList<>();
    private OrderProductAdapter adapter;
    private TextView freight,couponFree,summery;
    private TextView note;
    private TextView orderNum,clip,submitTime,orderStatus,protect;
    private CheckBox checkBox;
    private TextView orderPrice,payNow;

    private Dialog orderProtectDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);
        changeTitle("订单详情","取消订单");
        id = getIntent().getStringExtra("id");
        EventBus.getDefault().register(this);
        initView();
        initEvent();
        initData();

    }

    private void initView(){
        namePhone = (TextView) findViewById(R.id.namePhone);
        address = (TextView) findViewById(R.id.address);
        shopName = (TextView) findViewById(R.id.shopName);
        recyclerView = (android.support.v7.widget.RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(baseContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(lm);
        adapter = new OrderProductAdapter(baseContext,R.layout.item_order_product,productData);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        freight = (TextView) findViewById(R.id.freight);
        couponFree = (TextView) findViewById(R.id.couponFree);
        summery = (TextView) findViewById(R.id.summery);
        note = (TextView) findViewById(R.id.note);
        orderNum = (TextView) findViewById(R.id.orderNum);
        clip = (TextView) findViewById(R.id.clip);
        submitTime = (TextView) findViewById(R.id.submitTime);
        orderStatus = (TextView) findViewById(R.id.orderStatus);
        protect = (TextView) findViewById(R.id.protect);
        checkBox = (CheckBox) findViewById(R.id.checkbox);
        orderPrice = (TextView) findViewById(R.id.orderPrice);
        payNow = (TextView) findViewById(R.id.payNow);

        View orderProtectDialogView = getLayoutInflater().inflate(R.layout.dialog_order_protect,null);
        TextView dialogTip = orderProtectDialogView.findViewById(R.id.tip);
        dialogTip.setText(PreferencesUtils.getString(baseContext,Params.PROTECT_TXT));
        /*我有练过，不怕*/
        orderProtectDialogView.findViewById(R.id.option1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderProtectDialog.dismiss();
                orderProtect(false);
            }
        });
        /*我没练过，我怕*/
        orderProtectDialogView.findViewById(R.id.option2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderProtectDialog.dismiss();
                pickGuard();
            }
        });
        /*我无人能敌，永不提示*/
        orderProtectDialogView.findViewById(R.id.option3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferencesUtils.putBoolean(baseContext,Params.KEY_IGNORE_ORDER_PROTECT,true);
                orderProtectDialog.dismiss();
                orderProtect(false);
            }
        });
        orderProtectDialog = new Dialog(baseContext);
        orderProtectDialog.setContentView(orderProtectDialogView);
        orderProtectDialog.setCancelable(false);
        orderProtectDialog.setCanceledOnTouchOutside(false);
        orderProtectDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

    }

    private void initEvent(){
        protect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(baseContext, WebViewActivity.class).putExtra("id","9"));
            }
        });
        tv_right_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelOrder(1,id);
            }
        });
        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent = new Intent(baseContext,PayModeSelectActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);*/
               payNow.setEnabled(false);
                orderProtect(true);
            }
        });
        clip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProjectUtils.clipStr(detailBean.getNumber(),"订单号已复制到剪贴板");
            }
        });
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()){
                    pickGuard();
                    checkBox.setChecked(false);
                }else{}
            }
        });
    }

    private String linkMan1Str = "",linkMan2Str = "",linkPhone1Str = "",linkPhone2Str = "";
    private void pickGuard(){
        Intent intent = new Intent(baseContext,ContactActivity.class);
        startActivityForResult(intent,PICK_GUARD);
    }

    private void initData(){
        getDetail();
    }

    OrderDetailBean.DataBean detailBean;
    private void getDetail(){
        mRequest01 = getRequest(Params.getOrderDetail,true);
        mRequest01.add("id",id);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<OrderDetailBean>(baseContext,true,OrderDetailBean.class) {
                    @Override
                    public void doWork(OrderDetailBean result, String code) {
                        try{

                           detailBean = result.getData();

                            /*地址信息*/
                            String addrNameStr = detailBean.getReceiver();
                            String addrPhoneStr = detailBean.getPhone();
                            String addressStr = detailBean.getAddress();
                            String addrDetailStr = detailBean.getHousenumber();
                            namePhone.setText(String.format("%s   %s", addrNameStr, addrPhoneStr));
                            address.setText(String.format("%s %s", addressStr, addrDetailStr));

                            /*店铺名字*/
                            String shopNameStr = detailBean.getTitle();
                           /* String shopId = detailBean.getShopid();*/
                            shopName.setText(shopNameStr);

                            /*商品*/
                            List<ShopCartBean.CartGoods> goodsItems = detailBean.getItem();
                            productData.clear();
                            productData.addAll(goodsItems);
                            adapter.notifyDataSetChanged();

                            /*配送费*/
                            String freightStr = detailBean.getFreight();
                            freight.setText(String.format("%s%s", Params.RMB_, freightStr));

                            /*优惠券*/
                            String couponFreeStr = detailBean.getDiscountamount();
                            couponFree.setText(String.format("- %s%s", Params.RMB_, couponFreeStr));

                            /*共1见商品  实付款 */
                            StringBuilder builder1 = new StringBuilder();
                            builder1.append("共");
                            builder1.append(detailBean.getGoodsnum());
                            builder1.append("件商品  实付款 ¥ ");
                            builder1.append(detailBean.getTotal());
                            summery.setText(builder1.toString());


                            /*买家留言*/
                            String memoStr = detailBean.getMemo();
                            note.setText(memoStr);

                            /*订单号*/
                            String orderNumStr = detailBean.getNumber();
                            orderNum.setText(orderNumStr);

                            /*下单时间*/
                            String createTimeStr = detailBean.getCreate_time();
                            submitTime.setText(createTimeStr);

                            /*订单状态*/
                            String orderStatusStr = detailBean.getStatus();
                            orderStatus.setText(TypeUtil.getOrderStatusStr(orderStatusStr));

                            /*防护功能  TODO 是否开启防护 1开启 2未开启*/
                            String openGuard = detailBean.getOpenfh();
                            if ("1".equals(openGuard)){
                                checkBox.setChecked(true);
                            }else{
                                checkBox.setChecked(false);
                            }

                            /*实付金额*/
                            String orderPriceStr = detailBean.getTotal();
                            orderPrice.setText(String.format("%s%s", Params.RMB_, orderPriceStr));



                        }catch (Exception e){

                        }
                    }
                },false,true);
    }

    /*取消订单  发货提醒  确认收货*/
    private void cancelOrder(int actionType,final String id){

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
                        new CustomHttpListener<BaseBean>(baseContext,true,BaseBean.class) {
                            @Override
                            public void doWork(BaseBean result, String code) {
                                showtoa(result.getMsg());
                                PaySuccess refresh = new PaySuccess();
                                refresh.setEvalSuccess(true);
                                EventBus.getDefault().post(refresh);
                                finish();
                            }
                        },false,true);
            }
        });
        dialog.show();
    }

    /*订单加防护*/
    private boolean changeProduct = false;
    private void orderProtect(boolean checkProduct){

        boolean ignoreForever = PreferencesUtils.getBoolean
                (baseContext,Params.KEY_IGNORE_ORDER_PROTECT,false);
        if (!ignoreForever){/*没有永久忽略*/
            /*需要检测 且  没有选择防护*/
            if (checkProduct && (!checkBox.isChecked())){
                orderProtectDialog.show();
                return;
            }
        }

        if (changeProduct && checkBox.isChecked()){
            mRequest03 = getRequest(Params.userOrderProtection,true);
            mRequest03.add("id",id);
            mRequest03.add("linkman1",linkMan1Str);
            mRequest03.add("phone1",linkPhone1Str);
            mRequest03.add("linkman2",linkMan2Str);
            mRequest03.add("phone2",linkPhone2Str);

            CallServer.getRequestInstance().add(baseContext, 0, mRequest03,
                    new CustomHttpListener<BaseBean>
                            (baseContext,true,BaseBean.class) {
                        @Override
                        public void doWork(BaseBean result, String code) {

                            Intent intent = new Intent(baseContext,PayModeSelectActivity.class);
                            intent.putExtra("id",id);
                            intent.putExtra("shopName",detailBean.getTitle());
                            intent.putExtra("orderPrice",detailBean.getTotal());
                            startActivity(intent);

                        }

                        @Override
                        public void onFinally(JSONObject obj, String code, boolean isNetSucceed) {
                            super.onFinally(obj, code, isNetSucceed);
                            payNow.setEnabled(true);
                        }
                    },false,false);
        }else{
            Intent intent = new Intent(baseContext,PayModeSelectActivity.class);
            intent.putExtra("id",id);
            intent.putExtra("shopName",detailBean.getTitle());
            intent.putExtra("orderPrice",detailBean.getTotal());
            startActivity(intent);
            payNow.setEnabled(true);
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intentResult) {
        super.onActivityResult(requestCode, resultCode, intentResult);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case PICK_GUARD:
                    try{
                        linkMan1Str = intentResult.getStringExtra("name1");
                        linkMan2Str = intentResult.getStringExtra("name2");
                        linkPhone1Str = intentResult.getStringExtra("phone1");
                        linkPhone2Str = intentResult.getStringExtra("phone2");
                        checkBox.setChecked(true);
                        changeProduct = true;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
            }
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
        if (refresh.isB()){
            finish();
        }
    }




}
