package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.ShopTypeBean;
import com.meida.cosmeticsshopuser.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;

/**
 * 作者 亢兰兰
 * 时间 2018/10/11 0011
 * 公司  郑州软盟
 */

public class FenLeileftAdapter extends CommonAdapter<ShopTypeBean.Bean> {
    private ArrayList<ShopTypeBean.Bean> datas = new ArrayList<>();
    Context mContext;
    private int selectIndex = -1;

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }

    public interface OnCheckChangeListener{
        void onCheckChange(int position);
    }

    private OnCheckChangeListener listener;

    public void setListener(OnCheckChangeListener listener) {
        this.listener = listener;
    }

    public FenLeileftAdapter(Context context, int layoutId, ArrayList<ShopTypeBean.Bean> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        mContext = context;
    }

    @Override
    public void convert(final ViewHolder holder, final ShopTypeBean.Bean bean, final int position) {
        View itemView = holder.getView(R.id.ll_fenlei);
        View view_fenlei = holder.getView(R.id.view_fenlei);
        TextView tv_fenleiname = holder.getView(R.id.tv_fenleiname);
        if (position==selectIndex){
            itemView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            view_fenlei.setBackgroundColor(mContext.getResources().getColor(R.color.main));
            tv_fenleiname.setTextColor(mContext.getResources().getColor(R.color.main));
        }else{
            itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorf7));
            view_fenlei.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
            tv_fenleiname.setTextColor(mContext.getResources().getColor(R.color.black));
        }
        tv_fenleiname.setText(bean.getTitle());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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