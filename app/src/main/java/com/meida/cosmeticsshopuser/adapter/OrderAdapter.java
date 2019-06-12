package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Activity.OrderDetailActivity;
import com.meida.cosmeticsshopuser.Activity.StoresActivity;
import com.meida.cosmeticsshopuser.Bean.OrderBean;
import com.meida.cosmeticsshopuser.Activity.OrderInfoActivity;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.Activity.WritCommentActivity;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.meida.cosmeticsshopuser.utils.TypeUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

/**
 * 作者 亢兰兰
 * 时间 2018/10/11 0011
 * 公司  郑州软盟
 */

public class OrderAdapter extends CommonAdapter<OrderBean.OrderItemBean> {
    private ArrayList<OrderBean.OrderItemBean> datas = new ArrayList<>();
    Context mContext;

    public OrderAdapter(Context context, int layoutId, ArrayList<OrderBean.OrderItemBean> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        mContext = context;
    }

    public interface OnActionListener{
        void onAcition(int actionType,int position,OrderBean.OrderItemBean bean);
        void onContactShop(int position,OrderBean.OrderItemBean bean);
    }

    private OnActionListener listener;

    public void setListener(OnActionListener listener) {
        this.listener = listener;
    }

    /*; 1:待支付 2:待发货 3:待收货 4:已收货 5:已评价       9:关闭  99:删除  12 凭条派单
    * 状态; 1:待支付 2:待发货 3:待收货 4:已收货 5:已评价     6退货申请 7已退货 8（ 4:已收货 5:已评价）
    * */
    @Override
    public void convert(final ViewHolder holder, final OrderBean.OrderItemBean bean, final int position) {

        TextView shopName = (TextView) holder.getView(R.id.shopName);
        TextView orderStatus = (TextView) holder.getView(R.id.orderStatus);
        LinearLayout goodsParent = (LinearLayout) holder.getView(R.id.goodsParent);
        TextView deliveryMode = (TextView) holder.getView(R.id.deliveryMode);
        TextView orderSummery = (TextView) holder.getView(R.id.orderSummery);
        /*action*/
        View contactShop = holder.getView(R.id.contactShop);
        View cancelOrder = holder.getView(R.id.cancelOrder);
        View remindSeller = holder.getView(R.id.remindSeller);
        View confirmOrder = holder.getView(R.id.confirmOrder);
        View payOrder = holder.getView(R.id.payOrder);
        View evalOrder = holder.getView(R.id.evalOrder);
        View deleteOrder = holder.getView(R.id.deleteOrder);

        TypeUtil.OrderType orderType = TypeUtil.getOrderType(bean.getStatus());
        shopName.setText(bean.getTitle());
        orderStatus.setText(TypeUtil.getOrderStatusStr(bean.getStatus()));
        deliveryMode.setText(TypeUtil.getDeliveryModeStr(bean.getDistribution()));

        if (bean.getItem()!=null && bean.getItem().size()>0){
            goodsParent.removeAllViews();
            for (int i = 0; i<bean.getItem().size(); i++){
                addGoodsItem(bean.getItem().get(i),goodsParent);
            }
        }

        StringBuilder builder = new StringBuilder();
        builder.append("共");
        builder.append(bean.getGoodsnum());
        builder.append("件商品  实付款：¥");
        builder.append(bean.getTotal());
        orderSummery.setText(builder.toString());

        contactShop.setVisibility(View.GONE);
        cancelOrder.setVisibility(View.GONE);
        remindSeller.setVisibility(View.GONE);
        confirmOrder.setVisibility(View.GONE);
        payOrder.setVisibility(View.GONE);
        evalOrder.setVisibility(View.GONE);
        deleteOrder.setVisibility(View.GONE);

        if (orderType!=null){
            switch (orderType){
                case DZF:
                    contactShop.setVisibility(View.VISIBLE);
                    cancelOrder.setVisibility(View.VISIBLE);
                    payOrder.setVisibility(View.VISIBLE);
                    break;
                case DFH:
                    contactShop.setVisibility(View.VISIBLE);
                    remindSeller.setVisibility(View.VISIBLE);
                    break;
                case DSH:
                    contactShop.setVisibility(View.VISIBLE);
                    confirmOrder.setVisibility(View.VISIBLE);
                    break;
                case YSH:
                    evalOrder.setVisibility(View.VISIBLE);
                    break;
                case YPJ:
                    deleteOrder.setVisibility(View.VISIBLE);
                    break;
                case GB:
                    deleteOrder.setVisibility(View.VISIBLE);
                    break;
                case SC:
                    deleteOrder.setVisibility(View.VISIBLE);
                    break;
                case THZ:
                    contactShop.setVisibility(View.VISIBLE);
                    break;
                case THWC:
                    //deleteOrder.setVisibility(View.VISIBLE);
                    break;
            }
        }

        contactShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onContactShop(position,bean);
                }
            }
        });

        /*1取消订单  2发货提醒  3确认收货*/
        cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onAcition(1,position,bean);
                }
            }
        });

        remindSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onAcition(2,position,bean);
                }
            }
        });

        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onAcition(3,position,bean);
                }
            }
        });

        deleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null ){
                    listener.onAcition(4,position,bean);
                }
            }
        });


        evalOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, WritCommentActivity.class)
                        .putExtra("id",bean.getId())
                        .putExtra("img",bean.getAvatar())
                        .putExtra("star",bean.getGoods())
                );
            }
        });

        payOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,OrderInfoActivity.class);
                intent.putExtra("id",bean.getId());
                mContext.startActivity(intent);
            }
        });

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TypeUtil.getOrderType(bean.getStatus()).equals(TypeUtil.OrderType.DZF)){
                    Intent intent = new Intent(mContext,OrderInfoActivity.class);
                    intent.putExtra("id",bean.getId());
                    mContext.startActivity(intent);
                }else{
                    Intent intent = new Intent(mContext,OrderDetailActivity.class);
                    intent.putExtra("star",bean.getGoods());
                    intent.putExtra("id",bean.getId());
                    intent.putExtra("shopImg",bean.getAvatar());
                    mContext.startActivity(intent);
                }
            }
        });

        shopName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, StoresActivity.class);
                intent.putExtra("id",bean.getShopid());
                mContext.startActivity(intent);
            }
        });


    }


    private void addGoodsItem(final OrderBean.GoodsItemBean bean,LinearLayout goodsParent){

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_ziorder,null);
        ImageView img = view.findViewById(R.id.img);
        TextView name = view.findViewById(R.id.name);
        TextView money = view.findViewById(R.id.money);
        TextView num = view.findViewById(R.id.num);

        GlideUtils.loadGoods2(bean.getImgs(),img);
        name.setText(bean.getTitle());
        FormatterUtil.formatPrice2(bean.getPrice(),money);
        num.setText("x"+bean.getNum());
        goodsParent.addView(view);

    }







}