package com.meida.cosmeticsshopuser.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.ShopTypeBean;
import com.meida.cosmeticsshopuser.Bean.TypeBean;
import com.meida.cosmeticsshopuser.MyView.CustomGridView;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.FenLeileftAdapter;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FenleiActivity extends BaseActivity {


    @Bind(R.id.listview)
    ListView listview;
    FenLeileftAdapter leftAdapter;

    @Bind(R.id.gv_fl)
    CustomGridView gvFl;
    @Bind(R.id.empty_view)
    View emptyView;
    private RightAdapter rightAdapter;

    private ArrayList<ShopTypeBean.Bean> data = new ArrayList<>();
    private ArrayList<TypeBean> rightData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fenlei);
        ButterKnife.bind(this);
        changeTitle("店铺分类");
        leftAdapter = new FenLeileftAdapter(baseContext, R.layout.item_fenleileft, data);
        leftAdapter.setSelectIndex(0);
        leftAdapter.setListener(new FenLeileftAdapter.OnCheckChangeListener() {
            @Override
            public void onCheckChange(int position) {
                rightData.clear();
                rightData.addAll(data.get(position).getChild().getData());
                rightAdapter.notifyDataSetChanged();
                checkEmpty();
            }
        });
        listview.setAdapter(leftAdapter);
        rightAdapter = new RightAdapter(baseContext,R.layout.item_fenlei,rightData);
        gvFl.setAdapter(rightAdapter);
        getData();

    }

    private void getData(){
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
                                data.clear();
                                data.addAll(beans);
                            }
                            leftAdapter.notifyDataSetChanged();
                            rightData.clear();
                            rightData.addAll(data.get(0).getChild().getData());
                            rightAdapter.notifyDataSetChanged();
                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onFinally(JSONObject obj, String code, boolean isNetSucceed) {
                        super.onFinally(obj, code, isNetSucceed);
                        checkEmpty();
                    }
                }, false, true);

    }

    /*R.layout.item_fenlei*/

    public class RightAdapter extends CommonAdapter<TypeBean>{


        public RightAdapter(Context context, int layoutId, List<TypeBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder viewHolder, final TypeBean item, int position) {
            TextView name = viewHolder.getView(R.id.tv_fenleianme);
            ImageView img = viewHolder.getView(R.id.img_fenlei);
            name.setText(item.getTitle());
            GlideUtils.loadImg(item.getImg(),img);
            viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(baseContext, ShopListActivity.class);
                    intent.putExtra("categoryid",item.getId());
                    intent.putExtra("topTitle",item.getTitle());
                    startActivity(intent);
                }
            });

        }

    }

    private void checkEmpty(){
        if (rightAdapter.getCount()>0){
            emptyView.setVisibility(View.GONE);
            gvFl.setVisibility(View.VISIBLE);
        }else{
            emptyView.setVisibility(View.VISIBLE);
            gvFl.setVisibility(View.GONE);
        }
    }


}
