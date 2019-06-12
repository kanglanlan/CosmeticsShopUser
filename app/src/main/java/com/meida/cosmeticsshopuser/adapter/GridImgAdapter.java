package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meida.cosmeticsshopuser.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/6.
 */

public class GridImgAdapter extends RecyclerView.Adapter<GridImgAdapter.ViewHolder> {

    private Context context;
    private int resourceId;
    private List<String> data = new ArrayList<>();
    private int maxNum = 9;

    public interface onMyItemClickListener{
        void onItemClick(View view, int position);
        void onPermissionRequest();
        void onDelete(int position);
        void onAdd(int surplusNum);
    }

    private onMyItemClickListener listener;

    public  void setOnMyItemClickListener(onMyItemClickListener listener){
        this.listener  = listener;
    }

    public GridImgAdapter(Context context, int resourceId, List<String> data){
        this.context = context;
        this.resourceId = resourceId;
        this.data = data;
    }

    public GridImgAdapter(Context context, int resourceId, List<String> data, int maxNum){
        this.context = context;
        this.resourceId = resourceId;
        this.data = data;
        this.maxNum = maxNum;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(resourceId,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int pos) {

        final int position = holder.getAdapterPosition();

        String imgPath = data.get(position);
        if ("-9".equals(imgPath)){
            holder.del.setVisibility(View.GONE);
            holder.img.setImageResource(R.mipmap.pic_add);
        }else{
            holder.del.setVisibility(View.VISIBLE);
            Glide.with(context.getApplicationContext())
                    .setDefaultRequestOptions(new RequestOptions().error(R.drawable.moren))
                    .load(data.get(position)).into(holder.img);
        }

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onDelete(position);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    if ("-9".equals(data.get(position))){
                        listener.onAdd(maxNum-position);
                    }else{
                        listener.onItemClick(v,position);
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView img;
        public View del;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView)itemView.findViewById(R.id.img);
            del = itemView.findViewById(R.id.del);
        }

    }

}
