package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.CollectShopBean;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.Activity.StoresActivity;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.willy.ratingbar.ScaleRatingBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

/**
 * 作者 亢兰兰
 * 时间 2018/10/11 0011
 * 公司  郑州软盟
 */

public class CollectionshopAdapter extends CommonAdapter<CollectShopBean.CollectShop> {

    private ArrayList<CollectShopBean.CollectShop> datas = new ArrayList<>();
    Context mContext;

    public interface OnItemListener{
        void onDelete(ViewHolder holder,CollectShopBean.CollectShop bean);
        void onClickItem(int position,CollectShopBean.CollectShop bean);
    }
    public OnItemListener listener;

    public void setListener(OnItemListener listener) {
        this.listener = listener;
    }


    public CollectionshopAdapter(Context context, int layoutId, ArrayList<CollectShopBean.CollectShop> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        mContext = context;
    }

    @Override
    public void convert(final ViewHolder holder, final CollectShopBean.CollectShop bean, final int position) {

        View contentView = holder.getView(R.id.contentView);
        View delete = holder.getView(R.id.delete);

        ImageView img_dianpu = holder.getView(R.id.img_dianpu);
        TextView tv_title = holder.getView(R.id.tv_title);
        ScaleRatingBar ratbar01_mc = holder.getView(R.id.ratbar01_mc);
        TextView starTv = holder.getView(R.id.starTv);
        TextView tv_xiaoliang = holder.getView(R.id.tv_xiaoliang);
        TextView tv_time = holder.getView(R.id.tv_time);
        TextView tv_juli = holder.getView(R.id.tv_juli);

        GlideUtils.loadShop(bean.getAvatar(),img_dianpu);
        tv_title.setText(bean.getTitle());
        FormatterUtil.setStarRating(bean.getGoods(),ratbar01_mc);

        starTv.setText(FormatterUtil.formatStarValue(bean.getGoods()));
        FormatterUtil.formatSaleNum(bean.getSalesvolume(),bean.getSalesvolumea(),tv_xiaoliang);
        tv_time.setText("");
        FormatterUtil.formatDistance(bean.getJuli(),tv_juli);

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