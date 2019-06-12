package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.utils.DisplayUtil;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.meida.cosmeticsshopuser.utils.ProjectUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 亢兰兰
 * 时间 2018/10/11 0011
 * 公司  郑州软盟
 */

public class GridInImgAdapter2 extends CommonAdapter<String> {
    private List<String> datas = new ArrayList<String>();
    Context mContext;

    public GridInImgAdapter2(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        mContext = context;
    }

    @Override
    public void convert(final ViewHolder holder, final String s, final int position) {
        /*R.layout.img_95*/
        ImageView img = holder.getView(R.id.img);
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams)img.getLayoutParams();
        lp.setMargins(0,0,0,0);
        img.setLayoutParams(lp);
        GlideUtils.loadImg(s,img);

        int rightMargin ,topMargin ;
        if (position%3==0){
            rightMargin = 0;
        }else{
            rightMargin = 3;
        }
        if (position<3){
            topMargin = 0;
        }else{
            topMargin = 3;
        }
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
        layoutParams.setMargins(0, DisplayUtil.dp2px(topMargin),DisplayUtil.dp2px(rightMargin),0);
        holder.itemView.setLayoutParams(layoutParams);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProjectUtils.ShowLargeImg(mContext, datas, position);
            }
        });

    }



}