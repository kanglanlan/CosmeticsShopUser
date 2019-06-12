package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.ShopEvalBean;
import com.meida.cosmeticsshopuser.MyView.CircleImageView;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Administrator on 2019/1/21 0021.
 */

public class GoodsPJAdapter extends RecyclerView.Adapter<GoodsPJAdapter.ViewHolder>{

    public GoodsPJAdapter(Context mContext , ArrayList<ShopEvalBean.ShopEvalItemBean> datas) {
        this.datas = datas;
        this.mContext = mContext;
    }

    private ArrayList<ShopEvalBean.ShopEvalItemBean> datas = new ArrayList<>();
    Context mContext;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_goodspingjia2,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {

        final int position = holder.getAdapterPosition();
        ShopEvalBean.ShopEvalItemBean bean = datas.get(position);

        GlideUtils.loadHead(bean.getAvatar(),holder.img_pj);
        holder.tv_username.setText(bean.getUser_nickname());
        FormatterUtil.setStarRating(bean.getGoods(),holder.ratbar1);
        holder.tv_time.setText(bean.getCreatetime());
        holder.tv_content.setText(bean.getContent());
        holder.tv_type.setText(bean.getTitle());

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

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img_pj;
        TextView tv_username;
        ScaleRatingBar ratbar1;
        TextView tv_time;
        TextView tv_content ;
        TextView tv_type ;

        RecyclerView picRecycler;
        private ArrayList<String> picData = new ArrayList<>();
        private GridInImgAdapter2 picAdapter;

        public ViewHolder(View itemView) {
            super(itemView);

            img_pj = itemView.findViewById(R.id.img_pj);
            tv_username = itemView.findViewById(R.id.tv_username);
            ratbar1 = itemView.findViewById(R.id.ratbar1);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_type = itemView.findViewById(R.id.tv_type);

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
