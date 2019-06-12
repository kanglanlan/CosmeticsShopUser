package com.meida.cosmeticsshopuser.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.ActionBean;
import com.meida.cosmeticsshopuser.Bean.FindDetailBean;
import com.meida.cosmeticsshopuser.MyView.CircleImageView;
import com.meida.cosmeticsshopuser.MyView.CustomGridView;
import com.meida.cosmeticsshopuser.MyView.dialog.CommentEditDialog;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.viewpager.FindPJAdapter;
import com.meida.cosmeticsshopuser.base.App;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.CommonUtil;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.meida.cosmeticsshopuser.utils.ProjectUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FindInfoActivity extends BaseActivity {

    private String detailId = "",userid = "";

    CircleImageView img;
    TextView time, liulan, content, zannum, tv_guanzhu, tv_nicheng;
    CustomGridView gv_img;
    @Bind(R.id.smart)
    SmartRefreshLayout smart;
    @Bind(R.id.recycle_list)
    RecyclerView recycleLisst;
    private FindPJAdapter pjAdapter;
    private ArrayList<FindDetailBean.CommentItemBean> pjData = new ArrayList<>();
    private CommentEditDialog commentEditDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_info);
        ButterKnife.bind(this);
        detailId = getIntent().getStringExtra("id");
        changeTitle("详情");
        initview();
        getDetail();
    }

    private void initview() {
        linearLayoutManager = new LinearLayoutManager(this);
        recycleLisst.setLayoutManager(linearLayoutManager);
        recycleLisst.setFocusableInTouchMode(false);
        pjAdapter = new FindPJAdapter(baseContext,R.layout.item_findpingjia,pjData);
        recycleLisst.setAdapter(pjAdapter);
        recycleLisst.setNestedScrollingEnabled(false);
        View view = findViewById(R.id.findTop);
                //View.inflate(this, R.layout.layout_findtop, null);
        img = (CircleImageView) view.findViewById(R.id.img_photo);
        time = (TextView) view.findViewById(R.id.tv_time);
        tv_nicheng = (TextView) view.findViewById(R.id.tv_name);
        liulan = (TextView) view.findViewById(R.id.tv_liulan);
        content = (TextView) view.findViewById(R.id.tv_content);
        zannum = (TextView) view.findViewById(R.id.tv_zxan);

        tv_guanzhu = (TextView) view.findViewById(R.id.tv_guanzhu);
        gv_img = (CustomGridView) view.findViewById(R.id.gv_faxainpic);
        smart.setEnableRefresh(true);
        smart.setEnableLoadMore(true);
        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadData(false);
            }
        });
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData(true);
            }
        });
        zannum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (needLogin()){
                    return;
                }
                updateFindZanState();
            }
        });

        tv_guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (needLogin()){
                    return;
                }
                updateFollowState();
            }
        });

        commentEditDialog = new CommentEditDialog(baseContext);
        commentEditDialog.setDialogViewListener(new CommentEditDialog.DialogViewListener() {
            @Override
            public void onListSureClick(View view, String content) {
                publishFindPj(content);
            }
        });

        findViewById(R.id.toLiuyan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (needLogin()){
                    return;
                }
                if (!commentEditDialog.isShowing()){
                    commentEditDialog.show();
                }
            }
        });
    }


    /*发现详情*/
    private void getDetail(){
        mRequest01 = getRequest(Params.getFindDetail,true);
        mRequest01.add("page",1);
        mRequest01.add("id",detailId);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<FindDetailBean>(baseContext,true,FindDetailBean.class) {
                    @Override
                    public void doWork(FindDetailBean result, String code) {
                        try{
                            if (result.getData().getShopinfo()!=null){
                                GlideUtils.loadHead(result.getData().getShopinfo().getAvatar(),img);
                                tv_nicheng.setText(result.getData().getShopinfo().getTitle());
                                userid = result.getData().getShopinfo().getId();
                            }else if (result.getData().getUserinfo()!=null){
                                GlideUtils.loadHead(result.getData().getUserinfo().getAvatar(),img);
                                tv_nicheng.setText(result.getData().getUserinfo().getTitle());
                                userid = result.getData().getUserinfo().getId();
                            }else{
                                img.setImageResource(R.drawable.ico_img115);
                                tv_nicheng.setText("匿名");
                            }

                            /*是否关注  0 未关注  1已关注*/
                            if("1".equals(result.getData().getFollow())){
                                tv_guanzhu.setText("已关注");
                            }else{
                                tv_guanzhu.setText("+关注");
                            }

                            /*时间*/
                            time.setText(result.getData().getCreatetime());
                            /*浏览量*/
                            liulan.setText(FormatterUtil.formatNumber(result.getData().getHits()));
                            /*内容*/
                            content.setText(result.getData().getContent());
                            /*图片*/
                            String imgsStr = result.getData().getImgs();
                            if (!TextUtils.isEmpty(imgsStr)){
                                String[] imgArr = imgsStr.split(",");
                                final List<String> imgList = Arrays.asList(imgArr);
                                if (imgList!=null && imgList.size()>0){
                                    int layoutId = R.layout.item_img;
                                    final int numColums;
                                    if (imgList.size()==1){
                                        layoutId = R.layout.item_img_large;
                                        gv_img.setNumColumns(1);
                                        numColums = 1;
                                    }else if (imgList.size()==4){
                                        gv_img.setNumColumns(2);
                                        numColums = 2;
                                    }else{
                                        gv_img.setNumColumns(3);
                                        numColums = 3;
                                    }
                                    final int layId = layoutId;
                                    gv_img.setAdapter(new com.zhy.adapter.abslistview.CommonAdapter<String>(
                                            baseContext, layoutId, imgList) {
                                        @Override
                                        protected void convert(com.zhy.adapter.abslistview.ViewHolder viewHolder,
                                                               String item, final int position) {
                                            ImageView img = viewHolder.getView(R.id.img);
                                            if (layId==R.layout.item_img){
                                                int wid = (App.wid - (CommonUtil.dip2px(mContext, 95))) / numColums;
                                                //int wid = (CommonUtil.dip2px(mContext, 45));
                                                CommonUtil.setwidhe(img, wid, wid);
                                            }
                                            GlideUtils.loadImg(item,img);
                                            img.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    ProjectUtils.ShowLargeImg(mContext, imgList, position);
                                                }
                                            });

                                        }
                                    });
                                }
                            }

                            /*是否点赞*/
                            if ("1".equals(result.getData().getShow())){
                                zannum.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ico_wnm_241,0,0);
                            }else{
                                zannum.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ico_wnm_240,0,0);
                            }
                            /*点赞量*/
                            zanValue = result.getData().getLike();
                            zannum.setText(FormatterUtil.formatNumber(zanValue));
                            /*评论列表*/
                            List<FindDetailBean.CommentItemBean> commentItemBeans = result.getData().getCommentlist().getData();
                            if (commentItemBeans!=null && commentItemBeans.size()>0){
                                pjData.clear();
                                pjData.addAll(commentItemBeans);
                                pjAdapter.notifyDataSetChanged();
                            }
                            pager++;

                        }catch (Exception e){

                        }
                    }

                    @Override
                    public void onFinally(JSONObject obj, String code, boolean isNetSucceed) {
                        super.onFinally(obj, code, isNetSucceed);
                        smart.finishLoadMore(true);
                        smart.finishRefresh(true);
                    }
                },false,true);


    }

    /*加载更多*/
    private void loadData(final boolean isRefresh){
        if (isRefresh){
            pager = 1;
        }
        mRequest02 = getRequest(Params.getFindDetail,true);
        mRequest02.add("page",pager);
        mRequest02.add("id",detailId);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest02,
                new CustomHttpListener<FindDetailBean>(baseContext,true,FindDetailBean.class) {
                    @Override
                    public void doWork(FindDetailBean result, String code) {
                        try{
                            /*评论列表*/
                            List<FindDetailBean.CommentItemBean> commentItemBeans = result.getData().getCommentlist().getData();
                            if (isRefresh){
                                pjData.clear();
                            }
                            if (commentItemBeans!=null && commentItemBeans.size()>0){
                                pjData.addAll(commentItemBeans);
                                smart.setNoMoreData(false);
                            }else{
                                smart.setNoMoreData(true);
                            }
                            pjAdapter.notifyDataSetChanged();
                            pager++;

                        }catch (Exception e){

                        }
                    }

                    @Override
                    public void onFinally(JSONObject obj, String code, boolean isNetSucceed) {
                        super.onFinally(obj, code, isNetSucceed);
                        smart.finishLoadMore(true);
                        smart.finishRefresh(true);
                    }

                },false,false);
    }

    private long zanValue = 0;
    /*点赞这条发现*/
    private void updateFindZanState(){
        mRequest03 = getRequest(Params.updateFindZanState,true);
        mRequest03.add("id",detailId);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest03,
                new CustomHttpListener<ActionBean>(baseContext,true,ActionBean.class) {
                    @Override
                    public void doWork(ActionBean result, String code) {
                        try{
                            showtoa(result.getMsg());
                            if (result.getMsg().contains("取消")){
                                zanValue = zanValue-1>0?zanValue-1:0;
                                zannum.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ico_wnm_240,0,0);
                            }else{
                                zanValue = zanValue+1;
                                zannum.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ico_wnm_241,0,0);
                            }
                            zannum.setText(FormatterUtil.formatNumber(zanValue));
                        }catch (Exception e){

                        }
                    }
                },false,true);
    }

    /*发布评论*/
    private void publishFindPj(String content){
        if (TextUtils.isEmpty(content)){
            showtoa("请输入您要评价的内容");
            return;
        }
        mRequest04 = getRequest(Params.publishFindPJ,true);
        mRequest04.add("content",content);
        mRequest04.add("id",detailId);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest04,
                new CustomHttpListener<ActionBean>(baseContext,true,ActionBean.class) {
                    @Override
                    public void doWork(ActionBean result, String code) {
                        showtoa(result.getMsg());
                        loadData(true);
                        if (commentEditDialog!=null){
                            commentEditDialog.clearContent();
                        }
                    }
                },true,true);

    }

    /*关注这个用户*/
    private void updateFollowState(){
        mRequest05 = getRequest(Params.updateFollowState,true);
        mRequest05.add("id",detailId);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest05,
                new CustomHttpListener<ActionBean>(baseContext,true,ActionBean.class) {
                    @Override
                    public void doWork(ActionBean result, String code) {
                        showtoa(result.getMsg());
                        if (result.getMsg().contains("取消")){
                            tv_guanzhu.setText("+关注");
                        }else{
                            tv_guanzhu.setText("已关注");
                        }
                    }
                },false,true);
    }


}
