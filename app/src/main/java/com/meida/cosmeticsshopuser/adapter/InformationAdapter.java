package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Activity.MessageDetailActivity;
import com.meida.cosmeticsshopuser.Bean.NewsItemBean;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.nohttp.Params;
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

public class InformationAdapter extends CommonAdapter<NewsItemBean> {
    private ArrayList<NewsItemBean> datas = new ArrayList<>();
    Context mContext;

    private boolean showDivider = true;

    public void setShowDivider(boolean showDivider) {
        this.showDivider = showDivider;
    }

    public InformationAdapter(Context context, int layoutId, ArrayList<NewsItemBean> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        mContext = context;
    }

    @Override
    public void convert(final ViewHolder holder, final NewsItemBean bean, int position) {

        final ImageView img_zixun = holder.getView(R.id.img_zixun);
        TextView tv_zixuntitle = holder.getView(R.id.tv_zixuntitle);
        TextView tv_time = holder.getView(R.id.tv_time);
        TextView tv_liulan = holder.getView(R.id.tv_liulan);
        TextView tv_content = holder.getView(R.id.tv_content);

        GlideUtils.loadNews(bean.getThumbnail(),img_zixun);
        tv_zixuntitle.setText(bean.getPost_title());
        tv_time.setText(bean.getCreatetime());
        tv_liulan.setText(FormatterUtil.formatNumber(bean.getPost_hits()));

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(img_zixun.getContext(), MessageDetailActivity.class);
                intent.putExtra("canShare",true);
                intent.putExtra("id",bean.getId());
                intent.putExtra("ip", Params.getNewsDetail);
                img_zixun.getContext().startActivity(intent);
            }
        });

        View line = holder.getView(R.id.line);
        if (showDivider){
            line.setBackgroundColor(mContext.getResources().getColor(R.color.xian));
        }else{
            line.setBackgroundColor(Color.parseColor("#ffffff"));
        }


    }



}