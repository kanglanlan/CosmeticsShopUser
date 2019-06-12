package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Activity.ProductDetailActivity;
import com.meida.cosmeticsshopuser.Bean.GoodsItemBean;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.base.App;
import com.meida.cosmeticsshopuser.utils.CommonUtil;
import com.meida.cosmeticsshopuser.utils.DisplayUtil;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2019/1/8 0008.
 */

public class GoodsAdapter extends CommonAdapter<GoodsItemBean> {

    private Context mContext;

    public GoodsAdapter(Context context, int layoutId, List<GoodsItemBean> datas) {
        super(context, layoutId, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, final GoodsItemBean item, int position) {
        ImageView img = viewHolder.getView(R.id.img_chanpin);
        int wid = (App.wid - DisplayUtil.dp2px( 30)) / 2;
        int he = wid * 531 / 894;
        CommonUtil.setwidhe(img, wid, he);
        String imgs = item.getImgs();
        if (!TextUtils.isEmpty(imgs)){
            String[] imgArray = imgs.split(",");
            if (imgArray.length>0){
                GlideUtils.loadRecommendProduct(imgArray[0],img);
            }
        }
        TextView tv_chanpintitle = viewHolder.getView(R.id.tv_chanpintitle);
        tv_chanpintitle.setText(item.getTitle());

        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra("id",item.getId());
                mContext.startActivity(intent);
            }
        });

    }

}
