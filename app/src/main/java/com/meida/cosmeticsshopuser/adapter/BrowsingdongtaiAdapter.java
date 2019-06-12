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
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Activity.FindInfoActivity;
import com.meida.cosmeticsshopuser.Bean.FindListBean;
import com.meida.cosmeticsshopuser.MyView.CircleImageView;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.meida.cosmeticsshopuser.utils.GlideUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 作者 亢兰兰
 * 时间 2018/10/11 0011
 * 公司  郑州软盟
 */

public class BrowsingdongtaiAdapter extends RecyclerView.Adapter<BrowsingdongtaiAdapter.ViewHolder> {

    private ArrayList<FindListBean.FindItemBean> datas = new ArrayList<>();

    Context mContext;

    public BrowsingdongtaiAdapter(Context mContext, ArrayList<FindListBean.FindItemBean> datas) {
        this.datas = datas;
        this.mContext = mContext;
    }


    public interface OnItemListener{
        void onDelete(BrowsingdongtaiAdapter.ViewHolder holder, FindListBean.FindItemBean bean);
        void onClickItem(int position,FindListBean.FindItemBean bean);
    }
    public OnItemListener listener;

    public void setListener(OnItemListener listener) {
        this.listener = listener;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dongtai_scroll,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int pos) {

        final int position = holder.getAdapterPosition();
        final FindListBean.FindItemBean bean = datas.get(position);
        holder.contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, FindInfoActivity.class).putExtra("id", bean.getId()));
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onDelete(holder,bean);
                }
            }
        });

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

        holder.tv_sc.setText(FormatterUtil.formatNumber(bean.getLike()));
        holder.tv_pj.setText(FormatterUtil.formatNumber(bean.getComment()));
        holder.tv_liulan.setText(FormatterUtil.formatNumber(bean.getHits()));

        /*TODO 是否点赞*/

        if ("1".equals(bean.getIsaddress())){
            holder.tv_add.setText(bean.getAddress());
            holder.tv_add.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ico_img33,0,0,0);
        }else{
            holder.tv_add.setText("");
            holder.tv_add.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0);
        }
        /*holder.tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
*/
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
            Log.e("AAAAAAAYYYYY","YYYYYYY");
            holder.picAdapter.notifyDataSetChanged();
        }else{
            Log.e("AAAAAAFFFFFFFFF","FFFFFFFFF");
        }


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img_photo;
        TextView tv_name;
        TextView tv_time;
        TextView tv_content;
        TextView tv_add ;
        TextView tv_sc;
        TextView tv_pj ;
        TextView tv_liulan ;
        View contentView ;
        View delete ;

        RecyclerView picRecycler;
        private ArrayList<String> picData = new ArrayList<>();
        private GridInImgAdapter2 picAdapter;


        public ViewHolder(View itemView) {
            super(itemView);

             img_photo = itemView.findViewById(R.id.img_photo);
             tv_name = itemView.findViewById(R.id.tv_name);
             tv_time = itemView.findViewById(R.id.tv_time);
             tv_content = itemView.findViewById(R.id.tv_content);
             tv_add = itemView.findViewById(R.id.tv_add);
             tv_sc = itemView.findViewById(R.id.tv_sc);
             tv_pj = itemView.findViewById(R.id.tv_pj);
             tv_liulan = itemView.findViewById(R.id.tv_liulan);
             contentView = itemView.findViewById(R.id.contentView);
             delete = itemView.findViewById(R.id.delete);

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