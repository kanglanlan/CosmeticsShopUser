package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Activity.CommentInfoActivity;
import com.meida.cosmeticsshopuser.Bean.OrderEvalBean;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

/**
 * 作者 亢兰兰
 * 时间 2018/10/11 0011
 * 公司  郑州软盟
 */

public class CommentAdapter extends CommonAdapter<OrderEvalBean.OrderEvalItemBean> {
    private ArrayList<OrderEvalBean.OrderEvalItemBean> datas = new ArrayList<>();
    Context mContext;

    public CommentAdapter(Context context, int layoutId, ArrayList<OrderEvalBean.OrderEvalItemBean> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        mContext = context;
    }

    @Override
    public void convert(final ViewHolder holder, final OrderEvalBean.OrderEvalItemBean bean, int position) {

        ImageView img = holder.getView(R.id.img);
        TextView content = holder.getView(R.id.content);
        TextView type = holder.getView(R.id.type);
        TextView toDetail = holder.getView(R.id.toDetail);

        GlideUtils.loadGoods2(bean.getImgs(),img);
        content.setText(bean.getContent());
        type.setText(bean.getTitle());
        toDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent
                        (mContext, CommentInfoActivity.class).putExtra("id",bean.getId()));
            }
        });

        /*holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

    }
}