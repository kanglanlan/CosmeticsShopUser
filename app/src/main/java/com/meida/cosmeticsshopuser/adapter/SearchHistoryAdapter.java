package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 亢兰兰
 * 时间 2018/10/11 0011
 * 公司  郑州软盟
 */
/*R.layout.item_search_history*/
public class SearchHistoryAdapter extends CommonAdapter<String> {

    private List<String> datas = new ArrayList<String>();
    Context mContext;

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onClickItem(int position,String s);
        void onDelete(int position);
    }

    public SearchHistoryAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        mContext = context;
    }

    @Override
    public void convert(final ViewHolder holder, final String s, final int position) {
        TextView text = holder.getView(R.id.text);
        View delete = holder.getView(R.id.delete);

        text.setText(s);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onDelete(position);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onClickItem(position,s);
                }
            }
        });


    }
}