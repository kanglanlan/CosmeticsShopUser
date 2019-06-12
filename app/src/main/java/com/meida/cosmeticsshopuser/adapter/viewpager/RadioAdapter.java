package com.meida.cosmeticsshopuser.adapter.viewpager;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
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

/*R.layout.item_radio_type*/
public class RadioAdapter extends CommonAdapter<String> {

    private List<String> datas = new ArrayList<>();
    Context mContext;
    private int selectIndex = -1;

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        if (this.selectIndex==selectIndex){
            return;
        }
        this.selectIndex = selectIndex;
        notifyDataSetChanged();
    }

    public interface OnCheckChangeListener{
        void onCheckChange(int position);
    }

    private OnCheckChangeListener listener;

    public void setListener(OnCheckChangeListener listener) {
        this.listener = listener;
    }

    public RadioAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        mContext = context;

    }

    @Override
    public void convert(final ViewHolder holder, final String s, final int position) {

        TextView text = holder.getView(R.id.text);
        ImageView img = holder.getView(R.id.img);
        if (position==selectIndex){
            text.setTextColor(mContext.getResources().getColor(R.color.main));
            img.setImageResource(R.drawable.ico_img90);
        }else{
            text.setTextColor(mContext.getResources().getColor(R.color.b3));
            img.setImageDrawable(null);
        }

        text.setText(s);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position==selectIndex){

                }else{
                    selectIndex = position;
                    notifyDataSetChanged();
                    if (listener!=null){
                        listener.onCheckChange(position);
                    }
                }
            }
        });


    }
}