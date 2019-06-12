package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.FindListBean;
import com.meida.cosmeticsshopuser.MyView.CircleImageView;
import com.meida.cosmeticsshopuser.MyView.CustomGridView;
import com.meida.cosmeticsshopuser.Activity.FindInfoActivity;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.meida.cosmeticsshopuser.utils.LoggerUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 作者 亢兰兰
 * 时间 2018/10/11 0011
 * 公司  郑州软盟
 */

public class FindAdapter extends CommonAdapter<FindListBean.FindItemBean> {


    private boolean canDelete = false;

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public interface OnItemActionListener{
        void onDelete(ViewHolder holder,FindListBean.FindItemBean bean);
    }

    private OnItemActionListener listener;

    public void setListener(OnItemActionListener listener) {
        this.listener = listener;
    }

    private ArrayList<FindListBean.FindItemBean> datas = new ArrayList<>();
    Context mContext;

    public FindAdapter(Context context, int layoutId, ArrayList<FindListBean.FindItemBean> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        mContext = context;
    }

    @Override
    public void convert(final ViewHolder holder, final FindListBean.FindItemBean bean, final int posi) {

        final int position = holder.getAdapterPosition();


        CustomGridView gridView = holder.getView(R.id.gv_faxainpic);
        gridView.setTag(bean.getId());

        if (gridView.getTag()!=null && bean.getId().equals((String)gridView.getTag())){
            LoggerUtil.e("FIND_ADAPTER111",(String) gridView.getTag()+"   "+bean.getId());
            String imgs = bean.getImgs();
            if (!TextUtils.isEmpty(imgs) && (imgs.length()>1)){
                String[] imgArr = imgs.split(",");
                if (imgArr!=null && imgArr.length>0){
                    List<String> imgList = Arrays.asList(imgArr);
                    if (imgList.size()==4){
                        gridView.setNumColumns(2);
                    }else{
                        gridView.setNumColumns(3);
                    }
                    GridInImgAdapter gridAdapter = new GridInImgAdapter(mContext,R.layout.img_95,imgList);
                    gridView.setAdapter(gridAdapter);
                }
            }

        }else{

            LoggerUtil.e("FIND_ADAPTER222",String.valueOf(gridView.getTag())+"   "+bean.getId());
        }

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, FindInfoActivity.class).putExtra("id", bean.getId()));
            }
        });

        CircleImageView img_photo = holder.getView(R.id.img_photo);
        TextView tv_name = holder.getView(R.id.tv_name);
        TextView tv_time = holder.getView(R.id.tv_time);
        TextView tv_content = holder.getView(R.id.tv_content);
        TextView tv_add = holder.getView(R.id.tv_add);
        TextView tv_sc = holder.getView(R.id.tv_sc);
        TextView tv_pj = holder.getView(R.id.tv_pj);
        TextView tv_liulan = holder.getView(R.id.tv_liulan);
        if (bean.getShopinfo()!=null){
            GlideUtils.loadHead(bean.getShopinfo().getAvatar(),img_photo);
            tv_name.setText(bean.getShopinfo().getTitle());
        }else if (bean.getUserinfo()!=null){
            GlideUtils.loadHead(bean.getUserinfo().getAvatar(),img_photo);
            tv_name.setText(bean.getUserinfo().getTitle());
        }else{
            img_photo.setImageResource(R.drawable.ico_img115);
            tv_name.setText("匿名");
        }

        tv_time.setText(bean.getCreatetime());
        tv_content.setText(bean.getContent());

        tv_sc.setText(FormatterUtil.formatNumber(bean.getLike()));
        tv_pj.setText(FormatterUtil.formatNumber(bean.getComment()));
        tv_liulan.setText(FormatterUtil.formatNumber(bean.getHits()));

        /*TODO 是否点赞*/


        /*我的分享支持删除*/
        if (canDelete){
            tv_add.setText("删除");
            tv_add.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ico_img71,0,0,0);
            tv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!=null){
                        listener.onDelete(holder,bean);
                    }
                }
            });
        }else{
            tv_add.setText(bean.getAddress());
            tv_add.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ico_img33,0,0,0);
            tv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }



    }






}