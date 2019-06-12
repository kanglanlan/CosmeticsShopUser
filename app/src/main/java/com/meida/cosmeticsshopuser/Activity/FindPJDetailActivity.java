package com.meida.cosmeticsshopuser.Activity;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.ActionBean;
import com.meida.cosmeticsshopuser.Bean.FindDetailBean;
import com.meida.cosmeticsshopuser.Bean.SecondCommentBean;
import com.meida.cosmeticsshopuser.MyView.CircleImageView;
import com.meida.cosmeticsshopuser.MyView.dialog.CommentEditDialog;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.SecondCommentAdapter;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.meida.cosmeticsshopuser.utils.ProjectUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yolanda.nohttp.rest.Request;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FindPJDetailActivity extends BaseActivity {

    private String id = "";/*评论id*/
    private FindDetailBean.CommentItemBean pjBean;/*评论实体类*/

    private CircleImageView userHead;
    private TextView userName;
    private CheckedTextView zanSec;
    private TextView content;
    private TextView time;
    private TextView reply;
    private SmartRefreshLayout smart;
    private RecyclerView recyclerView;
    private ArrayList<SecondCommentBean.CommentItemBean> data = new ArrayList<>();
    private SecondCommentAdapter adapter;
    private TextView writeComment;
    private CommentEditDialog commentEditDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pjdetail);
        pjBean = (FindDetailBean.CommentItemBean)
                getIntent().getSerializableExtra("data");
        id = pjBean.getId();
        changeTitle("评论详情");
        initView();
        initEvent();
        initData();
    }


    private void initView() {
        userHead = (CircleImageView) findViewById(R.id.userHead);
        userName = (TextView) findViewById(R.id.userName);
        zanSec = (CheckedTextView) findViewById(R.id.zanSec);
        content = (TextView) findViewById(R.id.content);
        time = (TextView) findViewById(R.id.time);
        reply = (TextView) findViewById(R.id.reply);
        smart = (SmartRefreshLayout)findViewById(R.id.smart);
        recyclerView = (android.support.v7.widget.RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager lm1 = new LinearLayoutManager(baseContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(lm1);
        adapter = new SecondCommentAdapter(baseContext,R.layout.item_findpingjia2,data);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
        adapter.setEvalFirstId(id);
        writeComment = (TextView) findViewById(R.id.writeComment);
        try{
            GlideUtils.loadHead(pjBean.getAvatar(),userHead);
            userName.setText(pjBean.getUser_nickname());
            if ("1".equals(pjBean.getShow())){
                zanSec.setChecked(true);
            }else{
                zanSec.setChecked(false);
            }
            zanValue = pjBean.getLike();
            zanSec.setText(FormatterUtil.formatNumber(pjBean.getLike()));
            content.setText(pjBean.getContent());
            time.setText(pjBean.getCreatetime());
        }catch (Exception e){

        }

        commentEditDialog = new CommentEditDialog(baseContext);
        commentEditDialog.setDialogViewListener(new CommentEditDialog.DialogViewListener() {
            @Override
            public void onListSureClick(View view, String content) {
                publishFindPj(content);
            }
        });

        smart.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadData(false,false);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData(true,false);
            }
        });


    }

    private void initEvent() {

        zanSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (needLogin()){
                    return;
                }
                updateZanState();
            }
        });

        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentEditDialog.show();
            }
        });
        writeComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentEditDialog.show();
            }
        });
        adapter.setReloadListener(new SecondCommentAdapter.ReloadListener() {
            @Override
            public void reLoad() {
                loadData(true,false);
            }
        });
    }

    private void initData() {
        loadData(true,true);
    }

    /*TODO 写评论*/
    private void publishFindPj(String content){
        if (TextUtils.isEmpty(content)){
            showtoa("请输入您要评价的内容");
            return;
        }
        mRequest01 = getRequest(Params.publishPJPJ,true);
        mRequest01.add("content",content);
        mRequest01.add("id",id);
        mRequest01.add("touid",pjBean.getUid());
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<ActionBean>(baseContext,true,ActionBean.class) {
                    @Override
                    public void doWork(ActionBean result, String code) {
                        showtoa(result.getMsg());
                        loadData(true,false);
                        if (commentEditDialog!=null){
                            commentEditDialog.clearContent();
                        }
                    }
                },true,true);
    }


    private long zanValue;
    /*点赞评论*/
    private void updateZanState(){
        Request<String> request = ProjectUtils.getRequest(baseContext, Params.updatePjZanState,true);
        request.add("id",id);
        CallServer.getRequestInstance().add(baseContext, 0, request,
                new CustomHttpListener<ActionBean>(baseContext,true,ActionBean.class) {
                    @Override
                    public void doWork(ActionBean result, String code) {
                        if (result.getMsg().contains("取消")){
                            zanValue = zanValue-1>0?zanValue-1:0;
                            zanSec.setText(FormatterUtil.formatNumber(zanValue));
                            zanSec.setChecked(false);
                        }else{
                            zanValue = zanValue+1;
                            zanSec.setText(FormatterUtil.formatNumber(zanValue));
                            zanSec.setChecked(true);
                        }
                        //notifyDataSetChanged();
                    }
                },false,true);
    }

    /*获取子评论*/
    private void loadData(final boolean isRefresh,boolean showLoading){
        if (isRefresh){
            pager = 1;
        }
        mRequest02 = getRequest(Params.getPJPJList,true);
        mRequest02.add("id",id);
        mRequest02.add("page",pager);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest02,
                new CustomHttpListener<SecondCommentBean>
                        (baseContext,true,SecondCommentBean.class) {
                    @Override
                    public void doWork(SecondCommentBean result, String code) {
                        try{
                            List<SecondCommentBean.CommentItemBean> beans = result.getData().getData();
                            if (pager==1){
                                data.clear();
                            }

                            if (beans!=null && beans.size()>0){
                                data.addAll(beans);
                                smart.setNoMoreData(false);
                            }else{
                                smart.resetNoMoreData();
                            }

                            adapter.notifyDataSetChanged();
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
                },false,showLoading);

    }



}
