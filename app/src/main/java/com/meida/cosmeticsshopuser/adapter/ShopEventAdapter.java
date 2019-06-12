package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.ActionBean;
import com.meida.cosmeticsshopuser.Bean.ShopEventBean;
import com.meida.cosmeticsshopuser.MyView.CustomGridView;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.meida.cosmeticsshopuser.utils.ProjectUtils;
import com.yolanda.nohttp.rest.Request;
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
/*R.layout.item_dianpudongtai*/
/*店铺详情下的店铺动态*/
public class ShopEventAdapter extends CommonAdapter<ShopEventBean.ShopEventItemBean> {
    private ArrayList<ShopEventBean.ShopEventItemBean> datas = new ArrayList<>();
    Context mContext;

    public ShopEventAdapter(Context context, int layoutId, ArrayList<ShopEventBean.ShopEventItemBean> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        mContext = context;
    }

    @Override
    public void convert(final ViewHolder holder, final ShopEventBean.ShopEventItemBean bean, final int position) {

        ImageView img_photo = holder.getView(R.id.img_photo);
        TextView tv_name = holder.getView(R.id.tv_name);
        TextView tv_time = holder.getView(R.id.tv_time);
        TextView tv_content = holder.getView(R.id.tv_content);
        TextView tv_liulan = holder.getView(R.id.tv_liulan);
        final TextView tv_sc = holder.getView(R.id.tv_sc);
        TextView tv_pj = holder.getView(R.id.tv_pj);

        GlideUtils.loadHead(bean.getAvatar(),img_photo);
        tv_name.setText(bean.getUser_nickname());
        tv_time.setText(bean.getCreatetime());
        tv_content.setText(bean.getContent());
        tv_liulan.setText(FormatterUtil.formatNumber(String.valueOf(bean.getHits())));
        tv_sc.setText(FormatterUtil.formatNumber(String.valueOf(bean.getLike())));
        tv_pj.setText(FormatterUtil.formatNumber(String.valueOf(bean.getComment())));

        /*是否点赞*/
        final String isLike = bean.getIslike();
        if ("1".equals(isLike)){
            tv_sc.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ico_img06,0,0,0);
        }else{
            tv_sc.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ico_img121,0,0,0);
        }

        tv_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //updateEventCollectState(position,"1".equals(datas.get(position).getIslike()),tv_sc);
            }
        });

        String imgs = bean.getImgs();
        CustomGridView gridView = holder.getView(R.id.gv_faxainpic);
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

    }


    /*TODO 点赞 或 取消 点赞 店铺动态*/
    private void updateEventCollectState(final int position, final boolean b,final TextView textView){/*true  原本 没有收藏  */
        Request<String> request = ProjectUtils.getRequest(mContext, Params.updateShopEventCollState,true);
        request.add("id",datas.get(position).getId());
        CallServer.getRequestInstance().add(mContext, 0, request,
                new CustomHttpListener<ActionBean>(mContext,true,ActionBean.class) {
                    @Override
                    public void doWork(ActionBean result, String code) {
                        if (result.getMsg().contains("取消")){
                            datas.get(position).setIslike("");
                            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ico_img06,0,0,0);
                        }else{
                            datas.get(position).setIslike("1");
                            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ico_img121,0,0,0);
                        }
                        //notifyDataSetChanged();
                    }
                },false,true);
    }


}