package com.meida.cosmeticsshopuser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.ActionBean;
import com.meida.cosmeticsshopuser.Bean.GoodsItemBean;
import com.meida.cosmeticsshopuser.Bean.ShopDetailBean;
import com.meida.cosmeticsshopuser.MyView.dialog.ActionDialog;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.meida.cosmeticsshopuser.utils.ProjectUtils;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 店铺详情
 */
public class StoreInfoActivity extends BaseActivity {

    private String id = "";

    @Bind(R.id.shopBg)
    ImageView shopBg;
    @Bind(R.id.img_dianpu)
    ImageView imgDianpu;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_scnums)
    TextView tvScnums;
    @Bind(R.id.tv_sc)
    TextView tvSc;
    @Bind(R.id.ratbar1)
    ScaleRatingBar ratbar1;
    @Bind(R.id.ratbar2)
    ScaleRatingBar ratbar2;
    @Bind(R.id.ratbar3)
    ScaleRatingBar ratbar3;
    @Bind(R.id.ratbar4)
    ScaleRatingBar ratbar4;
    @Bind(R.id.tv_kefucall)
    TextView tvKefucall;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_add)
    TextView tvAdd;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_openTime)
    TextView tvOpenTime;
    @Bind(R.id.tv_signature)
    TextView tvSignature;

    /*三项保证*/
    @Bind(R.id.gm1)
    View gm1;
    @Bind(R.id.gm2)
    View gm2;
    @Bind(R.id.gm3)
    View gm3;
    @Bind(R.id.gt1)
    View gt1;
    @Bind(R.id.gt2)
    View gt2;
    @Bind(R.id.gt3)
    View gt3;

    private ActionDialog callDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_info);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        findViewById(R.id.baseBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getDetail();
    }

    @OnClick({R.id.tv_sc, R.id.ll_call, R.id.ll_zhengjian, R.id.bt_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sc:
                if (needLogin()){
                    return;
                }
                updateShopCollectState();
                break;
            case R.id.ll_call:
                if (callDialog!=null && (!callDialog.isShowing())){
                    callDialog.show();
                }
                break;
            case R.id.ll_zhengjian:
                Intent intent = new Intent(baseContext,SingleImgActivity.class);
                intent.putExtra("pic",yyzzImgStr);
                intent.putExtra("topTitle","企业证件");
                startActivity(intent);
                break;
            case R.id.bt_more:
                finish();
                break;
        }
    }

    String yyzzImgStr = "";
    private void getDetail(){

        mRequest01 = getRequest(Params.getShopDetail,true);
        mRequest01.add("id",id);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<ShopDetailBean>(baseContext,true,ShopDetailBean.class) {
                    @Override
                    public void doWork(ShopDetailBean data, String code) {
                        try{
                            ShopDetailBean.DataBean result = data.getData();
                            /*店铺logo*/
                            String logoStr = result.getAvatar();
                            GlideUtils.loadShop(logoStr, imgDianpu);
                            /*店铺背景图*/
                            String backBg = result.getBackground();
                            GlideUtils.loadShopBg(backBg, shopBg);
                            /*店铺名称*/
                            String name = result.getTitle();
                            tvTitle.setText(name);

                            /*是否收藏*/
                            String iscollection = result.getIscollection();
                            if ("1".equals(iscollection)){
                                isCollect =  false;
                                tvSc.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ico_img06,0,0);
                            }else{
                                isCollect = true;
                                tvSc.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ico_img51,0,0);
                            }

                            /*收藏人数*/
                            String collectNum = result.getCollection();
                            try{
                                collectNumValue = Long.parseLong(collectNum);
                            }catch (Exception e){

                            }
                            tvScnums.setText(FormatterUtil.formatNumber(collectNumValue)+"人收藏");

                            /*营业执照*/
                            final String g1Str = data.getData().getImg();
                            if (TextUtils.isEmpty(g1Str)){
                                gm1.setVisibility(View.GONE);
                                gt1.setVisibility(View.GONE);
                            }else{
                                gm1.setVisibility(View.VISIBLE);
                                gt1.setVisibility(View.VISIBLE);

                                gm1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(baseContext,SingleImgActivity.class);
                                        intent.putExtra("pic",g1Str);
                                        intent.putExtra("topTitle","营业执照");
                                        startActivity(intent);
                                    }
                                });

                                gt1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(baseContext,SingleImgActivity.class);
                                        intent.putExtra("pic",g1Str);
                                        intent.putExtra("topTitle","营业执照");
                                        startActivity(intent);
                                    }
                                });

                            }

                            /*保证金*/
                            String g2Str = data.getData().getIsalipay();
                            if (TextUtils.isEmpty(g2Str)){
                                gm2.setVisibility(View.GONE);
                                gt2.setVisibility(View.GONE);
                            }else{
                                gm2.setVisibility(View.VISIBLE);
                                gt2.setVisibility(View.VISIBLE);
                            }

                            /*售价赔偿协议*/
                            final String g3Str = data.getData().getProtocol();
                            if (TextUtils.isEmpty(g3Str)){
                                gm3.setVisibility(View.GONE);
                                gt3.setVisibility(View.GONE);
                            }else{
                                gm3.setVisibility(View.VISIBLE);
                                gt3.setVisibility(View.VISIBLE);

                                gm3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(baseContext,SingleImgActivity.class);
                                        intent.putExtra("pic",g3Str);
                                        intent.putExtra("topTitle","售假赔偿协议");
                                        startActivity(intent);
                                    }
                                });

                                gt3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(baseContext,SingleImgActivity.class);
                                        intent.putExtra("pic",g3Str);
                                        intent.putExtra("topTitle","售假赔偿协议");
                                        startActivity(intent);
                                    }
                                });

                            }


                            /*描述相符*/
                            String matchStar = data.getData().getMatch();
                            FormatterUtil.setStarRating(matchStar,ratbar1);

                            /*商品评价*/
                            String evalStar = data.getData().getGoods();
                            FormatterUtil.setStarRating(evalStar,ratbar2);

                            /*服务态度*/
                            String attitudeStar = data.getData().getAttitude();
                            FormatterUtil.setStarRating(attitudeStar,ratbar3);

                            /*物流速度*/
                            String speedStar = data.getData().getSpeed();
                            FormatterUtil.setStarRating(speedStar,ratbar4);

                            /*客服电话*/
                            final String clientPhoneStr = data.getData().getPhone();
                            tvKefucall.setText(clientPhoneStr);
                            if (!TextUtils.isEmpty(clientPhoneStr)){
                                callDialog = new ActionDialog(baseContext,clientPhoneStr,"取消","拨打");
                                callDialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
                                    @Override
                                    public void onLeftClick() {

                                    }

                                    @Override
                                    public void onRightClick() {
                                        ProjectUtils.doCallTo(StoreInfoActivity.this,clientPhoneStr);
                                    }
                                });
                            }

                            /*企业名称*/
                            String nameStr = data.getData().getName();
                            tvName.setText(nameStr);

                            /*企业证件*/
                            yyzzImgStr = data.getData().getImg();

                            /*所在地区*/
                            String addressStr = data.getData().getAddress();
                            String addressDetailStr = data.getData().getDoornum();
                            tvAdd.setText(addressStr+addressDetailStr);

                            /*开店时间*/
                            String timeStr = data.getData().getCreatetime();
                            tvTime.setText(timeStr);

                            /*营业时间*/
                            String openTimeStr = data.getData().getYysj1();
                            tvOpenTime.setText(openTimeStr);

                            /*个性签名*/
                            String signStr = data.getData().getSign();
                            tvSignature.setText(signStr);

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },false,true);
    }


    private boolean isCollect = false;/*用户是否收藏*/
    private long collectNumValue = 0;/*多少人收藏*/
    /*改变店铺关注状态*/
    private void updateShopCollectState(){
        mRequest04 = getRequest(Params.updateShopCollState,true);
        mRequest04.add("shopid",id);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest04,
                new CustomHttpListener<ActionBean>(baseContext,true, ActionBean.class) {
                    @Override
                    public void doWork(ActionBean result, String code) {
                        try{

                            isCollect = !isCollect;
                            showtoa(result.getMsg());
                            if (isCollect){
                                collectNumValue = collectNumValue+1;
                                tvSc.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ico_img51,0,0);
                            }else{
                                collectNumValue = collectNumValue-1>=0?collectNumValue-1:0;
                                tvSc.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ico_img06,0,0);
                            }
                            tvScnums.setText(FormatterUtil.formatNumber(collectNumValue)+"人收藏");
                        }catch (Exception e){

                        }
                    }
                },false,true);
    }



}
