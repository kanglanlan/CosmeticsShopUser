package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Activity.GoodsListActivity;
import com.meida.cosmeticsshopuser.Bean.CouponBean;
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

public class CouponsAdapter extends CommonAdapter<CouponBean.CouponItemBean> {
    private ArrayList<CouponBean.CouponItemBean> datas = new ArrayList<>();
    Context mContext;
    private int tabIndex = 1;/* 状态 1可用 2已使用 4过期*/
    private boolean isPick = false;

    public void setPick(boolean pick) {
        isPick = pick;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    public CouponsAdapter(Context context, int layoutId, ArrayList<CouponBean.CouponItemBean> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        mContext = context;
    }

    @Override
    public void convert(final ViewHolder holder, final CouponBean.CouponItemBean bean, int position) {
        LinearLayout llbg = holder.getView(R.id.ll_bg);
        TextView tv_qianbiaoshi = holder.getView(R.id.tv_qianbiaoshi);
        TextView tv_qian = holder.getView(R.id.tv_qian);
        TextView tv_diyong = holder.getView(R.id.tv_diyong);
        TextView tv_title = holder.getView(R.id.tv_title);
        TextView tv_time = holder.getView(R.id.tv_time);
        TextView tv_bqmanjian = holder.getView(R.id.tv_bqmanjian);
        TextView tv_manjian = holder.getView(R.id.tv_manjian);
        final TextView tv_uses = holder.getView(R.id.tv_uses);
        TextView tv_xianzhi = holder.getView(R.id.tv_xianzhi);
        ImageView img_shixiao = holder.getView(R.id.img_shixiao);

        String couponFreeStr = FormatterUtil.sortCouponPrice(bean.getFavourable());
        tv_qian.setText(couponFreeStr);
        tv_title.setText(bean.getTitle());
        tv_time.setText(String.format("有效期至：%s", bean.getEndtime()));
        String limitStr = FormatterUtil.sortCouponPrice(bean.getAmount());

        if (FormatterUtil.StringToDouble(limitStr)>0){
            tv_bqmanjian.setText("满减");
            tv_manjian.setVisibility(View.VISIBLE);
        }else{
            tv_bqmanjian.setText("无门槛");
            tv_manjian.setVisibility(View.INVISIBLE);
        }

        StringBuilder builder = new StringBuilder();
        builder.append("满");
        builder.append(limitStr);
        builder.append("减");
        builder.append(couponFreeStr);
        tv_manjian.setText(builder.toString());

        /*TODO 全店通用*/
        if (TextUtils.isEmpty(bean.getGoods())){
            tv_xianzhi.setText("全店通用");
        }else{
            tv_xianzhi.setText("限指定商品使用");
        }

        String status = bean.getStatus()+"";

        switch (tabIndex+"") {
            case "1"://待使用
                if (isPick){
                    tv_uses.setVisibility(View.GONE);
                }else{
                    tv_uses.setVisibility(View.VISIBLE);
                }
                img_shixiao.setVisibility(View.GONE);
                tv_uses.setText("去使用");
                llbg.setBackgroundResource(R.drawable.ico_img22);
                tv_xianzhi.setTextColor(mContext.getResources().getColor(R.color.black));
                tv_manjian.setTextColor(mContext.getResources().getColor(R.color.couponColor));
                tv_bqmanjian.setTextColor(mContext.getResources().getColor(R.color.white));
                tv_title.setTextColor(mContext.getResources().getColor(R.color.black));
                tv_qianbiaoshi.setTextColor(mContext.getResources().getColor(R.color.white));
                tv_qian.setTextColor(mContext.getResources().getColor(R.color.white));
                tv_diyong.setTextColor(mContext.getResources().getColor(R.color.white));
                tv_bqmanjian.setBackgroundResource(R.drawable.couponbt10);
                break;
            case "2"://已使用
                img_shixiao.setVisibility(View.GONE);
                tv_uses.setText("已使用");
                if (isPick){
                    tv_uses.setVisibility(View.GONE);
                }else{
                    tv_uses.setVisibility(View.VISIBLE);
                }
                tv_bqmanjian.setBackgroundResource(R.drawable.couponbt10);
                llbg.setBackgroundResource(R.drawable.ico_img47);
                tv_xianzhi.setTextColor(mContext.getResources().getColor(R.color.black));
                tv_title.setTextColor(mContext.getResources().getColor(R.color.black));
                tv_manjian.setTextColor(mContext.getResources().getColor(R.color.couponColor));
                tv_bqmanjian.setTextColor(mContext.getResources().getColor(R.color.white));

                tv_qianbiaoshi.setTextColor(mContext.getResources().getColor(R.color.couponColor));
                tv_qian.setTextColor(mContext.getResources().getColor(R.color.couponColor));
                tv_diyong.setTextColor(mContext.getResources().getColor(R.color.couponColor));
                break;
            case "4"://已失效
                img_shixiao.setVisibility(View.VISIBLE);
                tv_uses.setVisibility(View.GONE);
                tv_bqmanjian.setBackgroundResource(R.drawable.huibt10);
                llbg.setBackgroundResource(R.drawable.ico_img47);
                tv_xianzhi.setTextColor(mContext.getResources().getColor(R.color.txthui));
                tv_manjian.setTextColor(mContext.getResources().getColor(R.color.txthui));
                tv_bqmanjian.setTextColor(mContext.getResources().getColor(R.color.txthui));
                tv_title.setTextColor(mContext.getResources().getColor(R.color.txthui));
                tv_qianbiaoshi.setTextColor(mContext.getResources().getColor(R.color.txthui));
                tv_qian.setTextColor(mContext.getResources().getColor(R.color.txthui));
                tv_diyong.setTextColor(mContext.getResources().getColor(R.color.txthui));
                break;
        }

        tv_uses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("去使用".equals(tv_uses.getText().toString().trim())){
                    Intent intent = new Intent(mContext,GoodsListActivity.class);
                    intent.putExtra("couponId",bean.getId());
                    intent.putExtra("shopId",bean.getShopid());
                    mContext.startActivity(intent);
                }
            }
        });


    }
}