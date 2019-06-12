package com.meida.cosmeticsshopuser.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.BaseBundle;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.meida.cosmeticsshopuser.Bean.BaseBean;
import com.meida.cosmeticsshopuser.Bean.PayData;
import com.meida.cosmeticsshopuser.Bean.eventbus.PaySuccess;
import com.meida.cosmeticsshopuser.Bean.eventbus.UserInfoRefresh;
import com.meida.cosmeticsshopuser.MyView.dialog.ActionDialog;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.alipay.PayResult;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.media.Base;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

public class PayModeSelectActivity extends BaseActivity {

    private String orderId = "";
    private int payType = 1;/*1 支付宝 2 微信*/

    private TextView timeDown, orderPrice, shopName;
    private LinearLayout alipayView, wxView;
    private CheckBox alipayCb, wxCb;
    private TextView submit;

    /*倒计时结束弹窗框*/
    private ActionDialog timeOutDialog;
    /*未超时退出弹窗框*/
    private ActionDialog backTipDialog;

    /*时间倒计时 30分钟*/
    private static final int TIME_DOWN = 0x10;
    private static final int TIME_OUT = 0x11;
    private int timeTotal = 60*30;/*60*30*/

    @SuppressLint("HandlerLeak")
    private Handler hanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TIME_DOWN:
                    timeTotal = timeTotal - 1;
                    if (timeTotal > -1) {
                        int h = timeTotal / 60;
                        int min = timeTotal - 60 * h;
                        String hStr = h < 10 ? "0" + h : h + "";
                        String minStr = min < 10 ? "0" + min : "" + min;
                        StringBuilder builder = new StringBuilder();
                        builder.append(hStr);
                        builder.append(":");
                        builder.append(minStr);
                        timeDown.setText(builder.toString());
                        hanlder.sendEmptyMessageDelayed(TIME_DOWN, 1000);
                    } else {
                        hanlder.sendEmptyMessageDelayed(TIME_OUT, 1000);
                    }
                    break;
                case TIME_OUT:
                    timeDown.setText("00:00");
                    timeOutDialog.show();
                    break;

                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Intent intent = new Intent(baseContext,PayOkActivity.class);
                        intent.putExtra("orderid",orderId);
                        startActivity(intent);
                        showtoa("支付成功");
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showtoa("支付失败");
                    }
                    break;
                }
            }
        }
    };


    /*微信支付*/
    private IWXAPI msgApi;
    private static final int SDK_PAY_FLAG = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_mode_select);
        changeTitle("订单支付");
        orderId = getIntent().getStringExtra("id");
        Params.orderid = orderId;
        msgApi = WXAPIFactory.createWXAPI(baseContext, null);
        msgApi.registerApp(Params.WX_APP_ID);
        EventBus.getDefault().register(this);

        initView();
        initEvent();
        initData();
    }

    private void initView() {
        timeDown = (TextView) findViewById(R.id.timeDown);
        orderPrice = (TextView) findViewById(R.id.orderPrice);
        shopName = (TextView) findViewById(R.id.shopName);
        alipayView = (LinearLayout) findViewById(R.id.alipayView);
        alipayCb = (CheckBox) findViewById(R.id.alipayCb);
        wxView = (LinearLayout) findViewById(R.id.wxView);
        wxCb = (CheckBox) findViewById(R.id.wxCb);
        submit = (TextView) findViewById(R.id.submit);

        orderPrice.setText(Params.RMB_+FormatterUtil.sortPrice(getIntent().getStringExtra("orderPrice")));
        shopName.setText(getIntent().getStringExtra("shopName"));


        alipayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payType = 1;
                alipayCb.setChecked(true);
                wxCb.setChecked(false);
            }
        });

        wxView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payType = 2;
                wxCb.setChecked(true);
                alipayCb.setChecked(false);
            }
        });

        timeOutDialog = new ActionDialog(baseContext, "已超时，请重新购买", 2);
        timeOutDialog.setOnlyConfirmListeer(new ActionDialog.OnOnlyConfirmListeer() {
            @Override
            public void onOnlyConfirm() {
                finish();
            }
        });

        backTipDialog = new ActionDialog(baseContext, "确定放弃购买吗？", "放弃", "再想想");
        backTipDialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });
        //hanlder.sendEmptyMessageDelayed(TIME_DOWN, 1000);

    }

    private void initEvent() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPayData();
            }
        });
    }

    private void initData() {

    }

    @Override
    public void onBackPressed() {
        if (timeTotal > 0) {
            backTipDialog.show();
        } else {
            super.onBackPressed();
        }

    }


    private void getPayData(){
        mRequest01 = getRequest(Params.payOrder,true);
        mRequest01.add("id",orderId);
        mRequest01.add("pay_type",payType);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<PayData>(baseContext,true,PayData.class) {
                    @Override
                    public void doWork(PayData result, String code) {
                        try{
                            if (payType==1){
                                startAlipay(result.getData().getAlipay());
                            }else{
                                startWxpay(result.getData().getWechat());
                            }
                        }catch (Exception e){

                        }
                    }
                },false,true);
    }


    private void startAlipay(final String orderInfo){
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(baseContext);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                hanlder.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    private void startWxpay(PayData.WeChatData data){
        PayReq req = new PayReq();
        req.appId = data.getAppid();
        req.partnerId		= data.getPartnerid();
        req.prepayId		= data.getPrepayid();
        req.nonceStr		= data.getNoncestr();
        req.timeStamp		= data.getTimestamp();
        req.packageValue	= data.getPackageStr();
        req.sign			= data.getSign();
        //req.extData			= "app data"; // optional
        //Toast.makeText(JiHuoActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        msgApi.sendReq(req);
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
            hanlder.removeMessages(TIME_DOWN);
            hanlder.removeMessages(TIME_OUT);
            finish();
        }
    }





}
