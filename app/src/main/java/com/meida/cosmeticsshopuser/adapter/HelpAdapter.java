package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Activity.MessageDetailActivity;
import com.meida.cosmeticsshopuser.Activity.WebViewActivity;
import com.meida.cosmeticsshopuser.Bean.WebDetailBean;
import com.meida.cosmeticsshopuser.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

/**
 * 作者 亢兰兰
 * 时间 2018/10/11 0011
 * 公司  郑州软盟
 */

public class HelpAdapter extends CommonAdapter<WebDetailBean.DataBean> {

    private ArrayList<WebDetailBean.DataBean> datas = new ArrayList<>();
    Context mContext;

    public HelpAdapter(Context context, int layoutId, ArrayList<WebDetailBean.DataBean> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        mContext = context;
    }

    @Override
    public void convert(final ViewHolder holder, final WebDetailBean.DataBean bean, final int position) {

        TextView tv_help = holder.getView(R.id.tv_help);
        tv_help.setText(bean.getTitle());

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("id","8");
                intent.putExtra("index",position);
                mContext.startActivity(intent);

            }
        });


    }

    /**
     *  private val datas = ArrayList<WebDetailBean.DataBean>()

     init {
     this.datas = datas
     }

     public override fun convert(holder: ViewHolder, bean: WebDetailBean.DataBean, position: Int) {

     val tv_help = holder.getView<TextView>(R.id.tv_help)
     tv_help.text = bean.title

     holder.convertView.setOnClickListener {
     val intent = Intent(mContext, WebViewActivity::class.java)
     intent.putExtra("id", "8")
     intent.putExtra("index", position)
     mContext.startActivity(intent)
     }


     }

     */


}