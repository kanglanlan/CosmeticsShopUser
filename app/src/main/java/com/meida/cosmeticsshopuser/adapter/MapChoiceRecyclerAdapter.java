package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.meida.cosmeticsshopuser.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/1/4 0004.
 */

public class MapChoiceRecyclerAdapter extends RecyclerView.Adapter<MapChoiceRecyclerAdapter.ViewHolder> {

    private Context context;

    private List<PoiItem> list;

    private int checkposition = 0;

    private boolean canChoose = true;

    private OnItemClickListener itemOnItemClickListener;

    public MapChoiceRecyclerAdapter(Context context, List<PoiItem> list) {
        this.context = context;
        this.list = list;
    }

    public MapChoiceRecyclerAdapter(Context context,boolean canChoose) {
        this.context = context;
        this.canChoose = canChoose;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_map_choice, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final PoiItem item = list.get(position);
        holder.tv_title.setText(item.getTitle());
        holder.tv_content.setText(item.getSnippet());

        if (canChoose){
            if (checkposition == position) {
                holder.iv_check.setVisibility(View.VISIBLE);
            } else {
                holder.iv_check.setVisibility(View.GONE);
            }
        }else{
            holder.iv_check.setVisibility(View.GONE);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkposition = position;
                notifyDataSetChanged();
                if (itemOnItemClickListener != null) {
                    itemOnItemClickListener.onItemClick(holder.itemView, position, item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_content)
        TextView tv_content;
        @Bind(R.id.iv_check)
        ImageView iv_check;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void refresh(List<PoiItem> list) {
        this.list = list;
        checkposition = 0;
        notifyDataSetChanged();
    }

    public PoiItem getCheck() {
        if (list != null && list.size() != 0) {
            return list.get(checkposition);
        }
        return null;
    }

    public void setItemOnItemClickListener(OnItemClickListener itemOnItemClickListener) {
        this.itemOnItemClickListener = itemOnItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view ,int position, PoiItem item);
    }

}