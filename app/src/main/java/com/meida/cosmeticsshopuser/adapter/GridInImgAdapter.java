package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.meida.cosmeticsshopuser.utils.ProjectUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;


import java.util.ArrayList;
import java.util.List;

/**
 * 作者 亢兰兰
 * 时间 2018/10/11 0011
 * 公司  郑州软盟
 */

public class GridInImgAdapter extends CommonAdapter<String> {
    private List<String> datas = new ArrayList<String>();
    Context mContext;

    public GridInImgAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        mContext = context;
    }

    @Override
    public void convert(final ViewHolder holder, final String s, final int position) {
        /*R.layout.img_95*/
        ImageView img = holder.getView(R.id.img);
        GlideUtils.loadImg(s,img);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProjectUtils.ShowLargeImg(mContext, datas, position);
            }
        });

    }

}