package com.meida.cosmeticsshopuser.adapter.viewpager;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.ShopCartBean;
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

public class OrderProductAdapter extends CommonAdapter<ShopCartBean.CartGoods> {

    private ArrayList<ShopCartBean.CartGoods> datas = new ArrayList<>();
    Context mContext;

    public OrderProductAdapter(Context context, int layoutId, ArrayList<ShopCartBean.CartGoods> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        mContext = context;
    }

    @Override
    public void convert(final ViewHolder holder, final ShopCartBean.CartGoods bean, int position) {
        ImageView img = holder.getView(R.id.img);
        TextView name = holder.getView(R.id.name);
        TextView specify = holder.getView(R.id.specify);
        TextView price = holder.getView(R.id.price);
        TextView num = holder.getView(R.id.num);

        GlideUtils.loadGoods2(bean.getImgs(),img);
        name.setText(bean.getTitle());
        specify.setText(bean.getSpectitle());
        FormatterUtil.formatPrice2(bean.getPrice(),price);
        num.setText("x"+String.valueOf(bean.getNum()));

    }
}