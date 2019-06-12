package com.meida.cosmeticsshopuser.Activity;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.ActionBean;
import com.meida.cosmeticsshopuser.Bean.GoodsDetailBean;
import com.meida.cosmeticsshopuser.Bean.ShopCartBean;
import com.meida.cosmeticsshopuser.Bean.ShopCartRefresh;
import com.meida.cosmeticsshopuser.Bean.eventbus.ProductDetailRefresh;
import com.meida.cosmeticsshopuser.MyView.MUIToast;
import com.meida.cosmeticsshopuser.MyView.dialog.ProductSpecifyDialog;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.viewpager.TypesPagerAdapter;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.fragment.FragmentProductEvals;
import com.meida.cosmeticsshopuser.fragment.FragmentWebView;
import com.meida.cosmeticsshopuser.fragment.ProductDetailFragment;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.AnimUtil;
import com.meida.cosmeticsshopuser.utils.DisplayUtil;
import com.meida.cosmeticsshopuser.utils.ProjectUtils;
import com.meida.cosmeticsshopuser.utils.SoftHideKeyBoardUtil;
import com.meida.cosmeticsshopuser.utils.TabLayoutUtil;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

public class ProductDetailActivity extends BaseActivity {

    public String oid;
    public static GoodsDetailBean.DataBean detailBean ;

    private ViewPager viewPager;
    private ProductDetailFragment detailFragment;
    private FragmentWebView webFragment;
    private FragmentProductEvals evalFragment;
    private TabLayout tabLayout;

    private View topView;
    private ImageView back,share,more;

    private View client,shop,shopCar;
    private TextView carNum;
    private View addToCar,buyNow;

    private ProductSpecifyDialog specifyDialog;

    /*更多弹窗*/
    private PopupWindow morePop;
    private AnimUtil animUtil;
    private static final long DURATION = 150;
    private static final float START_ALPHA = 0.75f;
    private static final float END_ALPHA = 1f;
    private float bgAlpha = 1f;
    private boolean bright = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        SoftHideKeyBoardUtil.assistActivity(this);
        EventBus.getDefault().register(this);
        oid = getIntent().getStringExtra("id");
        initView();
        initEvent();
        initData();
    }

    private void initView(){
        viewPager = (android.support.v4.view.ViewPager) findViewById(R.id.viewPager);
        topView = findViewById(R.id.topView);
        back = (ImageView) findViewById(R.id.back);
        share = (ImageView) findViewById(R.id.share);
        more = (ImageView) findViewById(R.id.more);
        tabLayout = (android.support.design.widget.TabLayout) findViewById(R.id.tabLayout);

        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> fragmentTitles = new ArrayList<>();

        fragmentTitles.add("商品");
        fragmentTitles.add("详情");
        fragmentTitles.add("评价");

        detailFragment = ProductDetailFragment.newInstance();
        webFragment = FragmentWebView.newInstance();
        evalFragment = FragmentProductEvals.newInstance(oid);
        fragments.add(detailFragment);
        fragments.add(webFragment);
        fragments.add(evalFragment);

        TypesPagerAdapter pagerAdapter = new TypesPagerAdapter(getSupportFragmentManager(),fragments,fragmentTitles);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setVisibility(View.GONE);
        TabLayoutUtil.optimisedTabLayout(tabLayout, DisplayUtil.dp2px(200)/fragmentTitles.size());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position!=0){
                    topView.setBackgroundColor(Color.parseColor("#ffffff"));
                    tabLayout.setVisibility(View.VISIBLE);
                }else if (firstShowTab){
                    topView.setBackgroundColor(Color.parseColor("#ffffff"));
                    tabLayout.setVisibility(View.VISIBLE);
                }else{
                    topView.setBackgroundColor(Color.parseColor("#00ffffff"));
                    tabLayout.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ProjectUtils.needLogin(baseContext)){
                    return;
                }
                if (detailBean==null){
                    return;
                }
                ProjectUtils.share(ProductDetailActivity.this,detailBean.getTitle(),1);
            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMorePop();
            }
        });

        client = (TextView) findViewById(R.id.client);
        shop = (TextView) findViewById(R.id.shop);
        shopCar = (FrameLayout) findViewById(R.id.shopCar);
        carNum = (TextView) findViewById(R.id.carNum);
        addToCar = (TextView) findViewById(R.id.addToCar);
        buyNow = (TextView) findViewById(R.id.buyNow);

        initMorePop();

    }

    private void initEvent(){
        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (needLogin()){
                    return;
                }
                if (detailBean==null){
                    return;
                }
                RongIM.getInstance().refreshUserInfoCache(new UserInfo(detailBean.getKfid() + "",
                        detailBean.getShopinfo().getTitle(), Uri.parse(detailBean.getShopinfo().getAvatar())));
                /*RongIM.getInstance().startPrivateChat(baseContext, detailBean.getKfid(), detailBean.getShoptitle());*/
                RongIM.getInstance().startConversation(baseContext, Conversation.ConversationType.PRIVATE,
                        detailBean.getKfid(), detailBean.getShoptitle());
            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detailBean==null){
                    return;
                }
                Intent intent = new Intent(baseContext,StoresActivity.class);
                intent.putExtra("id",detailBean.getShopid());
                startActivity(intent);
            }
        });

        shopCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (needLogin()){
                    return;
                }
                Intent intent = new Intent(baseContext,ShopCartActivity.class);
                startActivity(intent);
            }
        });

        addToCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               safeShowSpecifyDialog();
            }
        });

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                safeShowSpecifyDialog();
            }
        });
    }

    private void initData(){
        getDetail();
    }

    boolean isFirst = true;
    boolean firstShowTab = false;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(ProductDetailRefresh refresh){
        if (isFirst){
            isFirst = false;
        }else{
            if (refresh.isShowTab()){
                firstShowTab = true;
                if (viewPager.getCurrentItem()==0){
                    topView.setBackgroundColor(Color.parseColor("#ffffff"));
                    tabLayout.setVisibility(View.VISIBLE);
                    back.setImageResource(R.drawable.img17);
                    /*share.setImageResource(R.drawable.ico_img75);
                    more.setImageResource(R.drawable.ico_img80);*/
                }
            }else{
                firstShowTab = false;
                if (viewPager.getCurrentItem()==0){
                    topView.setBackgroundColor(Color.parseColor("#00ffffff"));
                    tabLayout.setVisibility(View.INVISIBLE);
                    back.setImageResource(R.drawable.img17);
                    /*share.setImageResource(R.drawable.ico_wem20);
                    more.setImageResource(R.drawable.ico_img18);*/
                }

            }
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /*选中的规格*/
    private int specifyIndex = -1;
    /*获取商品详情*/
    private void getDetail(){
        mRequest01 = getRequest(Params.getGoodsDetail,true);
        mRequest01.add("page","1");
        mRequest01.add("id",oid);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<GoodsDetailBean>(baseContext,true,GoodsDetailBean.class) {
                    @Override
                    public void doWork(GoodsDetailBean result, String code) {
                        try{
                            GoodsDetailBean.DataBean data = result.getData();
                            detailBean = data;

                            detailFragment.setData(detailBean);

                            /*联系客服*/
                            /*String kfid = data.getKfid();*/

                            /*店铺id*/
                            /*String shopId = data.getShopid();*/
                            evalFragment.setShopId(data);

                            /*详情*/
                            String content = data.getContent();
                            webFragment.setData(detailBean);

                            /*规格弹窗*/
                            specifyDialog = new ProductSpecifyDialog(baseContext,detailBean);
                            specifyDialog.setListener(new ProductSpecifyDialog.OnSpedifyChangeListener() {
                                @Override
                                public void onCheckChange(int position) {
                                    specifyIndex = position;
                                    detailFragment.setSpecify(position);
                                }
                            });
                            if (data.getSpec()!=null && data.getSpec().size()>0){
                                specifyIndex = 0;
                            }

                            specifyDialog.setActionListener(new ProductSpecifyDialog.OnActionListener() {
                                @Override
                                public void addToCar() {
                                    netAddToCar();
                                }

                                @Override
                                public void buyNow() {
                                    netBuyNow();
                                }
                            });


                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },false,true);

    }

    public void safeShowSpecifyDialog(){
        if (detailBean==null){
            return;
        }
        if (detailBean.getSpec()!=null && detailBean.getSpec().size()>0){
            if (specifyDialog!=null && (!specifyDialog.isShowing())){
                specifyDialog.show();
            }
        }else{
            showtoa("该商品暂不支持购买");
        }

    }

    public void jumpToEvalFragment(int index){
        viewPager.setCurrentItem(3);
        evalFragment.setSelect(index);
    }


    /*添加到购物车*/
    private void netAddToCar(){
        if (needLogin()){
            return;
        }
        if (specifyIndex==-1){
            MUIToast.show(baseContext,"请选择产品规格");
            return;
        }

        if (specifyDialog==null || specifyDialog.getBuyNumValue()<=0){
            MUIToast.show(baseContext,"购买数量不合法");
            return;
        }

        mRequest02 = getRequest(Params.addToShopCar,true);
        mRequest02.add("specid",detailBean.getSpec().get(specifyIndex).getId());
        mRequest02.add("num",specifyDialog.getBuyNumValue());
        CallServer.getRequestInstance().add(baseContext, 0, mRequest02,
                new CustomHttpListener<ActionBean>(baseContext,true,ActionBean.class) {
                    @Override
                    public void doWork(ActionBean result, String code) {
                        showtoa(result.getMsg());
                        ProjectUtils.updateCartNum(baseContext,carNum);
                        EventBus.getDefault().post(new ShopCartRefresh());
                    }
                },false,true);

    }

    /*直接购买*/
    private void netBuyNow(){
        if (needLogin()){
            return;
        }
        if (specifyIndex==-1){
            MUIToast.show(baseContext,"请选择产品规格");
            return;
        }

        if (specifyDialog==null || specifyDialog.getBuyNumValue()<=0){
            MUIToast.show(baseContext,"购买数量不合法");
            return;
        }

        List<ShopCartBean.CartGoods> goodsList = new ArrayList<>();
        ShopCartBean.Shop shopBean = new ShopCartBean.Shop();
        shopBean.setId(detailBean.getShopid());
        shopBean.setTitle(detailBean.getShoptitle());
        ShopCartBean.CartGoods goodsBean = new ShopCartBean.CartGoods();
        goodsBean.setId("");/*购物车id*/
        goodsBean.setGoodsid(oid);
        goodsBean.setGoodsspcid(detailBean.getSpec().get(specifyIndex).getId());
        goodsBean.setNum(specifyDialog.getBuyNumValue());
        goodsBean.setImgs(detailBean.getImgs());
        goodsBean.setTitle(detailBean.getTitle());
        goodsBean.setPrice(detailBean.getSpec().get(specifyIndex).getPrice());
        goodsBean.setSpectitle(detailBean.getSpec().get(specifyIndex).getTitle());
        goodsBean.setIsfreeshipping(detailBean.getIsfreeshipping());
        goodsBean.setIsfreesend(detailBean.getIsfreesend());
        goodsBean.setIsfreedelivery(detailBean.getIsfreedelivery());
        goodsList.add(goodsBean);
        shopBean.setGoodslist(goodsList);
        Intent intent = new Intent(baseContext,SubmitOrderActivity.class);
        intent.putExtra("data",shopBean);
        startActivity(intent);

    }



    private void initMorePop(){
        animUtil = new AnimUtil();
        morePop = new PopupWindow(baseContext);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_product_pop,null);
        morePop.setContentView(dialogView);
        morePop.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        morePop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        morePop.setBackgroundDrawable(new ColorDrawable(0x0000));
        morePop.setAnimationStyle(R.style.pop_more);
        morePop.setFocusable(true);
        morePop.setTouchable(true);
        morePop.setOutsideTouchable(true);
        morePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                toggleBright();
            }
        });

        dialogView.findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                morePop.dismiss();
                Intent intent = new Intent(baseContext,SearchActivity.class);
                startActivity(intent);
            }
        });

        dialogView.findViewById(R.id.collect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                morePop.dismiss();
                if (needLogin()){
                    return;
                }
                ProjectUtils.backToHome();
                Intent intent = new Intent(baseContext,CollectionGoodActivity.class);
                startActivity(intent);
            }
        });

        dialogView.findViewById(R.id.footprint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                morePop.dismiss();
                if (needLogin()){
                    return;
                }
                Intent intent = new Intent(baseContext,BrowsinghistoryActivity.class);
                startActivity(intent);

            }
        });
        dialogView.findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                morePop.dismiss();
                ProjectUtils.backToHome();
            }
        });

    }

    private void showMorePop(){
        toggleBright();
        morePop.showAsDropDown(more, 150, 5);
    }

    private void toggleBright() {
        // 三个参数分别为：起始值 结束值 时长，那么整个动画回调过来的值就是从0.5f--1f的
        animUtil.setValueAnimator(START_ALPHA, END_ALPHA, DURATION);
        animUtil.addUpdateListener(new AnimUtil.UpdateListener() {
            @Override
            public void progress(float progress) {
                bgAlpha = bright ? progress : (START_ALPHA + END_ALPHA - progress);
                backgroundAlpha(bgAlpha);
            }
        });
        animUtil.addEndListner(new AnimUtil.EndListener() {
            @Override
            public void endUpdate(Animator animator) {
                bright = !bright;
            }
        });
        animUtil.startAnimator();
    }

    /**
     * 此方法用于改变背景的透明度，从而达到“变暗”的效果
     */
    private void backgroundAlpha(float bgAlpha) {
        if (true)
            return;
        WindowManager.LayoutParams lp = baseContext.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        baseContext.getWindow().setAttributes(lp);
        baseContext.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ProjectUtils.updateCartNum(baseContext,carNum);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }


}
