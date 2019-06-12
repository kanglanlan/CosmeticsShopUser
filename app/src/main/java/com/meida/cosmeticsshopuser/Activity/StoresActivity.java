package com.meida.cosmeticsshopuser.Activity;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.ActionBean;
import com.meida.cosmeticsshopuser.Bean.GoodsItemBean;
import com.meida.cosmeticsshopuser.Bean.GoodsListBean;
import com.meida.cosmeticsshopuser.Bean.ShopDetailBean;
import com.meida.cosmeticsshopuser.Bean.ShopEvalBean;
import com.meida.cosmeticsshopuser.Bean.ShopEventBean;
import com.meida.cosmeticsshopuser.Bean.ShopTypeBean;
import com.meida.cosmeticsshopuser.MyView.ClearEditText;
import com.meida.cosmeticsshopuser.MyView.FlowLiner;
import com.meida.cosmeticsshopuser.MyView.dialog.HeadPopuWindow;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.GoodsPJAdapter;
import com.meida.cosmeticsshopuser.adapter.ProductAdapter;
import com.meida.cosmeticsshopuser.adapter.ShopEventAdapter2;
import com.meida.cosmeticsshopuser.adapter.viewpager.RadioAdapter;
import com.meida.cosmeticsshopuser.base.App;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.AnimUtil;
import com.meida.cosmeticsshopuser.utils.AppBarStateChangeListener;
import com.meida.cosmeticsshopuser.utils.DisplayUtil;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.meida.cosmeticsshopuser.utils.ProjectUtils;
import com.meida.cosmeticsshopuser.utils.TabLayoutUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.socialize.UMShareAPI;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

public class StoresActivity extends BaseActivity {

    private String oid = "";

    @Bind(R.id.smart1)
    SmartRefreshLayout smart1;
    @Bind(R.id.smart2)
    SmartRefreshLayout smart2;
    @Bind(R.id.smart3)
    SmartRefreshLayout smart3;
    @Bind(R.id.empty_view)
    View emptyView;
    @Bind(R.id.topView)
    View topView;
    @Bind(R.id.tv_search1)
    ClearEditText tv_search1;
    @Bind(R.id.collapsing_layout)
    CollapsingToolbarLayout collapsingLayout;
    @Bind(R.id.appbar_layout)
    AppBarLayout appbarLayout;
    @Bind(R.id.cartNum)
    TextView cartNum;
    @Bind(R.id.preson_layout)
    View presonLayout;

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

    @Bind(R.id.couponParent)
    FlowLiner couponParent;

    /*店铺详情*/
    @Bind(R.id.shopBg)
    ImageView shopBg;
    @Bind(R.id.img_dianpu)
    ImageView img_dianpu;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.tv_scnums)
    TextView tv_scnums;
    @Bind(R.id.tv_sc)
    TextView tv_sc;


    @Bind(R.id.grpj)
    RadioGroup grpj;
    @Bind(R.id.ll_one)
    LinearLayout ll_one;
    @Bind(R.id.rb_pj1)
    RadioButton rb_pj1;
    @Bind(R.id.rb_pj2)
    RadioButton rb_pj2;
    @Bind(R.id.rb_pj3)
    RadioButton rb_pj3;
    @Bind(R.id.rb_pj4)
    RadioButton rb_pj4;

    @Bind(R.id.goodsRecycler)
    RecyclerView goodsRecycler;
    ProductAdapter adapter;
    private List<GoodsItemBean> goodsData = new ArrayList<>();

    @Bind(R.id.evalRecycler)
    RecyclerView evalRecycler;
    private GoodsPJAdapter evalAdapter;
    private ArrayList<ShopEvalBean.ShopEvalItemBean> evalData = new ArrayList<>();

    @Bind(R.id.eventRecycler)
    RecyclerView eventRecycler;
    private ShopEventAdapter2 eventAdapter;
    private ArrayList<ShopEventBean.ShopEventItemBean> eventData = new ArrayList<>();

    @Bind(R.id.tablayout_mo)
    TabLayout tablayoutMo;
    ArrayList<String> datas = new ArrayList<>();
    int type = 1;

    /*排列变化*/
    @Bind(R.id.sort_check)
    CheckBox radioSortCheck;

    /*推荐销量价格*/
    @Bind(R.id.optionGroup)
    RadioGroup optionGroup;
    @Bind(R.id.rb_1)
    RadioButton rb1;
    @Bind(R.id.rb_2)
    RadioButton rb2;
    @Bind(R.id.rb_3)
    RadioButton rb3;

    @Bind(R.id.more)
    View more;
    /*右上方更多弹窗*/
    private PopupWindow morePop;
    private AnimUtil animUtil;
    private static final long DURATION = 150;
    private static final float START_ALPHA = 0.75f;
    private static final float END_ALPHA = 1f;
    private float bgAlpha = 1f;
    private boolean bright = false;

    /*价格弹窗*/
    private HeadPopuWindow pricePop;
    private RadioGroup priceGroup;
    private RadioButton priceDown,priceUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);
        ButterKnife.bind(this);
        oid = getIntent().getStringExtra("id");
        initview();
        getdata(true);
    }

    private void initview() {
        int height;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            height = DisplayUtil.dp2px(220+23);
            ViewGroup.LayoutParams pLp = presonLayout.getLayoutParams();
            pLp.height = height;
            ViewGroup.LayoutParams bgLp = shopBg.getLayoutParams();
            bgLp.height = height;
            presonLayout.setLayoutParams(pLp);
            shopBg.setLayoutParams(bgLp);
        }

        appbarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state, int i_rate) {
                if (state == State.EXPANDED) {
                    //展开状态
                    topView.setBackgroundColor(getResources().getColor(R.color.transparent));
                    tv_search1.setBackground(getResources().getDrawable(R.drawable.bt_searchbai));
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    topView.setBackgroundColor(getResources().getColor(R.color.white));
                    tv_search1.setBackground(getResources().getDrawable(R.drawable.bt_searchhui));
                } else {
                    //中间状态
                    topView.setBackgroundColor(getResources().getColor(R.color.transparent));
                    tv_search1.setBackground(getResources().getDrawable(R.drawable.bt_searchbai));
                }
            }
        });
        tablayoutMo.addTab(tablayoutMo.newTab().setText("商品"));
        tablayoutMo.addTab(tablayoutMo.newTab().setText("评价"));
        tablayoutMo.addTab(tablayoutMo.newTab().setText("商家动态"));
        TabLayoutUtil.optimisedTabLayout(tablayoutMo, App.wid/3);
        gridLayoutManager = new GridLayoutManager(baseContext, 1);
        goodsRecycler.setLayoutManager(gridLayoutManager);
        adapter = new ProductAdapter(baseContext, goodsData ,1);
        goodsRecycler.setAdapter(adapter);
        goodsRecycler.setNestedScrollingEnabled(false);

        LinearLayoutManager lm1 = new LinearLayoutManager(baseContext,LinearLayoutManager.VERTICAL,false);
        evalRecycler.setLayoutManager(lm1);
        evalAdapter = new GoodsPJAdapter(baseContext,evalData);
        evalRecycler.setAdapter(evalAdapter);
        evalRecycler.setNestedScrollingEnabled(false);

        LinearLayoutManager lm2 = new LinearLayoutManager(baseContext,LinearLayoutManager.VERTICAL,false);
        eventRecycler.setLayoutManager(lm2);
        eventAdapter = new ShopEventAdapter2(baseContext,R.layout.item_dianpudongtai,eventData);
        eventRecycler.setAdapter(eventAdapter);
        eventRecycler.setNestedScrollingEnabled(false);

        tablayoutMo.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        ll_one.setVisibility(View.VISIBLE);
                        grpj.setVisibility(View.GONE);
                        type = 1;
                        /*goodsRecycler.setVisibility(View.VISIBLE);
                        evalRecycler.setVisibility(View.GONE);
                        eventRecycler.setVisibility(View.GONE);*/
                        smart1.setVisibility(View.VISIBLE);
                        smart2.setVisibility(View.GONE);
                        smart3.setVisibility(View.GONE);
                        break;
                    case 1:
                        ll_one.setVisibility(View.GONE);
                        grpj.setVisibility(View.VISIBLE);
                        type = 2;
                       /* goodsRecycler.setVisibility(View.GONE);
                        evalRecycler.setVisibility(View.VISIBLE);
                        eventRecycler.setVisibility(View.GONE);*/
                        smart1.setVisibility(View.GONE);
                        smart2.setVisibility(View.VISIBLE);
                        smart3.setVisibility(View.GONE);
                        break;
                    case 2:
                        ll_one.setVisibility(View.GONE);
                        grpj.setVisibility(View.GONE);
                        type = 3;
                        /*goodsRecycler.setVisibility(View.GONE);
                        evalRecycler.setVisibility(View.GONE);
                        eventRecycler.setVisibility(View.VISIBLE);*/
                        smart1.setVisibility(View.GONE);
                        smart2.setVisibility(View.GONE);
                        smart3.setVisibility(View.VISIBLE);
                        break;
                }
                checkEmpty();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        initMorePop();
        initPricePop();
        rb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pricePop.show();
            }
        });

        optionGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==R.id.rb_3){

                }else{
                    loadGoods(true,true);
                }

            }
        });

        radioSortCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){/*一行一行*/
                    gridLayoutManager.setSpanCount(1);
                    adapter.setSpan_size(ProductAdapter.SPAN_ONE);
                    adapter.notifyItemRangeChanged(0, adapter.getItemCount());
                    //loadGoods(true,true);

                }else{
                   /*两列*/
                    gridLayoutManager.setSpanCount(2);
                    adapter.setSpan_size(ProductAdapter.SPAN_TWO);
                    adapter.notifyItemRangeChanged(0, adapter.getItemCount());
                    //loadGoods(true,true);
                }
            }
        });

        grpj.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                getEvalList(true,true);
            }
        });

        findViewById(R.id.baseBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        smart1.setEnableRefresh(true);
        smart1.setEnableLoadMore(true);
        smart1.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadGoods(false,false);
            }
        });
        smart1.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadGoods(true,false);
            }
        });


        smart2.setEnableRefresh(true);
        smart2.setEnableLoadMore(true);
        smart2.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getEvalList(false,false);
            }
        });
        smart2.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getEvalList(true,false);
            }
        });

        smart3.setEnableRefresh(true);
        smart3.setEnableLoadMore(true);
        smart3.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getEventData(false,false);
            }
        });
        smart3.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getEventData(true,false);
            }
        });

    }

    int pager1 = 1,pager2 = 1,pager3 = 1;
    private void getdata(boolean b) {
        getShopDetail(true);
        loadGoods(true,false);
        getEvalList(true,false);
        getEventData(true,false);
        getTypes();
    }

    @OnClick({R.id.img_fabu, R.id.dianpuinfo, R.id.tv_denlei, R.id.kefu,R.id.more,R.id.tv_sc,R.id.toCouponView,R.id.couponMore,R.id.couponParent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_fabu://购物车
                startActivity(new Intent(baseContext,ShopCartActivity.class));
                break;
            case R.id.dianpuinfo:
                startActivity(new Intent(baseContext,StoreInfoActivity.class).putExtra("id",oid));
                break;
            case R.id.tv_denlei:
                if (goodsTypePop!=null){
                    goodsTypePop.show();
                }
                break;
            case R.id.kefu:
                if (needLogin()){
                    return;
                }
                if (detailBean==null){
                    return;
                }
                RongIM.getInstance().refreshUserInfoCache(new UserInfo(detailBean.getKfid() + "",
                        detailBean.getTitle(), Uri.parse(detailBean.getAvatar())));
                RongIM.getInstance().startConversation(baseContext, Conversation.ConversationType.PRIVATE,
                        detailBean.getKfid(), detailBean.getTitle());
                break;
            case R.id.more:
                showMorePop();
                break;
            case R.id.tv_sc:
                if (needLogin()){
                    return;
                }
                updateShopCollectState();
                break;
            case R.id.toCouponView:
                /*if (needLogin()){
                    return;
                }
                Intent intent = new Intent(baseContext,ShopCouponActivity.class);
                intent.putExtra("id",oid);
                startActivity(intent);*/
                break;
            case R.id.couponMore:
                if (needLogin()){
                    return;
                }
                Intent intent1 = new Intent(baseContext,ShopCouponActivity.class);
                intent1.putExtra("id",oid);
                startActivity(intent1);
                break;
            case R.id.couponParent:
                if (needLogin()){
                    return;
                }
                Intent intent2 = new Intent(baseContext,ShopCouponActivity.class);
                intent2.putExtra("id",oid);
                startActivity(intent2);
                break;
        }
    }

    private void initMorePop(){
        animUtil = new AnimUtil();
        morePop = new PopupWindow(baseContext);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_shop_pop,null);
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

        dialogView.findViewById(R.id.msg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                morePop.dismiss();
                RongIM.getInstance().refreshUserInfoCache(new UserInfo(detailBean.getKfid() + "",
                        detailBean.getTitle(), Uri.parse(detailBean.getAvatar())));
                RongIM.getInstance().startConversation(baseContext, Conversation.ConversationType.PRIVATE,
                        detailBean.getKfid(), detailBean.getTitle());
            }
        });

        dialogView.findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                morePop.dismiss();
                ProjectUtils.backToHome();

            }
        });

        dialogView.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                morePop.dismiss();
                if (ProjectUtils.needLogin(baseContext)){
                    return;
                }
                ProjectUtils.share(StoresActivity.this,detailBean.getTitle(),2);

            }
        });
        dialogView.findViewById(R.id.detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                morePop.dismiss();
                startActivity(new Intent(baseContext,StoreInfoActivity.class).putExtra("id",oid));
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

    private void initPricePop(){
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_price_pop,null);
        priceGroup = dialogView.findViewById(R.id.radioGroup);
        priceUp = dialogView.findViewById(R.id.price_up);
        priceDown = dialogView.findViewById(R.id.price_down);
        pricePop = new HeadPopuWindow(baseContext,dialogView);
        priceGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                loadGoods(true,true);
                pricePop.dismiss();
            }
        });
    }

    /*获取店铺商品评价*/
    private void getEvalList(final boolean isRefresh, boolean showLoading) {
        if (isRefresh){
            pager2 = 1;
        }
        mRequest01 = getRequest(Params.getGoodsEvalList, false);
        mRequest01.add("id", oid);
        mRequest01.add("page", pager2);
        if (rb_pj1.isChecked()){/*评价星级 1好评 2中评 3差评 为空 全部	*/
            mRequest01.add("goods", "");
        }else if (rb_pj2.isChecked()){
            mRequest01.add("goods", "1");
        }else if (rb_pj3.isChecked()){
            mRequest01.add("goods", "2");
        }else if (rb_pj4.isChecked()){
            mRequest01.add("goods", "3");
        }

        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<ShopEvalBean>(baseContext, true, ShopEvalBean.class) {
                    @Override
                    public void doWork(ShopEvalBean data, String code) {
                        try{
                            List<ShopEvalBean.ShopEvalItemBean> result = data.getData().getData();
                            if (isRefresh){
                                evalData.clear();
                            }
                            if (result!=null &&result.size()>0){
                                evalData.addAll(result);
                            }
                            evalAdapter.notifyDataSetChanged();
                            pager2++;
                        }catch (Exception e){

                        }
                    }

                    @Override
                    public void onFinally(JSONObject obj, String code, boolean isNetSucceed) {
                        super.onFinally(obj, code, isNetSucceed);
                        smart2.finishLoadMore(true);
                        smart2.finishRefresh(true);
                        checkEmpty();
                    }


                }, false, showLoading);
    }

    /*获取店铺动态*/
    private void getEventData(final boolean isRefresh, boolean showLoading) {
        if (isRefresh){
            pager3 = 1;
        }
        mRequest02 = getRequest(Params.getShopEventList, true);
        mRequest02.add("id", oid);
        mRequest02.add("page", pager3);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest02,
                new CustomHttpListener<ShopEventBean>(baseContext, true, ShopEventBean.class) {
                    @Override
                    public void doWork(ShopEventBean data, String code) {
                        try{
                            List<ShopEventBean.ShopEventItemBean> result = data.getData().getData();
                            if (isRefresh){
                                eventData.clear();
                            }

                            if (result!=null && result.size()>0){
                                eventData.addAll(result);
                            }
                            eventAdapter.notifyDataSetChanged();

                            pager3++;

                        }catch (Exception e){

                        }
                    }
                    @Override
                    public void onFinally(JSONObject obj, String code, boolean isNetSucceed) {
                        super.onFinally(obj, code, isNetSucceed);
                        smart3.finishLoadMore(true);
                        smart3.finishRefresh(true);
                        checkEmpty();
                    }
                }, false, showLoading);
    }

    ShopDetailBean.DataBean detailBean ;
    /*获取店铺详情*/
    private void getShopDetail(boolean showLoading){
        mRequest03 = getRequest(Params.getShopDetail,true);
        mRequest03.add("id",oid);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest03,
                new CustomHttpListener<ShopDetailBean>(baseContext,true,ShopDetailBean.class) {
                    @Override
                    public void doWork(ShopDetailBean data, String code) {
                        try{
                            ShopDetailBean.DataBean result = data.getData();
                            detailBean = result;
                            /*店铺logo*/
                            String logoStr = result.getAvatar();
                            GlideUtils.loadShop(logoStr, img_dianpu);
                            /*店铺背景图*/
                            String backBg = result.getBackground();
                            GlideUtils.loadShopBg(backBg, shopBg);
                            /*店铺名称*/
                            String name = result.getTitle();
                            tv_title.setText(name);

                            /*是否收藏*/
                            String iscollection = result.getIscollection();
                            if ("1".equals(iscollection)){
                                isCollect =  false;
                                tv_sc.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ico_img06,0,0);
                            }else{
                                isCollect = true;
                                tv_sc.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ico_img51,0,0);
                            }

                            /*收藏人数*/
                            String collectNum = result.getCollection();
                            try{
                                collectNumValue = Long.parseLong(collectNum);
                            }catch (Exception e){

                            }
                            tv_scnums.setText(FormatterUtil.formatNumber(collectNumValue)+"人收藏");

                            /*商品列表*/
                           /* List<GoodsItemBean> goodsList = result.getGoodslist().getData();
                            goodsData.clear();
                            goodsData.addAll(goodsList);
                            adapter.notifyDataSetChanged();
                            pager1++;*/

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
                            if (!"1".equals(g2Str)){
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

                            /*优惠券*/
                            String couponStr = data.getData().getCoupon();

                            /*couponStr = "123,2345,3555,677,33322,5658,23445112";*/
                            if ( (!TextUtils.isEmpty(couponStr)) && (couponStr.length()>0)){
                                String[] strs = couponStr.split(",");
                                addCouponChild(strs);
                            }

                            /*好评数*/
                            long praise =  detailBean.getPraise();
                            /*中评数*/
                            long mean =  detailBean.getMean();
                            /*差评数*/
                            long negative =  detailBean.getNegative();

                            /*praise = 999;
                            mean = 2400;
                            negative = 3500000;*/

                            long evalSuml = praise+mean+negative;
                            if (praise>999){
                                String str = FormatterUtil.formatBigNumber(praise);
                                rb_pj2.setText(String.format("好评(%s)", str));
                            }else{
                                rb_pj2.setText(new StringBuilder()
                                        .append("好评(").append(String.valueOf(praise)).append(")"));
                            }

                            if (negative>999){
                                String str = FormatterUtil.formatBigNumber(negative);
                                rb_pj4.setText(String.format("差评(%s)", str));
                            }else{
                                rb_pj4.setText(new StringBuilder()
                                        .append("差评(").append(String.valueOf(negative)).append(")"));
                            }

                            if (mean>999){
                                String str = FormatterUtil.formatBigNumber(mean);
                                rb_pj3.setText(String.format("中评(%s)", str));
                            }else{
                                rb_pj3.setText(new StringBuilder()
                                        .append("中评(").append(String.valueOf(mean)).append(")"));
                            }

                            if (evalSuml>999){
                                String str = FormatterUtil.formatBigNumber(evalSuml);
                                rb_pj1.setText(String.format("全部(%s)", str));
                            }else{
                                rb_pj1.setText(new StringBuilder()
                                        .append("全部(").append(String.valueOf(evalSuml)).append(")"));
                            }

                        }catch (Exception e){

                        }
                    }
                },false,showLoading);
    }

    /*加载更多商品*/
    private void loadGoods(final boolean isRefresh, boolean showLoading){
        if (isRefresh){
            pager1 = 1;
        }
        mRequest03 = getRequest(Params.getShopInGoodsList,true);
        mRequest03.add("id",oid);
        mRequest03.add("page",pager1);
        if (rb1.isChecked()){/*推荐排序*/
            mRequest03.add("isrecommendshop","1");/*店长推荐 1、是 2 否 为空所有*/
            mRequest03.add("salesvolume","");/*销量 不为空按销量排序 为空不按销量排*/
            mRequest03.add("price","");
        }else if (rb2.isChecked()){/*销量排序*/
            mRequest03.add("isrecommendshop","");
            mRequest03.add("salesvolume","1");
            mRequest03.add("price","");
        }else if (rb3.isChecked()){
            if (priceUp.isChecked()){
                mRequest03.add("isrecommendshop","");
                mRequest03.add("salesvolume","");
                mRequest03.add("price","2");
            }else if (priceDown.isChecked()){
                mRequest03.add("isrecommendshop","");
                mRequest03.add("salesvolume","");
                mRequest03.add("price","1");/*价格 1降序 2升序*/
            }else{
                mRequest03.add("isrecommendshop","");
                mRequest03.add("salesvolume","");
                mRequest03.add("price","");/*价格 1降序 2升序*/
            }
        }

        mRequest03.add("title",tv_search1.getText().toString().trim());

        if (typeAdapter!=null && typeAdapter.getSelectIndex()>=0){
            mRequest03.add("cid",typeData.get(typeAdapter.getSelectIndex()).getId());
        }
        CallServer.getRequestInstance().add(baseContext, 0, mRequest03,
                new CustomHttpListener<GoodsListBean>(baseContext,true,GoodsListBean.class) {
                    @Override
                    public void doWork(GoodsListBean data, String code) {
                        try{
                            GoodsListBean.DataBean result = data.getData();
                            /*商品列表*/
                            List<GoodsItemBean> goodsList = result.getData();
                            if (isRefresh){
                                goodsData.clear();
                            }
                            if (goodsList!=null &&goodsList.size()>0){
                                goodsData.addAll(goodsList);
                            }
                            adapter.notifyDataSetChanged();
                            pager1++;

                        }catch (Exception e){

                        }
                    }

                    @Override
                    public void onFinally(JSONObject obj, String code, boolean isNetSucceed) {
                        super.onFinally(obj, code, isNetSucceed);
                        smart1.finishLoadMore(true);
                        smart1.finishRefresh(true);
                        checkEmpty();
                    }
                },false,showLoading);
    }

    private void addCouponChild(String[] strArr){
        couponParent.removeAllViews();
        if (strArr.length>0){
            View gurView = findViewById(R.id.gurView);
            LinearLayout.LayoutParams glp = (LinearLayout.LayoutParams)gurView.getLayoutParams();
            glp.setMargins(0,0,0,DisplayUtil.dp2px(5));
            gurView.setLayoutParams(glp);
            for (int i = 0; i<strArr.length;i++){
                if (!TextUtils.isEmpty(strArr[i])){
                    View childView = getLayoutInflater().inflate(R.layout.layout_shop_coupon,null);
                    TextView text = childView.findViewById(R.id.text);
                    text.setText(strArr[i]);
                    couponParent.addView(childView);
                }
            }
            ViewGroup.LayoutParams lp = couponParent.getLayoutParams();
            lp.height = DisplayUtil.dp2px(25);
            couponParent.setLayoutParams(lp);
            findViewById(R.id.couponMore).setVisibility(View.VISIBLE);
        }
    }

    private boolean isCollect = false;/*用户是否收藏*/
    private long collectNumValue = 0;/*多少人收藏*/
    /*改变店铺关注状态*/
    private void updateShopCollectState(){
        mRequest04 = getRequest(Params.updateShopCollState,true);
        mRequest04.add("shopid",oid);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest04,
                new CustomHttpListener<ActionBean>(baseContext,true, ActionBean.class) {
                    @Override
                    public void doWork(ActionBean result, String code) {
                        try{

                            isCollect = !isCollect;
                            showtoa(result.getMsg());
                            if (isCollect){
                                collectNumValue = collectNumValue+1;
                                tv_sc.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ico_img51,0,0);
                            }else{
                                collectNumValue = collectNumValue-1>=0?collectNumValue-1:0;
                                tv_sc.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ico_img06,0,0);
                            }
                            tv_scnums.setText(FormatterUtil.formatNumber(collectNumValue)+"人收藏");
                        }catch (Exception e){

                        }
                    }
                },false,true);
    }

    private ArrayList<ShopTypeBean.Bean> typeData = new ArrayList<>();
    private ArrayList<String> typeStrData = new ArrayList<>();

    /*获取商品分类列表*/
    private void getTypes(){
        mRequest01 = getRequest(Params.getCategory,false);
        mRequest01.add("pid","0");
        mRequest01.add("showsub","1");
        mRequest01.add("page",1);
        mRequest01.add("size",200);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<ShopTypeBean>(baseContext, true, ShopTypeBean.class) {
                    @Override
                    public void doWork(ShopTypeBean result, String code) {
                        try {
                            List<ShopTypeBean.Bean> beans = result.getData().getData();
                            if (beans != null && beans.size() > 0) {
                                typeData.clear();
                                typeData.addAll(beans);
                            }

                            typeStrData.clear();
                            for (int i = 0; i<typeData.size(); i++){
                                typeStrData.add(typeData.get(i).getTitle());
                            }

                            initGoodsTypePop();

                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onFinally(JSONObject obj, String code, boolean isNetSucceed) {
                        super.onFinally(obj, code, isNetSucceed);
                    }
                }, false, false);
    }

    /*商品列表弹窗*/
    HeadPopuWindow goodsTypePop;
    private RadioAdapter typeAdapter;
    private void initGoodsTypePop(){
        View dialogView = LayoutInflater.from(baseContext).inflate(R.layout.dialog_goods_type_pop,null);
        dialogView.findViewById(R.id.dialogClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goodsTypePop.dismiss();
            }
        });
        RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(baseContext);
        recyclerView.setLayoutManager(linearLayoutManager);
        typeAdapter = new RadioAdapter(baseContext,R.layout.item_radio_type,typeStrData);
        recyclerView.setAdapter(typeAdapter);
        goodsTypePop = new HeadPopuWindow(baseContext,dialogView);
        typeAdapter.setListener(new RadioAdapter.OnCheckChangeListener() {
            @Override
            public void onCheckChange(int position) {
                loadGoods(true,true);
                goodsTypePop.dismiss();
            }
        });
        ProjectUtils.setRecyclerMaxHeight(recyclerView, 180);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ProjectUtils.updateCartNum(baseContext,cartNum);
    }

    private void checkEmpty(){
        int itemCount = 1;
        switch (tablayoutMo.getSelectedTabPosition()){
            case 0:
                itemCount = adapter.getItemCount();
                break;
            case 1:
                itemCount = evalAdapter.getItemCount();
                break;
            case 2:
                itemCount = eventAdapter.getItemCount();
                break;
        }

        if (itemCount>0){
            emptyView.setVisibility(View.GONE);
        }else{
            emptyView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction()) {
            String edStr = tv_search1.getText().toString().trim();
            if (TextUtils.isEmpty(edStr)){
                showtoa("请输入关键词");
            }else{
                loadGoods(true,true);
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}
