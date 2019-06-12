package com.meida.cosmeticsshopuser.Activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.meida.cosmeticsshopuser.Bean.ShopEvalBean;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.GoodsPJAdapter;
import com.meida.cosmeticsshopuser.base.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GoodsPingJiaActivity extends BaseActivity {

    @Bind(R.id.recycle_list)
    RecyclerView recycleList;
    @Bind(R.id.empty_view)
    LinearLayout emptyView;
    GoodsPJAdapter adapter;
    private int pager = 1;
    ArrayList<ShopEvalBean.ShopEvalItemBean> datas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_ping_jia);
        ButterKnife.bind(this);
        changeTitle("评价");
        initview();
        getdata(true);
    }

    private void getdata(boolean b) {

    }

    private void initview() {
        linearLayoutManager = new LinearLayoutManager(baseContext);
        recycleList.setLayoutManager(linearLayoutManager);
        recycleList.setItemAnimator(new DefaultItemAnimator());
        adapter = new GoodsPJAdapter(baseContext, datas);
        recycleList.setAdapter(adapter);
    }
}
