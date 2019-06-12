package com.meida.cosmeticsshopuser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.meida.cosmeticsshopuser.Bean.ActionBean;
import com.meida.cosmeticsshopuser.Bean.AddrBean;
import com.meida.cosmeticsshopuser.Bean.eventbus.EditAddrBean;
import com.meida.cosmeticsshopuser.MyView.dialog.ActionDialog;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.AddAdapter;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyAddActivity extends BaseActivity {

    private boolean isForPick = false;

    @Bind(R.id.recycle_list)
    RecyclerView recycleList;
    @Bind(R.id.empty_view)
    LinearLayout emptyView;
    AddAdapter adapter;
    ArrayList<AddrBean.AddrItemBean> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_add);
        ButterKnife.bind(this);
        isForPick = getIntent().getBooleanExtra("pick",false);
        changeTitle("地址管理");
        initview();
    }


    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void initview() {
        linearLayoutManager = new LinearLayoutManager(baseContext);
        recycleList.setLayoutManager(linearLayoutManager);
        recycleList.setItemAnimator(new DefaultItemAnimator());
        adapter = new AddAdapter(baseContext, R.layout.item_add, datas);
        recycleList.setAdapter(adapter);

        adapter.setListener(new AddAdapter.OnItemActionListener() {
            @Override
            public void onItemClick(int position, AddrBean.AddrItemBean bean) {
                if (isForPick){
                    Intent intent = new Intent();
                    intent.putExtra("data",bean);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }

            @Override
            public void onDelete(ViewHolder holder, AddrBean.AddrItemBean bean) {
                deleteAddr(holder,bean);
            }

            @Override
            public void onEdit(int position, AddrBean.AddrItemBean bean) {
                Intent intent = new Intent(baseContext,NewAddActivity.class);
                intent.putExtra("data",bean);
                startActivity(intent);
            }

            @Override
            public void setDefault(int position, AddrBean.AddrItemBean bean) {
                setDefaultAddr(position,bean);
            }
        });

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(baseContext,NewAddActivity.class);
                startActivity(intent);
            }
        });



    }

    /*获取收货地址列表*/
    private void getData(){
        mRequest01 = getRequest(Params.getAddrList,true);
        mRequest01.add("default","");
        datas.clear();
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<AddrBean>(baseContext,true,AddrBean.class) {
                    @Override
                    public void doWork(AddrBean result, String code) {
                        try{
                            List<AddrBean.AddrItemBean> beans = result.getData();
                            if (beans!=null && beans.size()>0){
                                datas.addAll(beans);
                            }

                        }catch (Exception e){

                        }
                    }

                    @Override
                    public void onFinally(JSONObject obj, String code, boolean isNetSucceed) {
                        super.onFinally(obj, code, isNetSucceed);
                        checkEmpty();
                        adapter.notifyDataSetChanged();
                    }

                },false,true);
    }

    /*删除地址*/
    private void deleteAddr(final ViewHolder holder, final AddrBean.AddrItemBean bean){
        ActionDialog deleteDialog = new ActionDialog(baseContext,"确认删除？");
        deleteDialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
                mRequest02 = getRequest(Params.deleteAddr,true);
                mRequest02.add("id",bean.getId());
                CallServer.getRequestInstance().add(baseContext, 0, mRequest02,
                        new CustomHttpListener<ActionBean>(baseContext,true,ActionBean.class) {
                            @Override
                            public void doWork(ActionBean result, String code) {
                                showtoa(result.getMsg());
                                getData();
                                EventBus.getDefault().post(new EditAddrBean(true,bean.getId(),null));
                            }
                        },false,true);
            }
        });
        deleteDialog.show();
    }

    /*设置默认地址*/
    private void setDefaultAddr(int position, AddrBean.AddrItemBean bean){
        mRequest03 = getRequest(Params.setDefaultAddr,true);
        mRequest03.add("id",bean.getId());
        CallServer.getRequestInstance().add(baseContext, 0, mRequest03,
                new CustomHttpListener<ActionBean>(baseContext,true,ActionBean.class) {
                    @Override
                    public void doWork(ActionBean result, String code) {
                        showtoa(result.getMsg());
                        getData();
                    }
                },false,false);
    }


    private void checkEmpty(){
        if (adapter!=null && adapter.getItemCount()>0){
            recycleList.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }else{
            recycleList.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
    }



}
