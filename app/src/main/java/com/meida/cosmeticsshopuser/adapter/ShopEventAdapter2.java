package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Activity.FindInfoActivity;
import com.meida.cosmeticsshopuser.Bean.FindListBean;
import com.meida.cosmeticsshopuser.Bean.ShopEventBean;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.meida.cosmeticsshopuser.utils.GlideUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Administrator on 2019/1/9 0009.
 */

public class ShopEventAdapter2 extends RecyclerView.Adapter<ShopEventAdapter2.ViewHolder>{


    private ArrayList<ShopEventBean.ShopEventItemBean> datas = new ArrayList<>();
    Context mContext;

    public ShopEventAdapter2(Context context, int layoutId, ArrayList<ShopEventBean.ShopEventItemBean> datas) {
        this.datas = datas;
        mContext = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dianpudongtai2,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {

        final int position = holder.getAdapterPosition();
        final ShopEventBean.ShopEventItemBean bean = datas.get(position);

        String imgs = bean.getImgs();
        holder.picData.clear();
        if (!TextUtils.isEmpty(imgs) && (imgs.length()>1)){
            String[] imgArr = imgs.split(",");
            if (imgArr!=null && imgArr.length>0){
                holder.picData.addAll(Arrays.asList(imgArr));
            }
        }

        holder.picRecycler.setTag(bean.getId());
        if ( (holder.picRecycler.getTag()!=null) &&( (bean.getId()).equals((String)holder.picRecycler.getTag())  ) ){
            holder.picAdapter.notifyDataSetChanged();
        }else{
        }

       /* GlideUtils.loadHead(bean.getAvatar(),holder.img_photo);
        holder.tv_name.setText(bean.getUser_nickname());*/

        if (bean.getShopinfo()!=null){
            GlideUtils.loadHead(bean.getShopinfo().getAvatar(),holder.img_photo);
            holder.tv_name.setText(bean.getShopinfo().getTitle());
        }else if (bean.getUserinfo()!=null){
            GlideUtils.loadHead(bean.getUserinfo().getAvatar(),holder.img_photo);
            holder.tv_name.setText(bean.getUserinfo().getTitle());
        }else{
            holder.img_photo.setImageResource(R.drawable.ico_img115);
            holder.tv_name.setText("匿名");
        }

        holder.tv_time.setText(bean.getCreatetime());
        holder.tv_content.setText(FormatterUtil.getMaxText(bean.getContent()));
        holder.tv_liulan.setText(FormatterUtil.formatNumber(String.valueOf(bean.getHits())));
        holder.tv_sc.setText(FormatterUtil.formatNumber(String.valueOf(bean.getLike())));
        holder.tv_pj.setText(FormatterUtil.formatNumber(String.valueOf(bean.getComment())));

        /*是否点赞*/
       /* final String isLike = bean.getIslike();
        if ("1".equals(isLike)){*//*ico_img06*//*
            holder.tv_sc.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ico_img121,0,0,0);
        }else{
            holder.tv_sc.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ico_img121,0,0,0);
        }*/

        holder.tv_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //updateEventCollectState(position,"1".equals(datas.get(position).getIslike()),tv_sc);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, FindInfoActivity.class).putExtra("id", bean.getId()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView img_photo;
        TextView tv_name ;
        TextView tv_time ;
        TextView tv_content ;
        TextView tv_liulan ;
        TextView tv_sc ;
        TextView tv_pj ;

        RecyclerView picRecycler;
        private ArrayList<String> picData = new ArrayList<>();
        private GridInImgAdapter2 picAdapter;

        public ViewHolder(View itemView) {
            super(itemView);

             img_photo = itemView.findViewById(R.id.img_photo);
             tv_name = itemView.findViewById(R.id.tv_name);
             tv_time = itemView.findViewById(R.id.tv_time);
             tv_content = itemView.findViewById(R.id.tv_content);
             tv_liulan = itemView.findViewById(R.id.tv_liulan);
             tv_sc = itemView.findViewById(R.id.tv_sc);
             tv_pj = itemView.findViewById(R.id.tv_pj);

            GridLayoutManager glm = new GridLayoutManager(mContext,3);
            glm.setOrientation(GridLayoutManager.VERTICAL);
            picRecycler = itemView.findViewById(R.id.picRecycler);
            picRecycler.setLayoutManager(glm);
            picAdapter = new GridInImgAdapter2(mContext,R.layout.img_95,picData);
            picAdapter.setHasStableIds(true);
            picRecycler.setAdapter(picAdapter);
            picRecycler.setNestedScrollingEnabled(false);

        }
    }


}
