package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Activity.ProductDetailActivity;
import com.meida.cosmeticsshopuser.Bean.GoodsItemBean;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.meida.cosmeticsshopuser.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 亢兰兰
 * 时间 2018/10/11 0011
 * 公司  郑州软盟
 */
/*R.layout.item_shangpin*/

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    public static final int SPAN_ONE = 1;
    public static final int SPAN_TWO = 2;

    private List<GoodsItemBean> datas = new ArrayList<>();
    Context mContext;
    private int span_size = 2;

    private int lId = R.layout.item_shangpin;
    private boolean hasLayoutId = false;

    public ProductAdapter(Context mContext,List<GoodsItemBean> datas,  int span_size) {
        this.datas = datas;
        this.mContext = mContext;
        this.span_size = span_size;
    }

    public ProductAdapter(Context mContext, int layoutId, List<GoodsItemBean> datas) {
        this.datas = datas;
        this.mContext = mContext;
        this.span_size = 2;
        this.lId = layoutId;

    }

    public void setSpan_size(int span_size) {
        this.span_size = span_size;
    }

    @Override
    public int getItemViewType(int position) {
        return span_size;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId;
        if (hasLayoutId){
            layoutId = lId;
        }else{
            layoutId = viewType==2?R.layout.item_shangpin:R.layout.item_shangpin_2;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final  int pos) {
        final int position = holder.getAdapterPosition();
        try{
            GlideUtils.loadGoods2(datas.get(position).getImgs(),holder.img_goods);
            holder.tv_title.setText(datas.get(position).getTitle());
            FormatterUtil.formatPrice2(datas.get(position).getPrice(),holder.tv_qian);
            FormatterUtil.formatSaleNum2
                    (datas.get(position).getSalesvolume(),datas.get(position).getSalesvolumea(),holder.tv_xiaoliang);
            FormatterUtil.formatPositiveRate(datas.get(position).getFavorablerate(),holder.tv_haoping);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ProductDetailActivity.class);
                    intent.putExtra("id",datas.get(position).getId());
                    mContext.startActivity(intent);
                }
            });

        }catch (Exception e){

        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView img_goods;
        TextView tv_title,tv_qian,tv_xiaoliang,tv_haoping;

        public ViewHolder(View itemView) {
            super(itemView);
            img_goods = itemView.findViewById(R.id.img_goods);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_qian = itemView.findViewById(R.id.tv_qian);
            tv_xiaoliang = itemView.findViewById(R.id.tv_xiaoliang);
            tv_haoping = itemView.findViewById(R.id.tv_haoping);
        }

    }


}