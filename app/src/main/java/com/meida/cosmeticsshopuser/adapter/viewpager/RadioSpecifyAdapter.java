package com.meida.cosmeticsshopuser.adapter.viewpager;

import android.content.Context;
import android.view.View;
import android.widget.CheckedTextView;

import com.meida.cosmeticsshopuser.Bean.GoodsDetailBean;
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

public class RadioSpecifyAdapter extends CommonAdapter<GoodsDetailBean.Specify> {

    private List<GoodsDetailBean.Specify> datas = new ArrayList<>();
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

    public RadioSpecifyAdapter(Context context, int layoutId, List<GoodsDetailBean.Specify> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        mContext = context;
    }

    @Override
    public void convert(final ViewHolder holder, final GoodsDetailBean.Specify bean, final int position) {
        CheckedTextView radio = holder.getView(R.id.radio);
        radio.setText(bean.getTitle());
        if (position==selectIndex){
            radio.setChecked(true);
        }else{
            radio.setChecked(false);
        }

        radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position==selectIndex){

                }else{
                    selectIndex=position;
                    notifyDataSetChanged();
                    if (listener!=null){
                        listener.onCheckChange(position);
                    }
                }
            }
        });

    }
}