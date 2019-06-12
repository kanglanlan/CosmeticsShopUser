package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Activity.ProductDetailActivity;
import com.meida.cosmeticsshopuser.Activity.StoresActivity;
import com.meida.cosmeticsshopuser.Bean.GoodsItemBean;
import com.meida.cosmeticsshopuser.Bean.ShopGoodsBean;
import com.meida.cosmeticsshopuser.MyView.FlowLiner;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.meida.cosmeticsshopuser.utils.ProjectUtils;
import com.willy.ratingbar.ScaleRatingBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 亢兰兰
 * 时间 2018/10/11 0011
 * 公司  郑州软盟
 */

public class StorelistAdapter extends CommonAdapter<ShopGoodsBean.ShopBean> {
    private ArrayList<ShopGoodsBean.ShopBean> datas = new ArrayList<>();
    Context mContext;

    public StorelistAdapter(Context context, int layoutId, ArrayList<ShopGoodsBean.ShopBean> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        mContext = context;
    }

    @Override
    public void convert(final ViewHolder holder, final ShopGoodsBean.ShopBean bean, int position) {
        FlowLiner ll = holder.getView(R.id.ll_more);
        ll.removeAllViews();
        ImageView img_dianpu = holder.getView(R.id.img_dianpu);
        TextView tv_title = holder.getView(R.id.tv_title);
        ScaleRatingBar ratbar01_mc = holder.getView(R.id.ratbar01_mc);
        TextView starTv = holder.getView(R.id.starTv);
        TextView tv_xiaoliang = holder.getView(R.id.tv_xiaoliang);
        TextView tv_time = holder.getView(R.id.tv_time);
        TextView tv_juli = holder.getView(R.id.tv_juli);
        View ll_lookmore = holder.getView(R.id.ll_lookmore);
        TextView tv_nums = holder.getView(R.id.tv_nums);

        GlideUtils.loadShop(bean.getAvatar(),img_dianpu);
        tv_title.setText(bean.getTitle());
        String starStr = bean.getGoods();
        float f = 0;
        try{
            f = Float.parseFloat(starStr);
        }catch (Exception e){

        }
        ratbar01_mc.setRating(f);
        starTv.setText(FormatterUtil.formatStarValue(starStr));
        FormatterUtil.formatSaleNum2(bean.getSalesvolume(),bean.getSalesvolumea(),tv_xiaoliang);
        tv_time.setText("");
        FormatterUtil.formatDistance(bean.getJuli(),tv_juli);
        FlowLiner ll_bq = holder.getView(R.id.ll_bq);
        ProjectUtils.sortShopLabel(bean.getDistribution1(),bean.getDistribution2(),bean.getDistribution3(),ll_bq);

        ll_lookmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        /*TODO 查看更多几件商品*/
        String goodsNumStr = bean.getGoodslist().getTotal()+"";
        StringBuilder sb = new StringBuilder();
        sb.append("查看更多");
        sb.append(goodsNumStr);
        sb.append("件商品");
        tv_nums.setText(sb.toString());

        List<GoodsItemBean> goodsList = bean.getGoodslist().getData();
        for (int i = 0; i<goodsList.size(); i++){
            addGoodsChild(goodsList.get(i),ll);
        }

        tv_nums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, StoresActivity.class);
                intent.putExtra("id",bean.getId());
                mContext.startActivity(intent);
            }
        });

        holder.getView(R.id.shopView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, StoresActivity.class);
                intent.putExtra("id",bean.getId());
                mContext.startActivity(intent);
            }
        });

    }


    private void addGoodsChild(final GoodsItemBean bean,FlowLiner parent){
        View goodsView = View.inflate(mContext,R.layout.item_listdianpugoods,null);
        ImageView img_goods = goodsView.findViewById(R.id.img_goods);
        TextView tv_gtitle = goodsView.findViewById(R.id.tv_gtitle);
        TextView tv_qian = goodsView.findViewById(R.id.tv_qian);
        TextView tv_gxiaoliang = goodsView.findViewById(R.id.tv_gxiaoliang);
        TextView tv_haopinglv = goodsView.findViewById(R.id.tv_haopinglv);
        FlowLiner ll_goodsbq = goodsView.findViewById(R.id.ll_goodsbq);
        GlideUtils.loadGoods2(bean.getImgs(),img_goods);
        tv_gtitle.setText(bean.getTitle());
        FormatterUtil.formatPrice2(bean.getPrice(),tv_qian);
        FormatterUtil.formatSaleNum2(bean.getSalesvolume(),bean.getSalesvolumea(),tv_gxiaoliang);
        FormatterUtil.formatPositiveRate(bean.getFavorablerate(),tv_haopinglv);
        /*TODO 商品优惠券*/
        ll_goodsbq.removeAllViews();
        if (!TextUtils.isEmpty(bean.getCoupon())){
            View viewbq = View.inflate(mContext, R.layout.item_biaoqian, null);
            TextView tv_biaoqian = viewbq.findViewById(R.id.tv_biaoqian);
            tv_biaoqian.setText(bean.getCoupon());
            ll_goodsbq.addView(viewbq);
        }
        goodsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra("id",bean.getId());
                mContext.startActivity(intent);
            }
        });
        parent.addView(goodsView);


    }

}