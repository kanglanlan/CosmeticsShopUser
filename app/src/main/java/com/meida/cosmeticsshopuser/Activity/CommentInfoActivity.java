package com.meida.cosmeticsshopuser.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.BaseBean;
import com.meida.cosmeticsshopuser.Bean.OrderEvalDetailBean;
import com.meida.cosmeticsshopuser.MyView.CircleImageView;
import com.meida.cosmeticsshopuser.MyView.CustomGridView;
import com.meida.cosmeticsshopuser.MyView.dialog.ActionDialog;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.GridInImgAdapter;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.meida.cosmeticsshopuser.utils.PreferencesUtils;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommentInfoActivity extends BaseActivity {

    private String id;

    @Bind(R.id.tv_title_right)
    TextView tvTitleRight;
    @Bind(R.id.img_pjinfo)
    CircleImageView imgPjinfo;
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.ratbar01_mc)
    ScaleRatingBar ratbar01Mc;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.gridView)
    CustomGridView gridView;
    private GridInImgAdapter adapter;
    private ArrayList<String> data = new ArrayList<>();

    private ActionDialog deleteDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_info);
        ButterKnife.bind(this);
        changeTitle("评价详情","删除评价");
        id = getIntent().getStringExtra("id");

        adapter = new GridInImgAdapter(baseContext,R.layout.img_95,data);
        gridView.setAdapter(adapter);

        getDetail();


        deleteDialog = new ActionDialog(baseContext,"确定要删除此条评价吗？");
        deleteDialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
                deletePJ();
            }
        });

    }

    @OnClick({R.id.tv_title_right})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_title_right:
                deleteDialog.show();
                break;

        }
    }

    /*获取订单评价*/
    private void getDetail(){
        mRequest01 = getRequest(Params.getMyEvalDetail,true);
        mRequest01.add("id",id);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<OrderEvalDetailBean>(baseContext,true,OrderEvalDetailBean.class) {
                    @Override
                    public void doWork(OrderEvalDetailBean result, String code) {
                        try{
                            String userHead = PreferencesUtils.getString(baseContext,Params.KEY_USERHEAD,"");
                            String userName = PreferencesUtils.getString(baseContext,Params.KEY_NICKNAME,"");
                            GlideUtils.loadHead(userHead,imgPjinfo);
                            tvUsername.setText(userName);
                            tvTime.setText(result.getData().getCreatetime());
                            FormatterUtil.setStarRating(result.getData().getGoods(),ratbar01Mc);
                            tvContent.setText(result.getData().getContent());
                            String imgsStr = result.getData().getImgs();
                            if (!TextUtils.isEmpty(imgsStr)){
                                String[] imgArr = imgsStr.split(",");
                                List<String> imgList = Arrays.asList(imgArr);
                                data.clear();
                                data.addAll(imgList);
                                adapter.notifyDataSetChanged();
                            }
                        }catch (Exception e){

                        }
                    }
                },false,true);


    }


    /*删除评价*/
    private void deletePJ(){
        mRequest02 = getRequest(Params.deleteMyEval,true);
        mRequest02.add("id",id);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest02,
                new CustomHttpListener<BaseBean>(baseContext,true,BaseBean.class) {
                    @Override
                    public void doWork(BaseBean result, String code) {
                        showtoa(result.getMsg());
                        finish();
                    }
                },false,true);


    }

}
