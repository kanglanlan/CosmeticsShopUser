package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Activity.ApplyReturnActivity;
import com.meida.cosmeticsshopuser.Activity.GoodsReturnDetailActivity;
import com.meida.cosmeticsshopuser.Activity.ProductDetailActivity;
import com.meida.cosmeticsshopuser.Activity.StoresActivity;
import com.meida.cosmeticsshopuser.Bean.OrderReturnAbleBean;
import com.meida.cosmeticsshopuser.Bean.ReturnHisListBean;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2019/1/29 0029.
 */

public class GoodsReturnHisAdapter extends CommonAdapter<ReturnHisListBean.Item>{

    public GoodsReturnHisAdapter(Context context, int layoutId, List<ReturnHisListBean.Item> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, final ReturnHisListBean.Item bean, int position) {

        TextView shopName = holder.getView(R.id.shopName);
        TextView orderStatus = holder.getView(R.id.orderStatus);

        String status = bean.getStatus();
        if ("1".equals(status)){
            orderStatus.setText("退货中");
        }else if ("2".equals(status)){
            orderStatus.setText("退货完成");
        }else{
            orderStatus.setText("");
        }

        LinearLayout goodsParent = holder.getView(R.id.goodsParent);
        shopName.setText(bean.getShoptitle());
        addHisChild(goodsParent,bean);

        shopName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, StoresActivity.class);
                intent.putExtra("id",bean.getShopid());
                mContext.startActivity(intent);
            }
        });


    }

    /*申请历史 添加 子view*/
    private void addHisChild(LinearLayout goodsParent, final ReturnHisListBean.Item bean){

        goodsParent.removeAllViews();

        View childView = LayoutInflater.from(mContext).inflate(R.layout.item_ziorder_return_his,null);
        ImageView img = childView.findViewById(R.id.img);
        TextView name = childView.findViewById(R.id.name);
        TextView num = childView.findViewById(R.id.num);
        TextView money = childView.findViewById(R.id.money);

        GlideUtils.loadGoods2(bean.getImgs(),img);
        name.setText(bean.getTitle());
        num.setText(String.format("x%s", bean.getNum()));
        money.setText(bean.getPrice());

        childView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, GoodsReturnDetailActivity.class);
                intent.putExtra("id",bean.getId());
                mContext.startActivity(intent);
            }
        });

        /*childView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                //intent.putExtra("id",bean.get)
                mContext.startActivity(intent);
            }
        });*/

        goodsParent.addView(childView);



    }






}
