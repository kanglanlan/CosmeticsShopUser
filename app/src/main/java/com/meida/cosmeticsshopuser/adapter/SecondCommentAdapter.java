package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.ActionBean;
import com.meida.cosmeticsshopuser.Bean.SecondCommentBean;
import com.meida.cosmeticsshopuser.MyView.MUIToast;
import com.meida.cosmeticsshopuser.MyView.dialog.CommentEditDialog;
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

/**
 * 作者 亢兰兰
 * 时间 2018/10/11 0011
 * 公司  郑州软盟
 */
/*动态的一级评论的子评论*/
public class SecondCommentAdapter extends CommonAdapter<SecondCommentBean.CommentItemBean> {
    private ArrayList<SecondCommentBean.CommentItemBean> datas = new ArrayList<>();
    Context mContext;

    public SecondCommentAdapter(Context context, int layoutId, ArrayList<SecondCommentBean.CommentItemBean> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        mContext = context;
    }

    public interface ReloadListener{
        void reLoad();
    }

    private ReloadListener reloadListener;

    public void setReloadListener(ReloadListener reloadListener) {
        this.reloadListener = reloadListener;
    }

    private String evalFirstId = "";
    public void setEvalFirstId(String evalFirstId) {
        this.evalFirstId = evalFirstId;
    }

    @Override
    public void convert(final ViewHolder holder, final SecondCommentBean.CommentItemBean bean, final int position) {
        ImageView img_pji = holder.getView(R.id.img_pji);
        TextView tv_pjname = holder.getView(R.id.tv_pjname);
        final CheckedTextView cb_zan = holder.getView(R.id.cb_zan);
        TextView tv_pjcontent = holder.getView(R.id.tv_pjcontent);
        TextView tv_pjtype = holder.getView(R.id.tv_pjtype);
        TextView tv_time = holder.getView(R.id.tv_time);
        TextView tv_huifu = holder.getView(R.id.tv_huifu);
        TextView tv_floor = holder.getView(R.id.floor);

        GlideUtils.loadHead(bean.getAvatar(),img_pji);

        String userId = bean.getOwnuserid()+"";
        String toId = bean.getTouid();
        String userNicknameStr = bean.getUser_nickname();
        tv_pjname.setText(userNicknameStr);
        if (userId.equals(toId)){
            tv_pjcontent.setText(bean.getContent());
        }else{
            String toNicknameStr = bean.getTouser_nickname();
            if (TextUtils.isEmpty(toNicknameStr)){
                tv_pjcontent.setText(bean.getContent());
            }else{
                StringBuilder builder = new StringBuilder();
                builder.append("@");
                builder.append(userNicknameStr);
                builder.append("回复：");
                builder.append(toNicknameStr);
                builder.append("  ");
                builder.append(bean.getContent());
                tv_pjcontent.setText(builder.toString());
            }
        }



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
        tv_pjtype.setText("");
        tv_time.setText(bean.getCreatetime());
        tv_huifu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publishPj(position);
            }
        });
        tv_floor.setText((position+1)+"楼");

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
                request.add("id",evalFirstId);
                request.add("touid",datas.get(position).getUid());
                CallServer.getRequestInstance().add(mContext, 0, request,
                        new CustomHttpListener<ActionBean>(mContext,true,ActionBean.class) {
                            @Override
                            public void doWork(ActionBean result, String code) {
                                try{
                                    MUIToast.show(mContext,result.getMsg());
                                    if (reloadListener!=null){
                                        reloadListener.reLoad();
                                    }
                                }catch (Exception e){

                                }
                            }
                        },false,true);
            }
        });
        commentEditDialog.show();

    }


}