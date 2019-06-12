package com.meida.cosmeticsshopuser.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Activity.ShopListActivity;
import com.meida.cosmeticsshopuser.Bean.BannerItemBean;
import com.meida.cosmeticsshopuser.Bean.MallData;
import com.meida.cosmeticsshopuser.Bean.ShopItemBean;
import com.meida.cosmeticsshopuser.Bean.TypeBean;
import com.meida.cosmeticsshopuser.Bean.eventbus.LocationSuccess;
import com.meida.cosmeticsshopuser.MyView.CustomGridView;
import com.meida.cosmeticsshopuser.MyView.MyListView;
import com.meida.cosmeticsshopuser.adapter.StoreAdapter;
import com.meida.cosmeticsshopuser.base.App;
import com.meida.cosmeticsshopuser.base.BaseFragment;
import com.meida.cosmeticsshopuser.Activity.FenleiActivity;
import com.meida.cosmeticsshopuser.Activity.MessageActivity;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.Activity.SearchActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.meida.cosmeticsshopuser.utils.ProjectUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

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

/**
 * 作者 亢兰兰
 * 时间 2018/2/24 0024
 * 公司  郑州软盟
 */
@SuppressLint("ValidFragment")
public class Fragment2 extends BaseFragment {

    @Bind(R.id.smart)
    SmartRefreshLayout smart;
    @Bind(R.id.banner)
    BGABanner banner;

    @Bind(R.id.listview_dianpu)
    MyListView listviewDianpu;
    private StoreAdapter shopAdapter;
    private ArrayList<ShopItemBean> shopData = new ArrayList<>();

    @Bind(R.id.gv_mokuai)
    CustomGridView gvMokuai;


    public static Fragment2 instantiation() {
        Fragment2 fragment = new Fragment2();
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
        View view = inflater.inflate(R.layout.fragment2, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setdata();
    }

    private void setdata() {

        ViewGroup.LayoutParams lp = banner.getLayoutParams();
        lp.width = App.wid;
        lp.height = (int)(lp.width*1.0f/750*350);
        banner.setLayoutParams(lp);

        shopAdapter = new StoreAdapter(getActivity(), R.layout.item_dianpu, shopData);
        listviewDianpu.setAdapter(shopAdapter);

        if (Params.aMapLocation != null) {
            loadData();
        }

        smart.setEnableLoadMore(false);
        smart.setEnableRefresh(true);
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.tv_fenlei, R.id.tv_search, R.id.look_dianpu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_fenlei:
                StartActivity(FenleiActivity.class);
                break;
            case R.id.tv_search:
                StartActivity(SearchActivity.class);
                break;
            case R.id.look_dianpu:
                Intent intent = new Intent(getContext(), ShopListActivity.class);
                intent.putExtra("cid", "");
                intent.putExtra("topTitle", "附近店铺");
                startActivity(intent);
                break;
        }
    }

    private void setBanner(List<BannerItemBean> bannerData) {
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

    private void setTypes(List<TypeBean> typeBeans) {
        gvMokuai.setAdapter(new CommonAdapter<TypeBean>(getActivity(),
                R.layout.item_mokuai, typeBeans) {
            @Override
            protected void convert(ViewHolder viewHolder, final TypeBean bean, int position) {
                ImageView img = viewHolder.getView(R.id.img_sy);
                GlideUtils.loadImg(bean.getImg(), img);
                TextView tv_syname = viewHolder.getView(R.id.tv_syname);
                tv_syname.setText(bean.getTitle());

                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), ShopListActivity.class);
                        intent.putExtra("cid", bean.getId());
                        intent.putExtra("topTitle", bean.getTitle());
                        startActivity(intent);
                    }
                });

            }
        });
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
            loadData();
        } else {
            loadData();
        }
    }

    private void loadData() {
        mRequest03 = getRequest(Params.getMallData, true);
        if ( (Params.aMapLocation!=null)){
            mRequest03.add("longitude",Params.aMapLocation.getLongitude());
            mRequest03.add("latitude",Params.aMapLocation.getLatitude());
        }/*else if (Params.locateType==2){
            mRequest03.add("longitude",Params.chooseArea.getLng());
            mRequest03.add("latitude",Params.chooseArea.getLat());
        }*/else{
            mRequest03.add("longitude",Params.LOCATE_LNG);
            mRequest03.add("latitude",Params.LOCATE_LAT);
        }
        CallServer.getRequestInstance().add(getContext(), 0, mRequest03,
                new CustomHttpListener<MallData>(getContext(), true, MallData.class) {
                    @Override
                    public void doWork(MallData data, String code) {
                        try {
                    /*轮播图*/
                            List<BannerItemBean> banners = data.getData().getSowing();
                            if (banners != null) {
                                setBanner(banners);
                            }

                    /*分类数据*/
                            List<TypeBean> types = data.getData().getShopclass().getData();
                            if (types != null) {
                                setTypes(types);
                            }

                    /*店铺数据*/
                            List<ShopItemBean> shops = data.getData().getData();
                            if (shops != null) {
                                shopData.clear();
                                shopData.addAll(shops);
                                shopAdapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFinally(JSONObject obj, String code, boolean isNetSucceed) {
                        super.onFinally(obj, code, isNetSucceed);
                        smart.finishRefresh(true);
                        smart.finishLoadMore(true);
                    }
                }, false, true);

    }




}
