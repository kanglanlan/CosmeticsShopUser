package com.meida.cosmeticsshopuser.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Activity.MessageDetailActivity;
import com.meida.cosmeticsshopuser.Activity.NewsListActivity;
import com.meida.cosmeticsshopuser.Activity.ShopListActivity;
import com.meida.cosmeticsshopuser.Bean.BannerItemBean;
import com.meida.cosmeticsshopuser.Bean.GoodsItemBean;
import com.meida.cosmeticsshopuser.Bean.HomeBean;
import com.meida.cosmeticsshopuser.Bean.NewsItemBean;
import com.meida.cosmeticsshopuser.Bean.ShopItemBean;
import com.meida.cosmeticsshopuser.Bean.eventbus.LocationSuccess;
import com.meida.cosmeticsshopuser.MyView.CustomGridView;
import com.meida.cosmeticsshopuser.MyView.MyListView;
import com.meida.cosmeticsshopuser.adapter.GoodsAdapter;
import com.meida.cosmeticsshopuser.adapter.InformationAdapter;
import com.meida.cosmeticsshopuser.adapter.StoreAdapter;
import com.meida.cosmeticsshopuser.base.App;
import com.meida.cosmeticsshopuser.base.BaseFragment;
import com.meida.cosmeticsshopuser.Activity.CitySecActivity;
import com.meida.cosmeticsshopuser.Activity.MessageActivity;
import com.meida.cosmeticsshopuser.Activity.ProductDetailActivity;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.Activity.SearchActivity;
import com.meida.cosmeticsshopuser.Activity.TuiJianActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.DisplayUtil;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.meida.cosmeticsshopuser.utils.ProjectUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;

import static com.meida.cosmeticsshopuser.share.Datas.setdatass;

/**
 * 作者 亢兰兰
 * 时间 2018/2/24 0024
 * 公司  郑州软盟
 */
@SuppressLint("ValidFragment")
public class Fragment1 extends BaseFragment {

    @Bind(R.id.smart)
    SmartRefreshLayout smart;
    @Bind(R.id.tv_city)
    TextView tvCity;
    @Bind(R.id.tv_messagenum)
    TextView tvMessagenum;
    @Bind(R.id.gv_chanpin)
    RecyclerView gvChanpin;
    private List<GoodsItemBean> goodsData = new ArrayList<>();
    private GoodsAdapter goodsAdapter;
    @Bind(R.id.listview)
    RecyclerView listview;
    private InformationAdapter inforadapter;
    private ArrayList<NewsItemBean> newsData = new ArrayList<>();
    @Bind(R.id.newsParent)
    LinearLayout newsParent;
    @Bind(R.id.listview_dianpu)
    MyListView listviewDianpu;
    private StoreAdapter shopAdapter;
    private ArrayList<ShopItemBean> shopData = new ArrayList<>();
    @Bind(R.id.banner)
    BGABanner banner;
    private List<BannerItemBean> bannerData = new ArrayList<>();

    public static Fragment1 instantiation() {
        Fragment1 fragment = new Fragment1();
        return fragment;
    }

    //调用这个方法切换时不会释放掉Fragment
    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null)
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment1, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setdatass();
        setdata();
    }


    private void setdata() {

        /*450 * 180*/
        ViewGroup.LayoutParams lp = banner.getLayoutParams();
        lp.width = App.wid;
        lp.height = (int)(lp.width*1.0f/750*350);
        banner.setLayoutParams(lp);

        goodsAdapter = new GoodsAdapter(getContext(), R.layout.item_chanpin, goodsData);
        gvChanpin.setNestedScrollingEnabled(false);
        gvChanpin.setLayoutManager(new GridLayoutManager(getContext(),2));
        gvChanpin.setAdapter(goodsAdapter);

        LinearLayoutManager lmr = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        lmr.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(lmr);
        listview.setNestedScrollingEnabled(false);
        inforadapter = new InformationAdapter(getActivity(), R.layout.item_zixun, newsData);
        inforadapter.setShowDivider(false);
        listview.setAdapter(inforadapter);
        shopAdapter = new StoreAdapter(getActivity(), R.layout.item_dianpu, shopData);
        listviewDianpu.setAdapter(shopAdapter);
        smart.setEnableLoadMore(false);
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getHomeData(false);
            }
        });
    }

    private void setBanner() {

        banner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View itemView, @Nullable Object model, int position) {
                ImageView imageView = itemView.findViewById(R.id.img);
                final BannerItemBean bean = (BannerItemBean) model;
                GlideUtils.loadBanner(bean.getImg(), imageView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ProjectUtils.sortBannerClick(getContext(), bean);
                    }
                });
            }
        });
        banner.setData(R.layout.item_img_h_140, bannerData, null);
        if (bannerData.size()>1){
            banner.startAutoPlay();
        }else{
            banner.stopAutoPlay();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_city, R.id.look_dianpu, R.id.rl_message, R.id.tv_search, R.id.look_chanpin, R.id.look_zixun})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.look_dianpu:
                Intent intent = new Intent(getContext(), ShopListActivity.class);
                intent.putExtra("cid", "");
                intent.putExtra("topTitle", "附近店铺");
                startActivity(intent);
                break;
            case R.id.tv_city:
                StartActivity(CitySecActivity.class);
                break;
            case R.id.rl_message:
                if (needLogin()) {
                    return;
                }
                StartActivity(MessageActivity.class);
                break;
            case R.id.tv_search:
                StartActivity(SearchActivity.class);
                break;
            case R.id.look_chanpin:
                StartActivity(TuiJianActivity.class);
                break;
            case R.id.look_zixun:
                StartActivity(NewsListActivity.class);
                break;
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
    public void onEventBus(LocationSuccess refresh) {
        if (refresh.getaMapLocation() == null) {
            if (refresh.locateType==1){
                if (Params.aMapLocation==null){
                    tvCity.setText("定位失败");
                }else{
                    tvCity.setText(Params.aMapLocation.getCity());
                }
            }else if (refresh.locateType==2){
                tvCity.setText(Params.chooseArea.getName());
            }else{
                tvCity.setText("定位失败");
            }
            getHomeData();
        } else {
            tvCity.setText(refresh.getaMapLocation().getCity());
            Params.aMapLocation = refresh.getaMapLocation();
            getHomeData(false);
        }
    }

    /*获取首页信息*/
    private void getHomeData(){
        getHomeData(true);
    }
    private void getHomeData(boolean showLoading) {
        mRequest01 = getRequest(Params.getHomeData, true);
        if ((Params.aMapLocation!=null)){
            mRequest01.add("longitude",Params.aMapLocation.getLongitude());
            mRequest01.add("latitude",Params.aMapLocation.getLatitude());
        }/*else if (Params.locateType==2){
            mRequest01.add("longitude",Params.chooseArea.getLng());
            mRequest01.add("latitude",Params.chooseArea.getLat());
        }*/else{
            mRequest01.add("longitude",Params.LOCATE_LNG);
            mRequest01.add("latitude",Params.LOCATE_LAT);
        }
        CallServer.getRequestInstance().add(getContext(), 0, mRequest01,
                new CustomHttpListener<HomeBean>(getContext(), true, HomeBean.class) {
                    @Override
                    public void doWork(HomeBean data, String code) {
                        try {
                            /*店铺数据*/
                            List<ShopItemBean> shops = data.getData().getData();
                            if (shops != null) {
                                shopData.clear();
                                shopData.addAll(shops);
                                shopAdapter.notifyDataSetChanged();
                            }

                            /*商品数据*/
                            List<GoodsItemBean> goods = data.getData().getGoodsdata().getData();
                            if (goods != null) {
                                goodsData.clear();
                                goodsData.addAll(goods);
                                goodsAdapter.notifyDataSetChanged();
                            }

                            /*资讯*/
                            List<NewsItemBean> news = data.getData().getNews().getData();
                            if (news != null) {
                                newsData.clear();
                                if (news.size()>0){
                                    newsData.add(news.get(0));
                                }
                                inforadapter.notifyDataSetChanged();
                                addNews(news);
                            }

                            /*轮播图*/
                            List<BannerItemBean> banners = data.getData().getSowing();
                            if (banners != null) {
                                bannerData.clear();
                                bannerData.addAll(banners);
                                setBanner();
                            }


                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onFinally(JSONObject obj, String code, boolean isNetSucceed) {
                        super.onFinally(obj, code, isNetSucceed);
                        smart.finishLoadMore(true);
                        smart.finishRefresh(true);
                    }
                }, false, showLoading);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (isVisible()){
            ProjectUtils.updateMsgNum(getContext(),tvMessagenum);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ProjectUtils.updateMsgNum(getContext(),tvMessagenum);
    }

    private void addNews(final List<NewsItemBean> news){
        if (news!=null && news.size()>1){
            newsParent.removeAllViews();
            for (int i = 1; i<news.size(); i++){
                TextView textView = new TextView(getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(DisplayUtil.dp2px(10),0,DisplayUtil.dp2px(10),DisplayUtil.dp2px(5));
                textView.setLayoutParams(lp);
                textView.setTextColor(Color.parseColor("#999999"));
                textView.setTextSize(13);
                newsParent.addView(textView);
                textView.setText(news.get(i).getPost_title());
                final int index = i;
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), MessageDetailActivity.class);
                        intent.putExtra("id",news.get(index).getId());
                        intent.putExtra("ip", Params.getNewsDetail);
                        startActivity(intent);
                    }
                });

            }
        }
    }



}
