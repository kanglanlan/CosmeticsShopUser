package com.meida.cosmeticsshopuser.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.ActionBean;
import com.meida.cosmeticsshopuser.Bean.GoodsDetailBean;
import com.meida.cosmeticsshopuser.Bean.GoodsItemBean;
import com.meida.cosmeticsshopuser.Bean.ShopEvalBean;
import com.meida.cosmeticsshopuser.Bean.eventbus.ProductDetailRefresh;
import com.meida.cosmeticsshopuser.adapter.GoodsPJAdapter;
import com.meida.cosmeticsshopuser.adapter.ProductAdapter;
import com.meida.cosmeticsshopuser.base.BaseFragment;
import com.meida.cosmeticsshopuser.Activity.ProductDetailActivity;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.AppBarStateChangeListener;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.meida.cosmeticsshopuser.utils.ProjectUtils;
import com.meida.cosmeticsshopuser.utils.SpanUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by Administrator on 2018/12/20 0020.
 */

public class ProductDetailFragment extends BaseFragment{

    private AppBarLayout appBar;
    private cn.bingoogolapple.bgabanner.BGABanner banner;
    private TextView price,oldPrice;
    private TextView name,specify;
    private View specifyView;
    private TextView collect;
    private TextView freight,saleNum;
    private LinearLayout addrView;
    private TextView addr;
    private TextView tip1,tip2;
    private TextView evalSum,positiveRate;
    private RadioGroup radioGroup;
    private RadioButton radioAll,radioNice,radioMiddle,radioBad;
    private RecyclerView evalRecycler;
    private ArrayList<ShopEvalBean.ShopEvalItemBean> evalData = new ArrayList<>();
    private GoodsPJAdapter evalAdapter;
    private TextView seeAllEval;
    private RecyclerView recommendRecycler;
    private List<GoodsItemBean> productData = new ArrayList<>();
    private ProductAdapter productAdapter;
    private SmartRefreshLayout smart;

    private SpanUtil spanUtil ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        spanUtil = new SpanUtil(getContext());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initEvent();
        initData();

    }


    private void initView(View rootView){
        smart = rootView.findViewById(R.id.smart);
        appBar = rootView.findViewById(R.id.appBar);
        banner = (cn.bingoogolapple.bgabanner.BGABanner) rootView.findViewById(R.id.banner);
        price = (TextView) rootView.findViewById(R.id.price);
        oldPrice = (TextView) rootView.findViewById(R.id.oldPrice);
        name = (TextView) rootView.findViewById(R.id.name);
        collect = (TextView) rootView.findViewById(R.id.collect);
        specify = (TextView) rootView.findViewById(R.id.specify);
        specifyView = rootView.findViewById(R.id.specifyView);
        freight = (TextView) rootView.findViewById(R.id.freight);
        saleNum = (TextView) rootView.findViewById(R.id.saleNum);
        addrView = (LinearLayout) rootView.findViewById(R.id.addrView);
        addr = (TextView) rootView.findViewById(R.id.addr);
        tip1 = (TextView) rootView.findViewById(R.id.tip1);
        tip2 = (TextView) rootView.findViewById(R.id.tip2);
        evalSum = (TextView) rootView.findViewById(R.id.evalSum);
        positiveRate = (TextView) rootView.findViewById(R.id.positiveRate);
        radioGroup = (RadioGroup) rootView.findViewById(R.id.radioGroup);
        radioAll = (RadioButton) rootView.findViewById(R.id.radioAll);
        radioNice = (RadioButton) rootView.findViewById(R.id.radioNice);
        radioMiddle = (RadioButton) rootView.findViewById(R.id.radioMiddle);
        radioBad = (RadioButton) rootView.findViewById(R.id.radioBad);
        evalRecycler = (android.support.v7.widget.RecyclerView) rootView.findViewById(R.id.evalRecycler);
        LinearLayoutManager lm1 = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        evalRecycler.setLayoutManager(lm1);
        evalRecycler.setNestedScrollingEnabled(false);
        evalAdapter = new GoodsPJAdapter(getContext(), evalData);
        evalRecycler.setAdapter(evalAdapter);
        seeAllEval = (TextView) rootView.findViewById(R.id.seeAllEval);
        recommendRecycler = (android.support.v7.widget.RecyclerView) rootView.findViewById(R.id.recommendRecycler);
        GridLayoutManager lm2 = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        recommendRecycler.setLayoutManager(lm2);
        recommendRecycler.setNestedScrollingEnabled(false);
        productAdapter = new ProductAdapter(getContext(),R.layout.item_shangpin,productData);
        recommendRecycler.setAdapter(productAdapter);

    }

    private void initEvent(){
        appBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state, int i_rate) {
                if (state == State.EXPANDED) {
                    //展开状态
                    EventBus.getDefault().post(new ProductDetailRefresh(false));
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    EventBus.getDefault().post(new ProductDetailRefresh(true));
                } else {
                    //中间状态
                    EventBus.getDefault().post(new ProductDetailRefresh(false));
                }
            }
        });
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (needLogin()){
                    return;
                }
                if (detailBean==null){
                    return;
                }
                updateGoodsCollectState();
            }
        });

        addrView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        specifyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity()!=null){
                    ((ProductDetailActivity)getActivity()).safeShowSpecifyDialog();
                }
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (getActivity()!=null){
                    switch (checkedId){
                        case R.id.radioAll:
                            ((ProductDetailActivity)getActivity()).jumpToEvalFragment(0);
                            break;
                        case R.id.radioNice:
                            ((ProductDetailActivity)getActivity()).jumpToEvalFragment(1);
                            break;
                        case R.id.radioMiddle:
                            ((ProductDetailActivity)getActivity()).jumpToEvalFragment(2);
                            break;
                        case R.id.radioBad:
                            ((ProductDetailActivity)getActivity()).jumpToEvalFragment(3);
                            break;
                    }

                }
            }
        });
        seeAllEval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity()!=null){
                    ((ProductDetailActivity)getActivity()).jumpToEvalFragment(-1);
                }
            }
        });

        smart.setEnableRefresh(false);
        smart.setEnableLoadMore(true);
        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadData();
            }
        });


    }

    private void initData(){

    }

    public static ProductDetailFragment newInstance() {
        Bundle bundle = new Bundle();
        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    private GoodsDetailBean.DataBean detailBean;
    public void setData(GoodsDetailBean.DataBean data){
        detailBean = data;
         /*轮播图*/
        String imgs = data.getImgs();
        if (!TextUtils.isEmpty(imgs)){
            String[] imgArr = imgs.split(",");
            final List<String> imgsList = Arrays.asList(imgArr);
            banner.setAdapter(new BGABanner.Adapter() {
                @Override
                public void fillBannerItem(BGABanner banner, View itemView, @Nullable Object model, final int position) {
                    ImageView img = itemView.findViewById(R.id.img);
                    String imgPath = (String) model;
                    GlideUtils.loadGoods(imgPath,img);
                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ProjectUtils.ShowLargeImg(getContext(),imgsList,position);
                        }
                    });
                }
            });
            banner.setData(R.layout.img_h_376,imgsList,null);
            if (imgsList.size()>1){
                banner.startAutoPlay();
            }else{
                banner.stopAutoPlay();
            }
        }

        /*价格 现价 原价*/
        String priceStr = data.getPrice();
        String oldPriceStr = data.getOldprice();
        FormatterUtil.formatPrice2(priceStr,price);
        StringBuilder op = new StringBuilder();
        op.append("原价：¥");
        op.append(oldPriceStr);
        spanUtil.setStrikeThroughSpan(op.toString(),oldPrice);

        /*是否收藏*/
        String isCollectStr = data.getIscollect();
        if ("1".equals(isCollectStr)){
            collect.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ico_img06,0,0);
        }else{
            collect.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ico_img51,0,0);
        }

        /*商品名称*/
        String goodsTitleStr = data.getTitle();
        name.setText(goodsTitleStr);

        /*TODO 包邮方式*/
        /*是否包邮*/
        StringBuilder builder = new StringBuilder();
        String isfreeshipping = data.getIsfreeshipping();
        if ("1".equals(data.getDistribution3())){
            if ("1".equals(isfreeshipping)){
                builder.append("快递配送：包邮");
            }else{
                builder.append("快递配送：不包邮");
            }
        }else{
            builder.append("快递配送：暂不支持");
        }
        builder.append("\n");

        /*是否包送*/
        if ("1".equals(data.getDistribution2())){
            if ("1".equals(data.getIsfreesend())){
                builder.append("商家自送：包送");
            }else{
                builder.append("商家自送：自送收费");
            }
        }else{
            builder.append("商家自送：暂不支持");
        }
        builder.append("\n");
        /*是否包配送*/
        if ("1".equals(data.getDistribution1())){
            if ("1".equals(data.getIsfreedelivery())){
                builder.append("同城配送：包配送");
            }else{
                builder.append("同城配送：配送收费");
            }
        }else{
            builder.append("同城配送：暂不支持");
        }

        freight.setText(builder.toString());

        /*销量*/
        long saleNumValue = data.getSalesvolume() + data.getSalesvolumea();
        saleNum.setText("销量："+saleNumValue);

        /*是否售后*/
        String aftersale = data.getAftersale();
        if ("1".equals(aftersale)){
            tip1.setVisibility(View.VISIBLE);
        }else{
            tip1.setVisibility(View.GONE);
        }

        /*是否7天退货*/
        String returngoods = data.getReturngoods();
        if ("1".equals(returngoods)){
            tip2.setVisibility(View.VISIBLE);
        }else{
            tip2.setVisibility(View.GONE);
        }

        /*TODO 全部 好 中  差  评价 数量*/
        long praise = data.getPraise();/*好评*/
        long negative = data.getNegative();/*差评*/
        long mean = data.getMean();/*中评*/

       /* praise = 999;
        mean = 2400;
        negative = 3500000;*/

        long evalSuml = praise+negative+mean;
        if (praise>999){
            String str = FormatterUtil.formatBigNumber(praise);
            radioNice.setText(String.format("好评(%s)", str));
        }else{
            radioNice.setText(new StringBuilder()
                    .append("好评(").append(String.valueOf(praise)).append(")"));
        }

        if (negative>999){
            String str = FormatterUtil.formatBigNumber(negative);
            radioBad.setText(String.format("差评(%s)", str));
        }else{
            radioBad.setText(new StringBuilder()
                    .append("差评(").append(String.valueOf(negative)).append(")"));
        }

        if (mean>999){
            String str = FormatterUtil.formatBigNumber(mean);
            radioMiddle.setText(String.format("中评(%s)", str));
        }else{
            radioMiddle.setText(new StringBuilder()
                    .append("中评(").append(String.valueOf(mean)).append(")"));
        }

        if (evalSuml>999){
            String str = FormatterUtil.formatBigNumber(evalSuml);
            radioAll.setText(String.format("全部(%s)", str));
        }else{
            radioAll.setText(new StringBuilder()
                    .append("全部(").append(String.valueOf(evalSuml)).append(")"));
        }

        /*TODO 用户评价总数*/
        if (evalSuml>999){
            String str = FormatterUtil.formatBigNumber(evalSuml);
            evalSum.setText(String.format("用户评价(%s)", str));
            positiveRate.setVisibility(View.VISIBLE);
            seeAllEval.setVisibility(View.VISIBLE);
        }else if (evalSuml<=0) {
            evalSum.setText("暂无评价");
            positiveRate.setVisibility(View.GONE);
            seeAllEval.setVisibility(View.GONE);
        }else{
            positiveRate.setVisibility(View.VISIBLE);
            seeAllEval.setVisibility(View.VISIBLE);
            evalSum.setText(new StringBuilder()
                    .append("用户评价(").append(String.valueOf(evalSuml)).append(")"));
        }

        /*好评率*/
        String positiveRateStr = data.getFavorablerate();
        FormatterUtil.formatPositiveRate(positiveRateStr,positiveRate);

        /*评价列表*/
        List<ShopEvalBean.ShopEvalItemBean> evals = data.getCommentlist();
        if (evals != null && evals.size() > 0) {
            evalData.clear();
            evalData.addAll(evals);
            evalAdapter.notifyDataSetChanged();
        }

        /*推荐商品*/
        List<GoodsItemBean> goodslist = data.getGoodslist().getData();
        if (goodslist!=null && goodslist.size()>0){
            productData.clear();;
            productData.addAll(goodslist);
            productAdapter.notifyDataSetChanged();
        }
        pager++;

        if (data.getSpec()!=null && data.getSpec().size()>0){
            specify.setText(data.getSpec().get(0).getTitle());
        }

    }

    public void setSpecify(int specifyIndex){
        specify.setText(detailBean.getSpec().get(specifyIndex).getTitle());
    }

    /*改变商品收藏状态*/
    private void updateGoodsCollectState(){
        mRequest04 = getRequest(Params.updateGoodsCollState,true);
        mRequest04.add("goodsid",detailBean.getId());
        CallServer.getRequestInstance().add(getContext(), 0, mRequest04,
                new CustomHttpListener<ActionBean>(getContext(),true, ActionBean.class) {
                    @Override
                    public void doWork(ActionBean result, String code) {
                        try{
                            showtoa(result.getMsg());
                            if (result.getMsg().contains("取消")){
                                collect.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ico_img06,0,0);
                            }else{
                                collect.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ico_img51,0,0);
                            }
                        }catch (Exception e){

                        }
                    }
                },false,true);
    }


    private int pager = 1;
    /*获取更多商品*/
    private void loadData(){
        mRequest01 = getRequest(Params.getGoodsDetail,true);
        mRequest01.add("page",pager);
        mRequest01.add("id",detailBean.getId());
        CallServer.getRequestInstance().add(getContext(), 0, mRequest01,
                new CustomHttpListener<GoodsDetailBean>(getContext(),true,GoodsDetailBean.class) {
                    @Override
                    public void doWork(GoodsDetailBean result, String code) {
                        try{
                             /*推荐商品*/
                            List<GoodsItemBean> goodslist =
                                    result.getData().getGoodslist().getData();
                            if (goodslist!=null && goodslist.size()>0){
                                productData.addAll(goodslist);
                                smart.setNoMoreData(false);
                            }else{
                                smart.setNoMoreData(true);
                            }
                            productAdapter.notifyDataSetChanged();
                            pager++;

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFinally(JSONObject obj, String code, boolean isNetSucceed) {
                        super.onFinally(obj, code, isNetSucceed);
                        smart.finishLoadMore(true);
                        smart.finishRefresh(true);
                    }
                },false,false);

    }


}
