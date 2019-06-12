package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.CollectGoodsBean;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/12/20 0020.
 */

public class CollectGoodsAdapter extends CommonAdapter<CollectGoodsBean.CollectGoodsItemBean>{

    private Context context;
    private List<CollectGoodsBean.CollectGoodsItemBean> data = new ArrayList<>();

    public interface OnItemListener{
        void onDelete(ViewHolder holder,CollectGoodsBean.CollectGoodsItemBean bean);
        void onClickItem(int position,CollectGoodsBean.CollectGoodsItemBean bean);
    }
    public OnItemListener listener;

    public void setListener(OnItemListener listener) {
        this.listener = listener;
    }

    public CollectGoodsAdapter(Context context, int layoutId, List<CollectGoodsBean.CollectGoodsItemBean> datas) {
        super(context, layoutId, datas);
        this.context = context;
        this.data = datas;
    }

    @Override
    protected void convert(final ViewHolder holder, final CollectGoodsBean.CollectGoodsItemBean bean, final int position) {
        ImageView img = holder.getView(R.id.img);
        TextView proName = holder.getView(R.id.proName);
        TextView price = holder.getView(R.id.price);
        TextView saleNum = holder.getView(R.id.saleNum);
        TextView positiveRate = holder.getView(R.id.positiveRate);
        View contentView = holder.getView(R.id.contentView);
        View delete = holder.getView(R.id.delete);

        GlideUtils.loadGoods2(bean.getImgs(),img);
        proName.setText(bean.getTitle());
        FormatterUtil.formatPrice(bean.getPrice(),price);
        FormatterUtil.formatSaleNum2(bean.getSalesvolume(),bean.getSalesvolumea(),saleNum);
        FormatterUtil.formatPositiveRate(bean.getFavorablerate(),positiveRate);

        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onClickItem(position,bean);
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onDelete(holder,bean);
                }
            }
        });


    }



}
