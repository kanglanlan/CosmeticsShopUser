package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Activity.StoresActivity;
import com.meida.cosmeticsshopuser.Bean.ShopItemBean;
import com.meida.cosmeticsshopuser.MyView.FlowLiner;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.meida.cosmeticsshopuser.utils.ProjectUtils;
import com.willy.ratingbar.ScaleRatingBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

/**
 * 作者 亢兰兰
 * 时间 2018/10/11 0011
 * 公司  郑州软盟
 */

public class StoreRecyAdapter extends CommonAdapter<ShopItemBean> {
    private ArrayList<ShopItemBean> datas = new ArrayList<ShopItemBean>();
    Context mContext;

    public StoreRecyAdapter(Context context, int layoutId, ArrayList<ShopItemBean> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        mContext = context;
    }

    @Override
    public void convert(final ViewHolder holder, final ShopItemBean bean, final int position) {
        FlowLiner llbq=holder.getView(R.id.ll_bq);
        ProjectUtils.sortShopLabel(bean.getDistribution1(),bean.getDistribution2(),bean.getDistribution3(),llbq);
        ScaleRatingBar bar=holder.getView(R.id.ratbar01_mc);
        FormatterUtil.setStarRating(bean.getGoods(),bar);
        ImageView img = holder.getView(R.id.img_dianpu);
        GlideUtils.loadShop(bean.getAvatar(),img);
        TextView tv_title = holder.getView(R.id.tv_title);
        tv_title.setText(bean.getTitle());
        TextView starTv = holder.getView(R.id.starTv);
        starTv.setText(FormatterUtil.formatStarValue(bean.getGoods()));
        TextView tv_xiaoliang = holder.getView(R.id.tv_xiaoliang);
        FormatterUtil.formatSaleNum(bean.getSalesvolume(),bean.getSalesvolumea(),tv_xiaoliang);
        TextView tv_time = holder.getView(R.id.tv_time);
        tv_time.setText("");
        TextView tv_juli = holder.getView(R.id.tv_juli);
        FormatterUtil.formatDistance(bean.getJuli(),tv_juli);
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,StoresActivity.class);
                intent.putExtra("id",datas.get(position).getId());
                mContext.startActivity(intent);
            }
        });
    }
}