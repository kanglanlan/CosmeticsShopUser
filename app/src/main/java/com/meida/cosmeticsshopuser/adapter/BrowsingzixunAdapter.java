package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.NewsItemBean;
import com.meida.cosmeticsshopuser.MyView.RoundImageView;
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

public class BrowsingzixunAdapter extends CommonAdapter<NewsItemBean> {
    private ArrayList<NewsItemBean> datas = new ArrayList<>();
    Context mContext;

    public BrowsingzixunAdapter(Context context, int layoutId, ArrayList<NewsItemBean> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        mContext = context;
    }

    public interface OnItemListener{
        void onDelete(ViewHolder holder,NewsItemBean bean);
        void onClickItem(int position,NewsItemBean bean);
    }
    public OnItemListener listener;

    public void setListener(OnItemListener listener) {
        this.listener = listener;
    }


    @Override
    public void convert(final ViewHolder holder, final NewsItemBean bean, final int position) {

        TextView timeTv = holder.getView(R.id.time);
        RoundImageView img_zixun = holder.getView(R.id.img_zixun);
        TextView tv_zixuntitle = holder.getView(R.id.tv_zixuntitle);
        TextView tv_time = holder.getView(R.id.tv_time);
        TextView tv_liulan = holder.getView(R.id.tv_liulan);

        GlideUtils.loadNews(bean.getThumbnail(),img_zixun);
        tv_zixuntitle.setText(bean.getPost_title());
        tv_time.setText(bean.getCreatetime());
        tv_liulan.setText(FormatterUtil.formatNumber(bean.getPost_hits()));
        timeTv.setText(bean.getCreate_time());

        View contentView = holder.getView(R.id.contentView);
        View delete = holder.getView(R.id.delete);
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