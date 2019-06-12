package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.FractionRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Activity.GoodsListActivity;
import com.meida.cosmeticsshopuser.Bean.AddrBean;
import com.meida.cosmeticsshopuser.Bean.ShopCouponBean;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

/**
 * 作者 亢兰兰
 * 时间 2018/10/11 0011
 * 公司  郑州软盟
 */

public class ShopCouponAdapter extends CommonAdapter<ShopCouponBean.ItemBean> {
    private ArrayList<ShopCouponBean.ItemBean> datas = new ArrayList<>();
    Context mContext;

    public ShopCouponAdapter(Context context, int layoutId, ArrayList<ShopCouponBean.ItemBean> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        mContext = context;
    }

    public interface OnItemActionListener{
        void onPick(int position, ShopCouponBean.ItemBean bean);
        void onUseNow(int position, ShopCouponBean.ItemBean bean);
    }

    private OnItemActionListener listener;
    public void setListener(OnItemActionListener listener) {
        this.listener = listener;
    }

    @Override
    public void convert(final ViewHolder holder, final ShopCouponBean.ItemBean bean, final int position) {

        View ll_bg = holder.getView(R.id.ll_bg);
        TextView tv_qianbiaoshi = holder.getView(R.id.tv_qianbiaoshi);
        TextView freeMoney = holder.getView(R.id.freeMoney);
        TextView title = holder.getView(R.id.title);
        TextView time = holder.getView(R.id.time);
        View pick = holder.getView(R.id.pick);
        View hasPick = holder.getView(R.id.hasPick);
        View useNow = holder.getView(R.id.useNow);
        View hasUse = holder.getView(R.id.hasUse);

        freeMoney.setText(FormatterUtil.sortCouponPrice(bean.getFavourable()));
        String limitStr = FormatterUtil.sortCouponPrice(bean.getAmount());
        StringBuilder builder = new StringBuilder();
        builder.append("满");
        builder.append(limitStr);
        builder.append("元可用");
        title.setText(builder.toString());
        time.setText("有效期至："+bean.getEndtime());

        /*TODO 状态 1可用  2已使用 3删除  0未领取*/
        String status = bean.getUserstauus();
        if ("1".equals(status)){
            hasUse.setVisibility(View.GONE);
            useNow.setVisibility(View.VISIBLE);
            pick.setVisibility(View.GONE);
            hasPick.setVisibility(View.VISIBLE);
            tv_qianbiaoshi.setTextColor(mContext.getResources().getColor(R.color.couponColor));
            freeMoney.setTextColor(mContext.getResources().getColor(R.color.couponColor));
            ll_bg.setBackgroundResource(R.drawable.ico_img47);
        }else if ("2".equals(status)){
            hasUse.setVisibility(View.VISIBLE);
            useNow.setVisibility(View.GONE);
            pick.setVisibility(View.GONE);
            hasPick.setVisibility(View.VISIBLE);
            tv_qianbiaoshi.setTextColor(mContext.getResources().getColor(R.color.couponColor));
            freeMoney.setTextColor(mContext.getResources().getColor(R.color.couponColor));
            ll_bg.setBackgroundResource(R.drawable.ico_img47);
        }else if ("3".equals(status)){
            hasUse.setVisibility(View.GONE);
            useNow.setVisibility(View.GONE);
            pick.setVisibility(View.GONE);
            hasPick.setVisibility(View.GONE);
            tv_qianbiaoshi.setTextColor(mContext.getResources().getColor(R.color.couponColor));
            freeMoney.setTextColor(mContext.getResources().getColor(R.color.couponColor));
            ll_bg.setBackgroundResource(R.drawable.ico_img47);
        }else if ("0".equals(status)){
            hasUse.setVisibility(View.GONE);
            useNow.setVisibility(View.GONE);
            pick.setVisibility(View.VISIBLE);
            hasPick.setVisibility(View.GONE);
            tv_qianbiaoshi.setTextColor(mContext.getResources().getColor(R.color.white));
            freeMoney.setTextColor(mContext.getResources().getColor(R.color.white));
            ll_bg.setBackgroundResource(R.drawable.ico_img22);
        }else{
            useNow.setVisibility(View.GONE);
            pick.setVisibility(View.GONE);
            hasPick.setVisibility(View.GONE);
            tv_qianbiaoshi.setTextColor(mContext.getResources().getColor(R.color.couponColor));
            freeMoney.setTextColor(mContext.getResources().getColor(R.color.couponColor));
            ll_bg.setBackgroundResource(R.drawable.ico_img47);
        }


        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onPick(position,bean);
                }
            }
        });

        useNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onUseNow(position,bean);
                }
            }
        });


    }



}