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
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2019/1/29 0029.
 */

public class GoodsReturnAdapter extends CommonAdapter<OrderReturnAbleBean.Shop>{

    public GoodsReturnAdapter(Context context, int layoutId, List<OrderReturnAbleBean.Shop> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, final OrderReturnAbleBean.Shop bean, int position) {

        TextView shopName = holder.getView(R.id.shopName);
        TextView orderStatus = holder.getView(R.id.orderStatus);
        LinearLayout goodsParent = holder.getView(R.id.goodsParent);
        shopName.setText(bean.getShoptitle());
        orderStatus.setText("");
        addChild(goodsParent,bean.getItem());

        shopName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, StoresActivity.class);
                intent.putExtra("id",bean.getShopid());
                mContext.startActivity(intent);
            }
        });

    }


    /*预申请 添加 子view*/
    private void addChild(LinearLayout goodsParent,List<OrderReturnAbleBean.Goods> goodsData){

        goodsParent.removeAllViews();

        for (int i = 0;i<goodsData.size();i++){
            final OrderReturnAbleBean.Goods bean = goodsData.get(i);
            View childView = LayoutInflater.from(mContext).inflate(R.layout.item_ziorder_return,null);
            ImageView img = childView.findViewById(R.id.img);
            TextView name = childView.findViewById(R.id.name);
            TextView num = childView.findViewById(R.id.num);

            GlideUtils.loadGoods2(bean.getImgs(),img);
            name.setText(bean.getTitle());
            num.setText(String.format("x%s", bean.getNum()));

            View applyReturn = childView.findViewById(R.id.applyReturn);

            applyReturn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ApplyReturnActivity.class);
                    intent.putExtra("id",bean.getId());
                    mContext.startActivity(intent);
                }
            });

            childView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*Intent intent = new Intent(mContext, ProductDetailActivity.class);
                    intent.putExtra("id",bean.getGoodsid());
                    mContext.startActivity(intent);*/

                    Intent intent = new Intent(mContext, ApplyReturnActivity.class);
                    intent.putExtra("id",bean.getId());
                    mContext.startActivity(intent);

                }
            });

            goodsParent.addView(childView);

        }



    }




}
