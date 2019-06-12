package com.meida.cosmeticsshopuser.Activity;

import android.content.Intent;
import android.os.BaseBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.BaseBean;
import com.meida.cosmeticsshopuser.Bean.OrderReturnAbleDetail;
import com.meida.cosmeticsshopuser.Bean.ReasonBean;
import com.meida.cosmeticsshopuser.Bean.eventbus.ReturnHistoryRefresh;
import com.meida.cosmeticsshopuser.MyView.dialog.HeadPopuWindow;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.viewpager.RadioAdapter;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.meida.cosmeticsshopuser.utils.ProjectUtils;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

public class ApplyReturnActivity extends BaseActivity {

    private String id;

    private TextView shopName;
    private ImageView img;
    private TextView name,money,num;
    private View toClient;
    private View reasonView;
    private TextView reasonTv;
    private EditText editText;
    private Button submit;

    /*退货原因弹窗*/
    private HeadPopuWindow reasonPop;
    private RadioAdapter reasonAdapter;
    private List<String> reasonStrData = new ArrayList<>();
    private List<ReasonBean.Reason> reasonData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_return);
        changeTitle("申请退货");
        id = getIntent().getStringExtra("id");
        initView();
        initEvent();
        initData();

    }

    private void initView(){
        shopName = (TextView) findViewById(R.id.shopName);
        img = (ImageView) findViewById(R.id.img);
        name = (TextView) findViewById(R.id.name);
        money = (TextView) findViewById(R.id.money);
        num = (TextView) findViewById(R.id.num);
        toClient = findViewById(R.id.toClient);
        reasonView =  findViewById(R.id.reasonView);
        reasonTv = (TextView) findViewById(R.id.reasonTv);
        editText = (EditText) findViewById(R.id.editText);
        submit = (Button) findViewById(R.id.submit);
        initReasonPop();
    }

    private void initReasonPop(){
        View dialogView = LayoutInflater.from(baseContext).inflate(R.layout.dialog_goods_type_pop,null);
        TextView dialogTitle = dialogView.findViewById(R.id.dialogTitle);
        dialogTitle.setText("退货原因");
        dialogView.findViewById(R.id.dialogClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reasonPop.dismiss();
            }
        });
        RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(baseContext);
        recyclerView.setLayoutManager(linearLayoutManager);
        reasonAdapter = new RadioAdapter(baseContext,R.layout.item_radio_type,reasonStrData);
        recyclerView.setAdapter(reasonAdapter);
        reasonPop = new HeadPopuWindow(baseContext,dialogView);
        reasonAdapter.setListener(new RadioAdapter.OnCheckChangeListener() {
            @Override
            public void onCheckChange(int position) {
                reasonPop.dismiss();
            }
        });
        ProjectUtils.setRecyclerMaxHeight(recyclerView, 180);
    }

    private void initEvent(){
        reasonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reasonPop.show();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyRequest();
            }
        });
       reasonAdapter.setListener(new RadioAdapter.OnCheckChangeListener() {
           @Override
           public void onCheckChange(int position) {
               reasonTv.setText(reasonStrData.get(position));
               reasonPop.dismiss();
           }
       });
    }

    private void initData(){
        getDetail();
        getReason();
    }

    /*获取退货原因*/
    private void getReason(){
        mRequest01 = getRequest(Params.returnReason,true);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<ReasonBean>(baseContext,true,ReasonBean.class) {
                    @Override
                    public void doWork(ReasonBean result, String code) {
                        try{
                            List<ReasonBean.Reason> beans = result.getData();
                            if (beans!=null && beans.size()>0){
                                reasonData.clear();
                                reasonStrData.clear();
                                for (int i = 0; i<beans.size(); i++){
                                    reasonStrData.add(beans.get(i).getTitle());
                                }
                                reasonAdapter.notifyDataSetChanged();
                            }
                        }catch (Exception e){

                        }
                    }
                },false,false);
    }

    /*获取详情*/
    private void getDetail(){
        mRequest02 = getRequest(Params.getReturnAbleOrderDetail,true);
        mRequest02.add("id",id);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest02,
                new CustomHttpListener<OrderReturnAbleDetail>(baseContext,true,OrderReturnAbleDetail.class) {
                    @Override
                    public void doWork(final OrderReturnAbleDetail result, String code) {
                        try{
                            shopName.setText(result.getData().getShoptitle());
                            GlideUtils.loadGoods2(result.getData().getImgs(),img);
                            name.setText(result.getData().getTitle());
                            money.setText(result.getData().getPrice());
                            num.setText(String.format("x%s", result.getData().getNum()));
                            toClient.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    RongIM.getInstance().startConversation(baseContext, Conversation.ConversationType.PRIVATE,
                                            result.getData().getKfid(), result.getData().getShoptitle());
                                }
                            });

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

    /*申请退货*/
    private void applyRequest(){
        final String reason = reasonTv.getText().toString().trim();
        String problemDes = editText.getText().toString().trim();
        if (TextUtils.isEmpty(reason)){
            showtoa("请选择退货原因");
            return;
        }
        if (TextUtils.isEmpty(problemDes)){
            showtoa("请输入问题描述");
            return;
        }
        mRequest03 = getRequest(Params.applyReturn,true);
        mRequest03.add("id",id);
        mRequest03.add("reason",reason);
        mRequest03.add("reasonmm",problemDes);
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
