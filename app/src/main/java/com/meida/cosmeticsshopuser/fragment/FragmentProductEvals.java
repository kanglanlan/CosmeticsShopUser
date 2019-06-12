package com.meida.cosmeticsshopuser.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.meida.cosmeticsshopuser.Bean.GoodsDetailBean;
import com.meida.cosmeticsshopuser.Bean.ShopEvalBean;
import com.meida.cosmeticsshopuser.adapter.GoodsPJAdapter;
import com.meida.cosmeticsshopuser.base.BaseFragment;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/12/21 0021.
 */

public class FragmentProductEvals extends BaseFragment{

    private SmartRefreshLayout smart;
    private RecyclerView recyclerView;
    private ArrayList<ShopEvalBean.ShopEvalItemBean> data = new ArrayList<>();
    private GoodsPJAdapter adapter;
    private View emptyView;
    private RadioGroup radioGroup;
    private RadioButton radioAll,radioNice,radioMiddle,radioBad;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_eval,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        iniEvent();
        initData();
    }


    private void initView(View rootView){
        if (getArguments()!=null){
            goodsId = getArguments().getString("id");
        }
        smart = rootView.findViewById(R.id.smart);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(lm);
        adapter = new GoodsPJAdapter(getContext(),data);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        emptyView = rootView.findViewById(R.id.empty_view);
        radioGroup = (RadioGroup) rootView.findViewById(R.id.radioGroup);
        radioAll = (RadioButton) rootView.findViewById(R.id.radioAll);
        radioNice = (RadioButton) rootView.findViewById(R.id.radioNice);
        radioMiddle = (RadioButton) rootView.findViewById(R.id.radioMiddle);
        radioBad = (RadioButton) rootView.findViewById(R.id.radioBad);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if (adapter.getItemCount()>0){
                    emptyView.setVisibility(View.GONE);
                }else{
                    emptyView.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private String evalClass = "";
    private void iniEvent(){
        smart.setEnableRefresh(false);
        smart.setEnableLoadMore(true);
        smart.setEnableLoadMoreWhenContentNotFull(true);
        smart.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadData(false,false);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData(true,false);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioAll:
                        evalClass = "";
                        loadData(true,true);
                        break;
                    case R.id.radioNice:
                        evalClass = "1";
                        loadData(true,true);
                        break;
                    case R.id.radioMiddle:
                        evalClass = "2";
                        loadData(true,true);
                        break;
                    case R.id.radioBad:
                        evalClass = "3";
                        loadData(true,true);
                        break;
                }
            }
        });

    }

    private void initData(){

    }

    public static FragmentProductEvals newInstance(String goodsId) {
        Bundle bundle = new Bundle();
        bundle.putString("id",goodsId);
        FragmentProductEvals fragment = new FragmentProductEvals();
        fragment.setArguments(bundle);
        return fragment;
    }

    private String shopId = "";
    public void setShopId(GoodsDetailBean.DataBean detail) {
        this.shopId = detail.getShopid();
        long praise = detail.getPraise();/*好评*/
        long negative = detail.getNegative();/*差评*/
        long mean = detail.getMean();/*中评*/

        /*praise = 999;
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

        loadData(true,false);
    }

    private String goodsId = "";
    private int pager = 1;
    public void loadData(final boolean isRefresh, boolean showLoading){
        if (isRefresh){
            pager = 1;
        }
        mRequest03 = getRequest(Params.getGetGoodsEvalList,false);
        mRequest03.add("id",goodsId);
        mRequest03.add("shopid",shopId);
        mRequest03.add("page",pager);
        mRequest03.add("goods",evalClass);/*1 好评 2  中评 3 差评*/
        CallServer.getRequestInstance().add(getContext(), 0, mRequest03,
                new CustomHttpListener<ShopEvalBean>(getContext(),true,ShopEvalBean.class) {
                    @Override
                    public void doWork(ShopEvalBean result, String code) {
                        try{
                            List<ShopEvalBean.ShopEvalItemBean> beans = result.getData().getData();
                            if (isRefresh){
                                data.clear();
                            }
                            if ( beans!=null && beans.size()>0 ){
                                data.addAll(beans);
                                smart.setNoMoreData(false);
                            }else{
                                smart.setNoMoreData(true);
                            }
                            adapter.notifyDataSetChanged();
                            pager++;

                        }catch (Exception e){

                        }
                    }

                    @Override
                    public void onFinally(JSONObject obj, String code, boolean isNetSucceed) {
                        super.onFinally(obj, code, isNetSucceed);
                        smart.finishRefresh(true);
                        smart.finishLoadMore(true);
                    }
                },false,showLoading);

    }

    public void setSelect(int index){
        switch (index){
            case 0:
                radioAll.setChecked(true);
                break;
            case 1:
                radioNice.setChecked(true);
                break;
            case 2:
                radioMiddle.setChecked(true);
                break;
            case 3:
                radioBad.setChecked(true);
                break;
        }
    }


}
