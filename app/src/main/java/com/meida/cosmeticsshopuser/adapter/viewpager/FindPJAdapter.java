package com.meida.cosmeticsshopuser.adapter.viewpager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.ActionBean;
import com.meida.cosmeticsshopuser.Bean.FindDetailBean;
import com.meida.cosmeticsshopuser.MyView.MUIToast;
import com.meida.cosmeticsshopuser.MyView.dialog.CommentEditDialog;
import com.meida.cosmeticsshopuser.Activity.FindPJDetailActivity;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.DisplayUtil;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.meida.cosmeticsshopuser.utils.ProjectUtils;
import com.yolanda.nohttp.rest.Request;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 亢兰兰
 * 时间 2018/10/11 0011
 * 公司  郑州软盟
 */
/*R.layout.item_findpingjia*/
public class FindPJAdapter extends CommonAdapter<FindDetailBean.CommentItemBean> {
    private ArrayList<FindDetailBean.CommentItemBean> datas = new ArrayList<FindDetailBean.CommentItemBean>();
    Context mContext;

    public FindPJAdapter(Context context, int layoutId, ArrayList<FindDetailBean.CommentItemBean> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        mContext = context;
    }

    @Override
    public void convert(final ViewHolder holder, final FindDetailBean.CommentItemBean bean, final int position) {

        ImageView img_pji = holder.getView(R.id.img_pji);
        TextView tv_pjname = holder.getView(R.id.tv_pjname);
        final CheckedTextView cb_zan = holder.getView(R.id.cb_zan);
        TextView tv_pjcontent = holder.getView(R.id.tv_pjcontent);
        TextView tv_pjtype = holder.getView(R.id.tv_pjtype);
        TextView tv_time = holder.getView(R.id.tv_time);
        TextView tv_huifu = holder.getView(R.id.tv_huifu);
        TextView tv_huifunum = holder.getView(R.id.tv_huifunum);
        TextView evalNum = holder.getView(R.id.evalNum);
        TextView tv_floor = holder.getView(R.id.floor);

        GlideUtils.loadHead(bean.getAvatar(),img_pji);
        tv_pjname.setText(bean.getUser_nickname());
        if ("1".equals(bean.getShow())){
            cb_zan.setChecked(true);
        }else{
            cb_zan.setChecked(false);
        }
        cb_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEventCollectState(position,cb_zan,bean.getLike());
            }
        });
        cb_zan.setText(FormatterUtil.formatNumber(bean.getLike()));

        tv_pjcontent.setText(bean.getContent());
        tv_pjtype.setText("");
        tv_time.setText(bean.getCreatetime());

        StringBuilder builder = new StringBuilder();
        builder.append("查看");
        builder.append(FormatterUtil.formatNumber(bean.getComment()));/*TODO 0条评论*/
        builder.append("条评论");
        tv_huifunum.setText(builder.toString());
        tv_floor.setText((position+1)+"楼");

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, FindPJDetailActivity.class);
                intent.putExtra("data",bean);
                mContext.startActivity(intent);
            }
        });

        LinearLayout childParent = holder.getView(R.id.childParent);
        View seeAllEval = holder.getView(R.id.seeAllEval);
        List<FindDetailBean.CommentChild> childList = bean.getChild();
        addCommentChild(childParent,seeAllEval,childList);
        /*tv_huifu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publishPj(position);
            }
        });*/

    }


    private void addCommentChild(LinearLayout childParent,View other, List<FindDetailBean.CommentChild> childList){
        if (childList.size()>0){
            childParent.removeAllViews();
            childParent.setVisibility(View.VISIBLE);
            other.setVisibility(View.VISIBLE);
            for (int i = 0; i<childList.size(); i++){
                TextView textView = new TextView(mContext);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                /*lp.setMargins(DisplayUtil.dp2px(6),0,0,0);*/
                textView.setLayoutParams(lp);
                textView.setPadding(0,DisplayUtil.dp2px(3),0,0);
                textView.setText(String.format("%s：%s", childList.get(i).getUser_nickname(), childList.get(i).getContent()));
                textView.setMaxLines(3);
                textView.setTextColor(Color.parseColor("#999999"));
                textView.setTextSize(13);
                textView.setLineSpacing(0.0f,1.1f);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                childParent.addView(textView);
            }
        }else{
            childParent.setVisibility(View.GONE);
            other.setVisibility(View.GONE);
        }
    }

    /*点赞评价*/
    private void updateEventCollectState(final int position, final CheckedTextView checkBox,final long num1){/*true  原本 没有收藏  */

        Request<String> request = ProjectUtils.getRequest(mContext, Params.updatePjZanState,true);
        request.add("id",datas.get(position).getId());
        CallServer.getRequestInstance().add(mContext, 0, request,
                new CustomHttpListener<ActionBean>(mContext,true,ActionBean.class) {
                    @Override
                    public void doWork(ActionBean result, String code) {
                        long num = num1;
                        if (result.getMsg().contains("取消")){
                            num = num1-1>0?num1-1:0;
                            datas.get(position).setShow("");
                            datas.get(position).setLike(num);
                            checkBox.setText(FormatterUtil.formatNumber(num));
                            checkBox.setChecked(false);
                        }else{
                            num = num1+1;
                            datas.get(position).setShow("1");
                            datas.get(position).setLike(num);
                            checkBox.setText(FormatterUtil.formatNumber(num));
                            checkBox.setChecked(true);
                        }
                        //notifyDataSetChanged();
                    }
                },false,true);
    }

    /*评论发布评论*/
    private void publishPj(final int position){
        CommentEditDialog commentEditDialog = new CommentEditDialog(mContext);
        commentEditDialog.setDialogViewListener(new CommentEditDialog.DialogViewListener() {
            @Override
            public void onListSureClick(View view, String content) {
                final Request<String> request = ProjectUtils.getRequest(mContext,Params.publishPJPJ,true);
                request.add("content",content);
                request.add("id",datas.get(position).getId());
                request.add("touid",datas.get(position).getUid());
                CallServer.getRequestInstance().add(mContext, 0, request,
                        new CustomHttpListener<ActionBean>(mContext,true,ActionBean.class) {
                            @Override
                            public void doWork(ActionBean result, String code) {
                                try{
                                    MUIToast.show(mContext,result.getMsg());
                                }catch (Exception e){

                                }
                            }
                        },false,true);
            }
        });
        commentEditDialog.show();

    }


}