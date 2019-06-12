package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.MyView.FlowLayout;
import com.meida.cosmeticsshopuser.R;
import com.willy.ratingbar.ScaleRatingBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/12/20 0020.
 */

public class ProductEvalAdapter extends CommonAdapter<String>{

    private Context context;
    private List<String> data = new ArrayList<>();

    public ProductEvalAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
        this.context = context;
        this.data = datas;
    }

    @Override
    protected void convert(final ViewHolder holder, final String bean, final int position) {
        ImageView img = holder.getView(R.id.img);
        TextView name = holder.getView(R.id.name);
        TextView time = holder.getView(R.id.time);
        ScaleRatingBar ratingBar = holder.getView(R.id.ratingBar);
        TextView evalText = holder.getView(R.id.evalText);
        FlowLayout flowLayout = holder.getView(R.id.flowLayout);
        TextView specify = holder.getView(R.id.specify);
        TextView reply = holder.getView(R.id.reply);

    }



}
