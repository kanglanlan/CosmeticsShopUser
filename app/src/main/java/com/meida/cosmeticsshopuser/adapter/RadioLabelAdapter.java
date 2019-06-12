package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckedTextView;

import com.meida.cosmeticsshopuser.Bean.FindTypeBean;
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

/*R.layout.item_label*/
public class RadioLabelAdapter extends CommonAdapter<FindTypeBean.TypeBean> {
    private List<FindTypeBean.TypeBean> datas = new ArrayList<>();
    Context mContext;

    private int selectIndex = -1;

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
        notifyDataSetChanged();
    }

    public interface OnSelectChangeListener{
        void onSelectChange(int position);
    }

    private OnSelectChangeListener listener;

    public void setListener(OnSelectChangeListener listener) {
        this.listener = listener;
    }

    public RadioLabelAdapter(Context context, int layoutId, List<FindTypeBean.TypeBean> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        mContext = context;
    }

    @Override
    public void convert(final ViewHolder holder, final FindTypeBean.TypeBean bean, final int position) {
        CheckedTextView textView = holder.getView(R.id.label);
        textView.setText(bean.getTitle());

        if (position==selectIndex){
            textView.setChecked(true);
        }else{
            textView.setChecked(false);
        }

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position==selectIndex){

                }else{
                    selectIndex = position;
                    notifyDataSetChanged();
                    if (listener!=null){
                        listener.onSelectChange(position);
                    }
                }
            }
        });

    }



}