package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.MessageNewsBean;
import com.meida.cosmeticsshopuser.Activity.MessageDetailActivity;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

/**
 * 作者 亢兰兰
 * 时间 2018/10/11 0011
 * 公司  郑州软盟
 */

public class PingTaiMessageAdapter extends CommonAdapter<MessageNewsBean.MessageNewsItemBean> {
    private ArrayList<MessageNewsBean.MessageNewsItemBean> datas = new ArrayList<>();
    Context mContext;

    public PingTaiMessageAdapter(Context context, int layoutId, ArrayList<MessageNewsBean.MessageNewsItemBean> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        mContext = context;
    }

    @Override
    public void convert(final ViewHolder holder, final MessageNewsBean.MessageNewsItemBean bean, int position) {
        TextView time = holder.getView(R.id.time);
        ImageView img = holder.getView(R.id.img);
        TextView title = holder.getView(R.id.title);
        TextView tv_time = holder.getView(R.id.tv_time);
        TextView num = holder.getView(R.id.num);

        time.setText(bean.getCreatetime());
        GlideUtils.loadImg(bean.getNews().getThumbnail(),img);
        title.setText(bean.getNews().getPost_title());
        tv_time.setText(bean.getNews().getCreatetime());
        num.setText(FormatterUtil.formatNumber(bean.getNews().getPost_hits()));

        if ("1".equals(bean.getLook())){
            title.setTextColor(Color.parseColor("#333333"));
        }else{
            title.setTextColor(Color.parseColor("#999999"));
        }

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MessageDetailActivity.class);
                intent.putExtra("id",bean.getId());
                mContext.startActivity(intent);
            }
        });

    }
}