package com.meida.cosmeticsshopuser.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.ActionBean;
import com.meida.cosmeticsshopuser.Bean.BaseBean;
import com.meida.cosmeticsshopuser.Bean.ShopCouponBean;
import com.meida.cosmeticsshopuser.Bean.ShopDetailBean;
import com.meida.cosmeticsshopuser.Bean.ShopGoodsBean;
import com.meida.cosmeticsshopuser.MyView.dialog.ActionDialog;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.ShopCouponAdapter;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.meida.cosmeticsshopuser.utils.ProjectUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShopCouponActivity extends BaseActivity {

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

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.empty_view)
    View emptyView;
    private ShopCouponAdapter adapter;
    private ArrayList<ShopCouponBean.ItemBean> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_coupon);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        LinearLayoutManager lm = new LinearLayoutManager(baseContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(lm);
        adapter = new ShopCouponAdapter(baseContext,R.layout.item_shop_coupon,data);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        tvSc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (needLogin()){
                    return;
                }
                updateShopCollectState();
            }
        });
        adapter.setListener(new ShopCouponAdapter.OnItemActionListener() {
            @Override
            public void onPick(int position, ShopCouponBean.ItemBean bean) {
                pickCoupon(bean.getId());
            }

            @Override
            public void onUseNow(int position, ShopCouponBean.ItemBean bean) {
                Intent intent = new Intent(baseContext,GoodsListActivity.class);
                intent.putExtra("shopId",id);
                intent.putExtra("couponId",bean.getUsercid());
                startActivity(intent);
            }
        });

        findViewById(R.id.baseBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getDetail();
        getCouponData();

    }

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

    private void getCouponData(){
        mRequest02 = getRequest(Params.getShopCoupon,true);
        mRequest02.add("id",id);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest02,
                new CustomHttpListener<ShopCouponBean>(baseContext,true,ShopCouponBean.class) {
                    @Override
                    public void doWork(ShopCouponBean result, String code) {
                        try{
                            List<ShopCouponBean.ItemBean> beans = result.getData().getData();
                            if (beans!=null && beans.size()>0){
                                data.clear();
                                data.addAll(beans);
                            }
                            adapter.notifyDataSetChanged();
                        }catch (Exception e){

                        }
                    }

                    @Override
                    public void onFinally(JSONObject obj, String code, boolean isNetSucceed) {
                        super.onFinally(obj, code, isNetSucceed);
                        checkEmpty();
                    }
                },false,false);
    }


    private void pickCoupon(String id){
        mRequest05 = getRequest(Params.pickCoupon,true);
        mRequest05.add("id",id);
        CallServer.getRequestInstance().add(baseContext,0,mRequest05,
                new CustomHttpListener<ActionBean>(baseContext, true, ActionBean.class) {
                    @Override
                    public void doWork(ActionBean result, String code) {
                        showtoa(result.getMsg());
                        getCouponData();
                    }
                },false,true);

    }

    private void checkEmpty(){
        if (adapter.getItemCount()>0){
            emptyView.setVisibility(View.GONE);
        }else{
            emptyView.setVisibility(View.VISIBLE);
        }
    }


}
