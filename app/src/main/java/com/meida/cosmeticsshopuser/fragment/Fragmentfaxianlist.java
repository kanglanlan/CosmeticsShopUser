package com.meida.cosmeticsshopuser.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.meida.cosmeticsshopuser.Bean.FindListBean;
import com.meida.cosmeticsshopuser.Bean.eventbus.KeyEventBean;
import com.meida.cosmeticsshopuser.Bean.eventbus.UserInfoRefresh;
import com.meida.cosmeticsshopuser.MyView.ClearEditText;
import com.meida.cosmeticsshopuser.adapter.FindAdapter2;
import com.meida.cosmeticsshopuser.base.BaseFragment;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.HttpIp;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者 亢兰兰
 * 时间 2018/2/24 0024
 * 公司  郑州软盟
 */
@SuppressLint("ValidFragment")
public class Fragmentfaxianlist extends BaseFragment {

    @Bind(R.id.editText)
    ClearEditText editText;
    @Bind(R.id.smart)
    SmartRefreshLayout smart;
    @Bind(R.id.recycle_list)
    RecyclerView recycleList;
    @Bind(R.id.empty_view)
    LinearLayout emptyView;
    FindAdapter2 adapter;
    ArrayList<FindListBean.FindItemBean> datas = new ArrayList<>();


    /**
     * @param type 1关注    2发现    3附近
     * @param id   对应的id
     */
    private String type;
    private String id;
    public Fragmentfaxianlist(String type, String id) {
        this.type = type;
        this.id = id;
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
        View view = inflater.inflate(R.layout.fragment_faxianlist, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initview();
        loadData(true,true);
    }


    private void initview() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recycleList.setLayoutManager(linearLayoutManager);
        recycleList.setItemAnimator(new DefaultItemAnimator());
        adapter = new FindAdapter2(getActivity(), R.layout.item_faxianlist, datas);
        //adapter.setHasStableIds(true);
        recycleList.setAdapter(adapter);
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private int pageIndex = 1;
    /*获取关注列表*/
    private void loadData(boolean isRefresh,boolean showLoading){
        if (isRefresh){
            pageIndex = 1;
        }
        mRequest01 = getRequest(Params.getFindList,true);
        mRequest01.add("page",pageIndex);
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
        mRequest01.add("fl1",type);
        mRequest01.add("fl2",id);
        mRequest01.add("keywords",editText.getText().toString().trim());

        CallServer.getRequestInstance().add(getContext(), 0, mRequest01,
                new CustomHttpListener<FindListBean>(getContext(),true,FindListBean.class) {
                    @Override
                    public void doWork(FindListBean result, String code) {
                        try{
                            List<FindListBean.FindItemBean> beans = result.getData().getData();
                            if (pageIndex==1){
                                datas.clear();
                            }
                            if (beans!=null && beans.size()>0){
                                datas.addAll(beans);
                                smart.setNoMoreData(false);
                            }else{
                                smart.setNoMoreData(true);
                            }
                            adapter.notifyDataSetChanged();
                            pageIndex++;

                        }catch (Exception e){

                        }
                    }


                    @Override
                    public void onFinally(JSONObject obj, String code, boolean isNetSucceed) {
                        super.onFinally(obj, code, isNetSucceed);
                        smart.finishLoadMore(true);
                        smart.finishRefresh(true);
                        checkEmpty();
                    }
                },false,showLoading);

    }

    @Override
    public void onResume() {
        super.onResume();
        /*Log.e("onResume","onResume6^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        if (!isHidden()){
            loadData(true,true);
        }*/
    }

    private void checkEmpty(){
        if (adapter.getItemCount()>0){
            recycleList.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }else{
            recycleList.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
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
    public void onEventBus(KeyEventBean refresh){
        if (isVisible() && editText.isFocused()){
            String string = editText.getText().toString().trim();
            if (TextUtils.isEmpty(string)){
                showtoa("请输入关键词");
            }else{
                loadData(true,true);
            }
        }
    }



}
