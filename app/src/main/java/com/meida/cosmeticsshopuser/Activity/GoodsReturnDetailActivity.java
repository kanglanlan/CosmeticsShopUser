package com.meida.cosmeticsshopuser.Activity;

import android.content.Intent;
import android.os.Trace;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.BaseBean;
import com.meida.cosmeticsshopuser.Bean.OrderBean;
import com.meida.cosmeticsshopuser.Bean.ReturnHisDetail;
import com.meida.cosmeticsshopuser.Bean.eventbus.ReturnHistoryRefresh;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.meida.cosmeticsshopuser.utils.GlideUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;

public class GoodsReturnDetailActivity extends BaseActivity {

    private String id;

    private View topView;
    private TextView backMoney,tip;
    private TextView shopName,orderStatus;
    private ImageView img;
    private TextView name,money,num;
    private TextView reason,problemDes,returnNum,applyTime;
    private Button delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_return_detail);
        changeTitle("退货详情");
        id = getIntent().getStringExtra("id");
        initView();
        initEvent();
        initData();
    }

    private void initView(){
        topView = findViewById(R.id.topView);
        backMoney = (TextView) findViewById(R.id.backMoney);
        tip = (TextView) findViewById(R.id.tip);
        shopName = (TextView) findViewById(R.id.shopName);
        orderStatus = (TextView) findViewById(R.id.orderStatus);
        img = (ImageView) findViewById(R.id.img);
        name = (TextView) findViewById(R.id.name);
        money = (TextView) findViewById(R.id.money);
        num = (TextView) findViewById(R.id.num);
        reason = (TextView) findViewById(R.id.reason);
        problemDes = (TextView) findViewById(R.id.problemDes);
        returnNum = (TextView) findViewById(R.id.returnNum);
        applyTime = (TextView) findViewById(R.id.applyTime);
        delete = (Button) findViewById(R.id.delete);
    }

    private void initEvent(){
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });
    }

    private void initData(){
        getDetail();
    }

    private void getDetail(){

        mRequest01 = getRequest(Params.getReturnHisDetail,true);
        mRequest01.add("id",id);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<ReturnHisDetail>(baseContext,true,ReturnHisDetail.class) {
                    @Override
                    public void doWork(final ReturnHisDetail result, String code) {
                        try{
                            shopName.setText(result.getData().getShoptitle());
                            String status = result.getData().getStatus();
                            /*1待审核 2确定 3拒绝*/
                            if ("1".equals(status)){
                                orderStatus.setText("退货中");
                                topView.setVisibility(View.GONE);
                            }else if ("2".equals(status)){
                                orderStatus.setText("退货完成");
                                delete.setVisibility(View.VISIBLE);
                                topView.setVisibility(View.VISIBLE);
                                /*double backMoneyValue = FormatterUtil.StringToDouble(result.getData().getPrice())*(
                                        FormatterUtil.StringToInt(result.getData().getNum(),1)
                                        );
                                DecimalFormat df = new DecimalFormat("##0.00");*/
                                backMoney.setText(String.format("%s%s", Params.RMB, result.getData().getTotal()));
                            }else if ("3".equals(status)){
                                orderStatus.setText("");
                                delete.setVisibility(View.VISIBLE);
                                topView.setVisibility(View.GONE);
                            }
                            GlideUtils.loadGoods2(result.getData().getImgs(),img);
                            name.setText(result.getData().getTitle());
                            money.setText(String.format("%s%s", Params.RMB, result.getData().getPrice()));
                            num.setText(String.format("x%s", result.getData().getNum()));
                            reason.setText(result.getData().getReason());
                            problemDes.setText(result.getData().getReasonmm());
                            returnNum.setText(result.getData().getOrderno());
                            applyTime.setText(result.getData().getCreatetime());

                            shopName.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(baseContext, StoresActivity.class);
                                    intent.putExtra("id",result.getData().getShopid());
                                    startActivity(intent);
                                }
                            });

                           /* findViewById(R.id.goodsView).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(baseContext, ProductDetailActivity.class);
                                    intent.putExtra("id",result.getData().getGoodsid());
                                    startActivity(intent);
                                }
                            });*/

                        }catch (Exception e){

                        }
                    }
                },false,true);

    }

    private void delete(){
        mRequest03 = getRequest(Params.deleteReturnHis,true);
        mRequest03.add("id",id);
        mRequest03.add("user_type","2");
        CallServer.getRequestInstance().add(baseContext, 0, mRequest03,
                new CustomHttpListener<BaseBean>(baseContext,true,BaseBean.class) {
                    @Override
                    public void doWork(BaseBean result, String code) {
                        showtoa(result.getMsg());
                        EventBus.getDefault().post(new ReturnHistoryRefresh());
                        finish();
                    }
                },false,true);

    }


}
