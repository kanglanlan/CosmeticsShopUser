package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.MessageBean;
import com.meida.cosmeticsshopuser.Activity.MessageDetailActivity;
import com.meida.cosmeticsshopuser.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

/**
 * 作者 亢兰兰
 * 时间 2018/10/11 0011
 * 公司  郑州软盟
 */

public class SysMessageAdapter extends CommonAdapter<MessageBean.MessageItemBean> {
    private ArrayList<MessageBean.MessageItemBean> datas = new ArrayList<>();
    Context mContext;

    public SysMessageAdapter(Context context, int layoutId, ArrayList<MessageBean.MessageItemBean> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        mContext = context;
    }

    @Override
    public void convert(final ViewHolder holder, final MessageBean.MessageItemBean bean, int position) {

        TextView time = holder.getView(R.id.time);
        TextView title = holder.getView(R.id.title);
        TextView content = holder.getView(R.id.content);
        TextView toDetail = holder.getView(R.id.toDetail);

        if ("1".equals(bean.getLook())){
            content.setTextColor(Color.parseColor("#333333"));
        }else{
            content.setTextColor(Color.parseColor("#999999"));
        }

        time.setText(bean.getCreatetime());
        toDetail.setText("查看详情");
        title.setText(bean.getTitle());
        content.setText(bean.getContent());
        toDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MessageDetailActivity.class);
                intent.putExtra("id",bean.getId());
                mContext.startActivity(intent);
            }
        });



    }
}