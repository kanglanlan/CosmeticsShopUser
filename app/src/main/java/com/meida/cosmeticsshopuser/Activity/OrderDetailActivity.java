package com.meida.cosmeticsshopuser.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.ActionBean;
import com.meida.cosmeticsshopuser.Bean.BaseBean;
import com.meida.cosmeticsshopuser.Bean.OrderDetailBean;
import com.meida.cosmeticsshopuser.Bean.ShopCartBean;
import com.meida.cosmeticsshopuser.Bean.eventbus.PaySuccess;
import com.meida.cosmeticsshopuser.MyView.dialog.ActionDialog;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.GridInImgAdapter;
import com.meida.cosmeticsshopuser.adapter.GridInImgAdapter2;
import com.meida.cosmeticsshopuser.adapter.viewpager.OrderProductAdapter;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.ProjectUtils;
import com.meida.cosmeticsshopuser.utils.SpanUtil;
import com.meida.cosmeticsshopuser.utils.TypeUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderDetailActivity extends BaseActivity {

    private String id = "";

    private TextView namePhone,address;
    private TextView shopName;
    private RecyclerView recyclerView;
    private ArrayList<ShopCartBean.CartGoods> productData = new ArrayList<>();
    private OrderProductAdapter adapter;
    private TextView freight,couponFree,summery,note;
    private TextView rearchTime,deliveryMode,courier;
    private ImageView call,toMap;
    private TextView orderNum,submitTime,orderStatus,payMode;
    private View clip;
    private Button action;
    private ActionDialog callDialog;

    private TextView sendOutView;
    private RecyclerView deliveryRecycler;
    private GridInImgAdapter2 deliveryAdapter;
    private List<String> deliveryList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_order_detail);
        changeTitle("订单详情","");
        id = getIntent().getStringExtra("id");
        initView();
        initEvent();
        initData();

    }

    private void initView(){
        namePhone = (TextView) findViewById(R.id.namePhone);
        address = (TextView) findViewById(R.id.address);
        shopName = (TextView) findViewById(R.id.shopName);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(baseContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(lm);
        adapter = new OrderProductAdapter(baseContext,R.layout.item_order_product,productData);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        freight = (TextView) findViewById(R.id.freight);
        couponFree = (TextView) findViewById(R.id.couponFree);
        summery = (TextView) findViewById(R.id.summery);
        note = (TextView) findViewById(R.id.note);
        rearchTime = (TextView) findViewById(R.id.rearchTime);
        deliveryMode = (TextView) findViewById(R.id.deliveryMode);
        courier = (TextView) findViewById(R.id.courier);
        call = (ImageView) findViewById(R.id.call);
        toMap = (ImageView) findViewById(R.id.toMap);
        orderNum = (TextView) findViewById(R.id.orderNum);
        clip = findViewById(R.id.clip);
        submitTime = (TextView) findViewById(R.id.submitTime);
        orderStatus = (TextView) findViewById(R.id.orderStatus);
        payMode = (TextView) findViewById(R.id.payMode);
        action = (Button) findViewById(R.id.action);
        /*发货照片*/
        sendOutView = (TextView)findViewById(R.id.sendOutView);
        deliveryRecycler = (RecyclerView) findViewById(R.id.deliveryRecycler);
        GridLayoutManager glm = new GridLayoutManager(baseContext,3);
        glm.setOrientation(GridLayoutManager.VERTICAL);
        deliveryRecycler.setLayoutManager(glm);
        deliveryAdapter = new GridInImgAdapter2(baseContext,R.layout.item_img,deliveryList);
        deliveryRecycler.setAdapter(deliveryAdapter);
        deliveryRecycler.setNestedScrollingEnabled(false);


    }

    private void initEvent(){
        clip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProjectUtils.clipStr(detailBean.getNumber(),"订单号已复制到剪贴板");
            }
        });
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
                            int index = builder1.toString().trim().length()-2;
                            builder1.append(detailBean.getTotal());
                            SpanUtil spanUtil = new SpanUtil(baseContext);
                            spanUtil.setColorSpan(Color.parseColor("#333333"),builder1.toString().trim(),0,index,summery);
                            //summery.setText(builder1.toString());

                            /*买家留言*/
                            String memoStr = detailBean.getMemo();
                            note.setText(memoStr);

                            /*TODO 送达时间*/

                            if (detailBean.getSendgoods()!=null){
                                String distribution = detailBean.getSendgoods().getDistribution();
                                deliveryMode.setText(TypeUtil.getDeliveryModeStr(distribution));

                                String sendUsername = detailBean.getSendgoods().getContacts();
                                final String sendUserphone = detailBean.getSendgoods().getPhone();
                                courier.setText(String.format("%s   %s", sendUsername, sendUserphone));
                                toMap.setVisibility(View.GONE);
                                call.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (callDialog==null){
                                            callDialog = new ActionDialog(baseContext,sendUserphone);
                                        }
                                        callDialog.show();
                                    }
                                });
                                findViewById(R.id.deliveryView).setVisibility(View.VISIBLE);
                            }else{
                                findViewById(R.id.deliveryView).setVisibility(View.GONE);
                            }


                            /*订单号*/
                            String orderNumStr = detailBean.getNumber();
                            orderNum.setText(orderNumStr);

                            /*下单时间*/
                            String createTimeStr = detailBean.getCreate_time();
                            submitTime.setText(createTimeStr);

                            /*订单状态*/
                            String orderStatusStr = detailBean.getStatus();
                            orderStatus.setText(TypeUtil.getOrderStatusStr(orderStatusStr));

                            /*付款方式*/
                            String payModeStr = detailBean.getPay_type();
                            payMode.setText(TypeUtil.getPayModeStr(payModeStr));

                            /*TODO 发货照片*/
                            if (detailBean.getSendgoods()!=null && (!TextUtils.isEmpty(detailBean.getSendgoods().getImgs())) ){
                                String imgsStr = detailBean.getSendgoods().getImgs();
                                if (!TextUtils.isEmpty(imgsStr)){
                                    String[] imgArr = imgsStr.split(",");
                                    final List<String> imgList = Arrays.asList(imgArr);
                                    if (imgList!=null && imgList.size()>0){
                                        deliveryList.addAll(imgList);
                                        deliveryAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                            if (deliveryAdapter.getItemCount()>0){
                                sendOutView.setText("发货照片");
                            }else{
                                sendOutView.setText("暂无发货照片");
                            }


                            TypeUtil.OrderType orderType = TypeUtil.getOrderType(detailBean.getStatus());
                            if (orderType==null){
                                action.setVisibility(View.VISIBLE);
                            }else{
                                switch (orderType){
                                    case DZF:
                                        action.setVisibility(View.GONE);
                                        break;
                                    case DFH:
                                        action.setVisibility(View.GONE);
                                        break;
                                    case DSH:
                                        action.setVisibility(View.VISIBLE);
                                        action.setText("确认收货");
                                        action.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                cancelOrder(3,id);
                                            }
                                        });
                                        break;
                                    case YSH:
                                        action.setVisibility(View.VISIBLE);
                                        action.setText("去评价");
                                        action.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(baseContext, WritCommentActivity.class)
                                                        .putExtra("id",id)
                                                        /*TODO 商品评分  detailBean.getGoods()*/
                                                        .putExtra("star",getIntent().getStringExtra("star"))
                                                        .putExtra("img",getIntent().getStringExtra("shopImg")
                                                        );

                                                startActivity(intent);
                                            }
                                        });
                                        break;
                                    case YPJ:
                                        action.setVisibility(View.VISIBLE);
                                        action.setText("删除订单");
                                        action.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                cancelOrder(4,id);
                                            }
                                        });
                                        break;
                                    case GB:
                                        action.setVisibility(View.VISIBLE);
                                        action.setText("删除订单");
                                        action.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                cancelOrder(4,id);
                                            }
                                        });
                                        break;
                                    case SC:
                                        action.setVisibility(View.VISIBLE);
                                        action.setText("删除订单");
                                        action.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                cancelOrder(4,id);
                                            }
                                        });
                                        break;
                                }
                            }

                        }catch (Exception e){
                            e.printStackTrace();
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

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(PaySuccess refresh){
        if (refresh.isB()||refresh.isEvalSuccess()){
            finish();
        }
    }


}
