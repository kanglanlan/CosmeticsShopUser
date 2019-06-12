package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.AddrBean;
import com.meida.cosmeticsshopuser.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

/**
 * 作者 亢兰兰
 * 时间 2018/10/11 0011
 * 公司  郑州软盟
 */

public class AddAdapter extends CommonAdapter<AddrBean.AddrItemBean> {
    private ArrayList<AddrBean.AddrItemBean> datas = new ArrayList<>();
    Context mContext;

    public AddAdapter(Context context, int layoutId, ArrayList<AddrBean.AddrItemBean> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        mContext = context;
    }

    public interface OnItemActionListener{

        void onItemClick(int position, AddrBean.AddrItemBean bean);
        void onDelete(ViewHolder holder, AddrBean.AddrItemBean bean);
        void onEdit(int position, AddrBean.AddrItemBean bean);
        void setDefault(int position, AddrBean.AddrItemBean bean);
    }

    private OnItemActionListener listener;
    public void setListener(OnItemActionListener listener) {
        this.listener = listener;
    }

    @Override
    public void convert(final ViewHolder holder, final AddrBean.AddrItemBean bean, final int position) {
        TextView namePhone = holder.getView(R.id.namePhone);
        TextView detail = holder.getView(R.id.detail);
        View defaultView = holder.getView(R.id.defaultView);
        ImageView defaultImg = holder.getView(R.id.defaultImg);
        TextView defaultTv = holder.getView(R.id.defaultTv);
        View edit = holder.getView(R.id.edit);
        View delete = holder.getView(R.id.delete);

        String nameStr = bean.getConsignee();
        String phoneStr = bean.getMobile();

        StringBuilder builder1 = new StringBuilder();
        builder1.append(nameStr);
        builder1.append("   ");
        builder1.append(phoneStr);
        namePhone.setText(builder1.toString().trim());

        StringBuilder builder2 = new StringBuilder();
        builder2.append(bean.getAddress());
        builder2.append("   ");
        builder2.append(bean.getDoornumber());
        detail.setText(builder2.toString());

        defaultView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.setDefault(position,bean);
                }
            }
        });
        if ("1".equals(bean.getDefaultStr())){
            defaultImg.setImageResource(R.drawable.ico_img70);
            defaultTv.setText("默认地址");
        }else{
            defaultImg.setImageResource(R.drawable.ico_img72);
            defaultTv.setText("设为默认");
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onEdit(position,bean);
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onDelete(holder,bean);
                }
            }
        });

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onItemClick(position,bean);
                }
            }
        });



    }



}