package com.meida.cosmeticsshopuser.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.ActionBean;
import com.meida.cosmeticsshopuser.Bean.AddrBean;
import com.meida.cosmeticsshopuser.Bean.BaseBean;
import com.meida.cosmeticsshopuser.Bean.CouponBean;
import com.meida.cosmeticsshopuser.Bean.FreightBean;
import com.meida.cosmeticsshopuser.Bean.ShopCartBean;
import com.meida.cosmeticsshopuser.Bean.ShopCartRefresh;
import com.meida.cosmeticsshopuser.Bean.eventbus.EditAddrBean;
import com.meida.cosmeticsshopuser.MyView.dialog.ActionDialog;
import com.meida.cosmeticsshopuser.MyView.dialog.CommentEditDialog;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.viewpager.OrderProductAdapter;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.meida.cosmeticsshopuser.utils.PreferencesUtils;
import com.yolanda.nohttp.rest.Request;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SubmitOrderActivity extends BaseActivity {

    /*1从购物车 提交的 订单  2  其它*/
    private String flag = "";

    private static final int PICK_COUPON = 0x11;
    private static final int PICK_GUARD = 0x12;
    private static final int PICK_ADDR = 0x13;

    private View addrView;
    private TextView addrEmptyView, namePhone, address;
    private RecyclerView recyclerView;
    private ArrayList<ShopCartBean.CartGoods> productData = new ArrayList<>();
    private OrderProductAdapter adapter;
    private TextView freight, couponFree, summery;
    private TextView note;
    private TextView protect;
    private CheckBox checkbox;
    private TextView orderPrice, submit;

    /*配送方式选择*/
    private int deliveryIndex = -1;
    private View deliveryOption;
    private View option1;
    private TextView optionName1, optionPrice1, optionTip1;
    private View option2;
    private TextView optionName2, optionPrice2, optionTip2;
    private View option3;
    private TextView optionName3, optionPrice3, optionTip3;
    private double freight1Value,freight2Value,freight3Value;

    private DecimalFormat df = new DecimalFormat("##0.00");
    private double goodsPriceValue = 0,orderPriceValue = 0,freightValue = 0;

    private ShopCartBean.Shop data;

    private CommentEditDialog noteDialog;

    private Dialog orderProtectDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_order);
        EventBus.getDefault().register(this);
        changeTitle("提交订单");
        if (!TextUtils.isEmpty(getIntent().getStringExtra("flag"))){
            flag = getIntent().getStringExtra("flag");
        }
        initView();
        initEvent();
        initData();
    }

    private void initView() {
        addrView = (LinearLayout) findViewById(R.id.addrView);
        addrEmptyView = (TextView) findViewById(R.id.addrEmptyView);
        namePhone = (TextView) findViewById(R.id.namePhone);
        address = (TextView) findViewById(R.id.address);
        recyclerView = (android.support.v7.widget.RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);
        adapter = new OrderProductAdapter(baseContext, R.layout.item_order_product, productData);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        freight = (TextView) findViewById(R.id.freight);
        couponFree = (TextView) findViewById(R.id.couponFree);
        summery = (TextView) findViewById(R.id.summery);
        note = (TextView) findViewById(R.id.note);
        protect = (TextView) findViewById(R.id.protect);
        checkbox = (CheckBox) findViewById(R.id.checkbox);
        orderPrice = (TextView) findViewById(R.id.orderPrice);
        submit = (TextView) findViewById(R.id.submit);
        optionDelivery();

        noteDialog = new CommentEditDialog(baseContext,R.layout.dialog_order_note,20);
        noteDialog.setDialogViewListener(new CommentEditDialog.DialogViewListener() {
            @Override
            public void onListSureClick(View view, String content) {

            }
        });
        noteDialog.setTextChangeListener(new CommentEditDialog.TextChangeListener() {
            @Override
            public void onTextChange(String text) {
                note.setText(text);
            }
        });
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteDialog.show();
            }
        });

        View orderProtectDialogView = getLayoutInflater().inflate(R.layout.dialog_order_protect,null);
        TextView dialogTip = orderProtectDialogView.findViewById(R.id.tip);
        dialogTip.setText(PreferencesUtils.getString(baseContext,Params.PROTECT_TXT));
        /*我有练过，不怕*/
        orderProtectDialogView.findViewById(R.id.option1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderProtectDialog.dismiss();
                submitOrder(false);
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
                submitOrder(false);
            }
        });
        orderProtectDialog = new Dialog(baseContext);
        orderProtectDialog.setContentView(orderProtectDialogView);
        orderProtectDialog.setCancelable(false);
        orderProtectDialog.setCanceledOnTouchOutside(false);
        orderProtectDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

    }


    private void initEvent() {
        addrView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(baseContext,MyAddActivity.class);
                intent.putExtra("pick",true);
                startActivityForResult(intent,PICK_ADDR);
            }
        });
        protect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(baseContext,WebViewActivity.class).putExtra("id","9"));
            }
        });
        couponFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(baseContext, PickCouponActivity.class);
                intent.putExtra("shopid",data.getId());
                intent.putExtra("goodsspec",submitData.toString());
                startActivityForResult(intent,PICK_COUPON);
            }
        });

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkbox.isChecked()){
                    pickGuard();
                    checkbox.setChecked(false);
                }else{

                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitOrder(true);
            }
        });

    }

    JSONArray submitData = new JSONArray();
    private void initData() {

        try{
            ShopCartBean.Shop intentData = (ShopCartBean.Shop)
                    getIntent().getSerializableExtra("data");
            if (intentData!=null && intentData.getGoodslist()!=null && intentData.getGoodslist()!=null){
                data = intentData;
                productData.clear();
                productData.addAll(intentData.getGoodslist());
                /* 同城  快递  自送 */
                int getFreigtNum1 = 0,getFreigtNum2 = 0,getFreigtNum3 = 0;
                submitData = new JSONArray();
                for (int i = 0; i<productData.size(); i++){
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("goodsspcid",productData.get(i).getGoodsspcid());
                    jsonObject1.put("num",productData.get(i).getNum());
                    if ("1".equals(productData.get(i).getIsfreeshipping())){/*快递包邮*/

                    }else{
                        getFreigtNum2 = getFreigtNum2 + productData.get(i).getNum();
                    }
                    if ("1".equals(productData.get(i).getIsfreesend())){/*包商家送*/

                    }else{
                        getFreigtNum3 = getFreigtNum3 + productData.get(i).getNum();

                    }
                    if ("1".equals(productData.get(i).getIsfreesend())){/*包同城配送*/

                    }else{
                        getFreigtNum1 = getFreigtNum1 + productData.get(i).getNum();
                    }
                    submitData.put(jsonObject1);

                    goodsPriceValue = goodsPriceValue+ productData.get(i).getPriceValue()*productData.get(i).getNum();
                }
                adapter.notifyDataSetChanged();
                getDefaultAddr();
                getFreight(data.getId(),getFreigtNum1,getFreigtNum2,getFreigtNum3);
                submit.setEnabled(true);
            }else{
                submit.setEnabled(false);
            }
        }catch (Exception e){

        }

    }

    private void optionDelivery(){
        /*配送方式选择*/
        deliveryOption = (LinearLayout) findViewById(R.id.deliveryOption);
        option1 = (LinearLayout) findViewById(R.id.option1);
        optionName1 = (TextView) findViewById(R.id.optionName1);
        optionPrice1 = (TextView) findViewById(R.id.optionPrice1);
        optionTip1 = (TextView) findViewById(R.id.optionTip1);
        option2 = (LinearLayout) findViewById(R.id.option2);
        optionName2 = (TextView) findViewById(R.id.optionName2);
        optionPrice2 = (TextView) findViewById(R.id.optionPrice2);
        optionTip2 = (TextView) findViewById(R.id.optionTip2);
        option3 = (LinearLayout) findViewById(R.id.option3);
        optionName3 = (TextView) findViewById(R.id.optionName3);
        optionPrice3 = (TextView) findViewById(R.id.optionPrice3);
        optionTip3 = (TextView) findViewById(R.id.optionTip3);

        option1.setEnabled(false);
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkOption1();
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkOption2();
            }

        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkOption3();
            }
        });

        option1.setVisibility(View.GONE);
        option2.setVisibility(View.GONE);
        option3.setVisibility(View.GONE);

    }

    private void checkOption1(){
        /*deliveryIndex = 1;*//*同城*//*
        optionName1.setTextColor(Color.parseColor("#333333"));
        optionPrice1.setTextColor(getResources().getColor(R.color.main));
        optionTip1.setTextColor(Color.parseColor("#333333"));
        optionName2.setTextColor(Color.parseColor("#666666"));
        optionPrice2.setTextColor(Color.parseColor("#666666"));
        optionTip2.setTextColor(Color.parseColor("#666666"));
        optionName3.setTextColor(Color.parseColor("#666666"));
        optionPrice3.setTextColor(Color.parseColor("#666666"));
        optionTip3.setTextColor(Color.parseColor("#666666"));

        freightValue = freight1Value;
        freight.setText(Params.RMB_+df.format(freightValue));
        orderPriceValue = goodsPriceValue + freightValue-couponFreeValue;
        orderPrice.setText(Params.RMB_+df.format(orderPriceValue));
        summery.setText(df.format(orderPriceValue));*/

    }

    private void checkOption2(){
        deliveryIndex = 3;/*快递*/
        optionName1.setTextColor(Color.parseColor("#666666"));
        optionPrice1.setTextColor(Color.parseColor("#666666"));
        optionTip1.setTextColor(Color.parseColor("#666666"));
        optionName2.setTextColor(Color.parseColor("#333333"));
        optionPrice2.setTextColor(getResources().getColor(R.color.main));
        optionTip2.setTextColor(Color.parseColor("#333333"));
        optionName3.setTextColor(Color.parseColor("#666666"));
        optionPrice3.setTextColor(Color.parseColor("#666666"));
        optionTip3.setTextColor(Color.parseColor("#666666"));

        freightValue = freight2Value;
        freight.setText(Params.RMB_+df.format(freightValue));
        orderPriceValue = goodsPriceValue + freightValue-couponFreeValue;
        orderPrice.setText(Params.RMB_+df.format(orderPriceValue));
        summery.setText(df.format(orderPriceValue));

    }

    private void checkOption3(){
        deliveryIndex = 2;/*自送*/
        optionName1.setTextColor(Color.parseColor("#666666"));
        optionPrice1.setTextColor(Color.parseColor("#666666"));
        optionTip1.setTextColor(Color.parseColor("#666666"));
        optionName2.setTextColor(Color.parseColor("#666666"));
        optionPrice2.setTextColor(Color.parseColor("#666666"));
        optionTip2.setTextColor(Color.parseColor("#666666"));
        optionName3.setTextColor(Color.parseColor("#333333"));
        optionPrice3.setTextColor(getResources().getColor(R.color.main));
        optionTip3.setTextColor(Color.parseColor("#333333"));

        freightValue = freight3Value;
        freight.setText(Params.RMB_+df.format(freightValue));
        orderPriceValue = goodsPriceValue + freightValue-couponFreeValue;
        orderPrice.setText(Params.RMB_+df.format(orderPriceValue));
        summery.setText(df.format(orderPriceValue));

    }

    private void hideAddrEmpty(){
        addrEmptyView.setVisibility(View.GONE);
        findViewById(R.id.line).setVisibility(View.VISIBLE);
        findViewById(R.id.colorLine).setVisibility(View.GONE);
        namePhone.setVisibility(View.VISIBLE);
        address.setVisibility(View.VISIBLE);
        deliveryOption.setVisibility(View.VISIBLE);
    }

    private void showAddrEmpty(){
        addrEmptyView.setVisibility(View.VISIBLE);
        findViewById(R.id.line).setVisibility(View.GONE);
        findViewById(R.id.colorLine).setVisibility(View.VISIBLE);
        namePhone.setVisibility(View.GONE);
        address.setVisibility(View.GONE);
        deliveryOption.setVisibility(View.VISIBLE);
    }

    /*获取运费*/
    private void getFreight(String shopId,int num1,int num2,int num3){
        mRequest01 = getRequest(Params.getOrderFreight,true);
        mRequest01.add("shopid",shopId);
        mRequest01.add("num1",num1);
        mRequest01.add("num2",num2);
        mRequest01.add("num3",num3);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<FreightBean>(baseContext,true,FreightBean.class) {
                    @Override
                    public void doWork(FreightBean result, String code) {
                        try{
                            List<FreightBean.Freight> data = result.getData();
                            for (int i = 0; i<data.size();i++){
                                if ("1".equals(data.get(i).getShow())){
                                    switch (data.get(i).getId()){
                                        case "1":/*同城*/
                                           /* FormatterUtil.formatPrice2(data.get(i).getRateprice(),optionPrice1);*/
                                            option1.setVisibility(View.VISIBLE);
                                            freight1Value = FormatterUtil.StringToDouble(data.get(i).getRateprice());
                                            break;
                                        case "2":/*快递*/
                                            FormatterUtil.formatPrice2(data.get(i).getRateprice(),optionPrice2);
                                            option2.setVisibility(View.VISIBLE);
                                            freight2Value = FormatterUtil.StringToDouble(data.get(i).getRateprice());
                                            break;
                                        case "3":/*自送*/
                                            FormatterUtil.formatPrice2(data.get(i).getRateprice(),optionPrice3);
                                            option3.setVisibility(View.VISIBLE);
                                            freight3Value = FormatterUtil.StringToDouble(data.get(i).getRateprice());
                                            break;
                                    }
                                }
                            }

                            /*if (option1.getVisibility()==View.VISIBLE){
                                *//*checkOption1();*//*
                            }else *//*if (option2.getVisibility()==View.VISIBLE){
                                checkOption2();
                            }else if (option3.getVisibility()==View.VISIBLE){
                                checkOption3();
                            }*/

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                },false,true);

    }

    private String addrId = "";
    /*获取默认地址*/
    private void getDefaultAddr(){
        mRequest02 = getRequest(Params.getAddrList,true);
        mRequest02.add("default","1");
        CallServer.getRequestInstance().add(baseContext, 0, mRequest02,
                new CustomHttpListener<AddrBean>(baseContext,true,AddrBean.class) {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void doWork(AddrBean result, String code) {
                        try{
                            List<AddrBean.AddrItemBean> beans = result.getData();
                            if (beans!=null && beans.size()>0){
                                AddrBean.AddrItemBean itemBean = result.getData().get(0);
                                addrId = itemBean.getId();
                                namePhone.setText(itemBean.getConsignee()+"   "+itemBean.getMobile());
                                address.setText(itemBean.getAddress()+""+itemBean.getDoornumber());
                                hideAddrEmpty();
                            }
                        }catch (Exception e){

                        }

                       /* if (TextUtils.isEmpty(addrId)){
                            showAddrEmpty();
                        }else{
                            hideAddrEmpty();
                        }
                        */
                    }
                },false,false);

    }

    private CouponBean.CouponItemBean couponItem;
    private double couponFreeValue = 0.0;


    private String linkMan1Str = "",linkMan2Str = "",linkPhone1Str = "",linkPhone2Str = "";
    private void pickGuard(){
        Intent intent = new Intent(baseContext,ContactActivity.class);
        startActivityForResult(intent,PICK_GUARD);
    }

    /*提交订单
    * 第一次提交调用  参数 是否检测订单防护是否开启*/
    private void submitOrder(boolean checkProduct){
        if (deliveryIndex<0){
            showtoa("请选择配送方式");
            return;
        }
        if (TextUtils.isEmpty(addrId)){
            showtoa("请选择收货地址");
            return;
        }

        boolean ignoreForever = PreferencesUtils.getBoolean
                (baseContext,Params.KEY_IGNORE_ORDER_PROTECT,false);
        if (!ignoreForever){/*没有永久忽略*/
            /*需要检测 且  没有选择防护*/
            if (checkProduct && (!checkbox.isChecked())){
                orderProtectDialog.show();
                return;
            }
        }

        mRequest03 = getRequest(Params.submitOrder,true);
        mRequest03.add("specs",submitData.toString());
        mRequest03.add("distribution",deliveryIndex);
        if (couponItem!=null){
            mRequest03.add("ucid",couponItem.getId());
        }else{
            mRequest03.add("ucid","");
        }
        mRequest03.add("addressid",addrId);
        mRequest03.add("linkman1",linkMan1Str);
        mRequest03.add("linkman2",linkMan2Str);
        mRequest03.add("phone1",linkPhone1Str);
        mRequest03.add("phone2",linkPhone2Str);/*TODO 防护功能开启回显*/
        if (checkbox.isChecked()){
            /*开启防护*/
            mRequest03.add("openfh","1");
        }else{
            /*未开启*/
            mRequest03.add("openfh","2");
        }
        mRequest03.add("memo",note.getText().toString().trim());
        mRequest03.add("flag",flag);

        CallServer.getRequestInstance().add(baseContext, 0, mRequest03,
                new CustomHttpListener<BaseBean>(baseContext,true,BaseBean.class) {
                    @Override
                    public void doWork(BaseBean result, String code) {
                        /* {"code":1,"msg":"提交订单成功!","data":{"id":"257","total":27,"shoptitle":"商家测试"}}*/

                    }

                    @Override
                    public void onFinally(final JSONObject obj, String code, boolean isNetSucceed) {
                        super.onFinally(obj, code, isNetSucceed);
                        try{
                            if ("1".equals(code)){
                                JSONObject object = obj.optJSONObject("data");
                                Intent intent = new Intent(baseContext,PayModeSelectActivity.class);
                                intent.putExtra("id",object.optString("id"));
                                intent.putExtra("shopName",object.optString("shoptitle"));
                                intent.putExtra("orderPrice",object.optString("total"));
                                startActivity(intent);
                                finish();
                                EventBus.getDefault().post(new ShopCartRefresh());

                            }else if ("999".equals(code)){
                                ActionDialog dialog = new ActionDialog(baseContext,"温馨提示",obj.optString("msg"),"切换店铺","继续购买");
                                dialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
                                    @Override
                                    public void onLeftClick() {
                                        JSONArray jsonArray = obj.optJSONArray("data");
                                        if (jsonArray.length()>0){
                                            try{
                                                String shopId = jsonArray.optJSONObject(0).getString("shopid");
                                                Intent intent = new Intent(baseContext,StoresActivity.class);
                                                intent.putExtra("id",shopId);
                                                startActivity(intent);
                                                finish();
                                            }catch (Exception e1){

                                            }
                                        }
                                    }

                                    @Override
                                    public void onRightClick() {
                                        submitOrder2();
                                    }
                                });
                                dialog.show();
                            }
                        }catch (Exception e){

                        }
                    }

                },false,true);

    }

    /*无障碍提交订单*/
    private void submitOrder2(){
        if (deliveryIndex<0){
            showtoa("请选择配送方式");
            return;
        }
        if (TextUtils.isEmpty(addrId)){
            showtoa("请选择收货地址");
            return;
        }
        mRequest04 = getRequest(Params.submitOrder2,true);
        mRequest04.add("specs",submitData.toString());
        mRequest04.add("distribution",deliveryIndex);
        if (couponItem!=null){
            mRequest04.add("ucid",couponItem.getId());
        }else{
            mRequest04.add("ucid","");
        }
        mRequest04.add("addressid",addrId);
        mRequest04.add("linkman1",linkMan1Str);
        mRequest04.add("linkman2",linkMan2Str);
        mRequest04.add("phone1",linkPhone1Str);
        mRequest04.add("phone2",linkPhone2Str);/*TODO 防护功能开启回显*/
        if (checkbox.isChecked()){
            mRequest04.add("openfh","1");
        }else{
            mRequest04.add("openfh","2");
        }
        mRequest04.add("memo",note.getText().toString().trim());
        mRequest04.add("flag",flag);

        CallServer.getRequestInstance().add(baseContext, 0, mRequest04,
                new CustomHttpListener<BaseBean>(baseContext,true,BaseBean.class) {
                    @Override
                    public void doWork(BaseBean result, String code) {
                        //showtoa(result.getMsg());
                        /* {"code":1,"msg":"提交订单成功!","data":{"id":"257","total":27,"shoptitle":"商家测试"}}*/
                    }

                    @Override
                    public void onFinally(JSONObject obj, String code, boolean isNetSucceed) {
                        super.onFinally(obj, code, isNetSucceed);

                        if ("1".equals(code)){
                            JSONObject object = obj.optJSONObject("data");
                            Intent intent = new Intent(baseContext,PayModeSelectActivity.class);
                            intent.putExtra("id",object.optString("id"));
                            intent.putExtra("shopName",object.optString("shoptitle"));
                            intent.putExtra("orderPrice",object.optString("total"));
                            startActivity(intent);
                            finish();
                            EventBus.getDefault().post(new ShopCartRefresh());
                        }
                    }

                },false,true);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intentResult) {
        super.onActivityResult(requestCode, resultCode, intentResult);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case PICK_COUPON:
                    try{
                        if ((CouponBean.CouponItemBean) intentResult.getSerializableExtra("data")!=null){
                            couponItem = (CouponBean.CouponItemBean) intentResult.getSerializableExtra("data");
                            couponFree.setText("- ¥ "+FormatterUtil.sortPrice(couponItem.getFavourable()));
                            couponFreeValue = FormatterUtil.StringToDouble(couponItem.getFavourable());
                            orderPriceValue = goodsPriceValue + freightValue-couponFreeValue;
                            orderPrice.setText(Params.RMB_+df.format(orderPriceValue));
                            summery.setText(df.format(orderPriceValue));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case PICK_GUARD:
                    try{
                        linkMan1Str = intentResult.getStringExtra("name1");
                        linkMan2Str = intentResult.getStringExtra("name2");
                        linkPhone1Str = intentResult.getStringExtra("phone1");
                        linkPhone2Str = intentResult.getStringExtra("phone2");
                        checkbox.setChecked(true);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;

                case PICK_ADDR:
                    try{
                        if (intentResult!=null){
                            AddrBean.AddrItemBean itemBean = (AddrBean.AddrItemBean)intentResult.getSerializableExtra("data");
                            if (itemBean!=null){
                                addrId = itemBean.getId();
                                namePhone.setText(itemBean.getConsignee()+"   "+itemBean.getMobile());
                                address.setText(String.format("%s%s", itemBean.getAddress(), itemBean.getDoornumber()));
                                hideAddrEmpty();
                            }
                        }
                    }catch (Exception e){

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
    public void onEventBus(EditAddrBean refresh){
        if (addrId.equals(refresh.getId())){
            if (refresh.getItemBean()!=null){
                namePhone.setText(String.format("%s   %s", refresh.getItemBean().getConsignee(), refresh.getItemBean().getMobile()));
                address.setText(String.format("%s%s", refresh.getItemBean().getAddress(), refresh.getItemBean().getDoornumber()));
            }else{
                addrId = "";
                showAddrEmpty();
                getDefaultAddr();
            }

        }
    }




}
