package com.meida.cosmeticsshopuser.Activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.meida.cosmeticsshopuser.Bean.ActionBean;
import com.meida.cosmeticsshopuser.Bean.eventbus.PaySuccess;
import com.meida.cosmeticsshopuser.MyView.Loading.LoadingDialog;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.FullyGridLayoutManager;
import com.meida.cosmeticsshopuser.adapter.GridImgAdapter;
import com.meida.cosmeticsshopuser.base.App;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.HttpIp;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.DisplayUtil;
import com.meida.cosmeticsshopuser.utils.FileToBase64Util;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.meida.cosmeticsshopuser.utils.ImageUtils;
import com.meida.cosmeticsshopuser.utils.MPermissionUtils;
import com.meida.cosmeticsshopuser.utils.SdcardUtils;
import com.willy.ratingbar.ScaleRatingBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;

public class WritCommentActivity extends BaseActivity {

    private String orderid = "";
    private String shopImgPath = "";

    @Bind(R.id.img_pj)
    ImageView imgPj;
    @Bind(R.id.ratbar01_mc)
    ScaleRatingBar ratbar01Mc;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.cb_pj)
    CheckBox cbPj;
    @Bind(R.id.ratbar1)
    ScaleRatingBar ratbar1;
    @Bind(R.id.ratbar2)
    ScaleRatingBar ratbar2;
    @Bind(R.id.ratbar3)
    ScaleRatingBar ratbar3;
    @Bind(R.id.ratbar4)
    ScaleRatingBar ratbar4;

    /*图片选择相关*/
    private static final int MAX_PIC_NUM = 9;
    private static final int REQUEST_IMAGE = 0x10;
    private static final int REQUEST_PERMISSIONS = 0x11;

    private String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private RecyclerView recyclerView;
    private GridImgAdapter adapter;
    private ArrayList<String> data = new ArrayList<>();
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writ_comment);
        ButterKnife.bind(this);
        changeTitle("评价晒单");
        orderid = getIntent().getStringExtra("id");
        shopImgPath = getIntent().getStringExtra("img");
        String starValue = getIntent().getStringExtra("star")+"";
        FormatterUtil.setStarRating(starValue,ratbar01Mc);
        GlideUtils.loadShop(shopImgPath,imgPj);
        loadingDialog = new LoadingDialog(WritCommentActivity.this);

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int screenWith = metric.widthPixels;
        int itemWidth = DisplayUtil.dp2px(120);
        int spanCount = screenWith / itemWidth;
        recyclerView = (android.support.v7.widget.RecyclerView) findViewById(R.id.recyclerView);
        FullyGridLayoutManager flm = new FullyGridLayoutManager(WritCommentActivity.this, spanCount);
        flm.setOrientation(FullyGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(flm);
        adapter = new GridImgAdapter(WritCommentActivity.this,
                R.layout.item_grid_img, data, MAX_PIC_NUM);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        etContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (v.getId()) {
                    case R.id.et_content:
                        // 解决scrollView中嵌套EditText导致不能上下滑动的问题
                        if (canVerticalScroll(etContent))
                            v.getParent().requestDisallowInterceptTouchEvent(true);
                        switch (event.getAction() & MotionEvent.ACTION_MASK) {
                            case MotionEvent.ACTION_UP:
                                v.getParent().requestDisallowInterceptTouchEvent(false);
                                break;
                        }
                }
                return false;
            }
        });

        MPermissionUtils.requestPermissionsResult(WritCommentActivity.this, REQUEST_PERMISSIONS,
                permissions, new MPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {

                    }

                    @Override
                    public void onPermissionDenied() {
                        MPermissionUtils.showTipsDialog(WritCommentActivity.this);
                    }
                });

        adapter.setOnMyItemClickListener(new GridImgAdapter.onMyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onPermissionRequest() {

            }

            @Override
            public void onDelete(int position) {
                if (position != RecyclerView.NO_POSITION) {
                    data.remove(position);
                    if ("-9".equals(data.get(data.size() - 1))) {

                    } else {
                        data.add("-9");
                    }
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, data.size());
                } else {
                    if ("-9".equals(data.get(data.size() - 1))) {

                    } else {
                        data.add("-9");
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onAdd(final int surplusNum) {
                if (MPermissionUtils.checkPermissionAllGranted(WritCommentActivity.this, permissions)) {
                    pickImg(surplusNum);
                } else {
                    MPermissionUtils.requestPermissionsResult(WritCommentActivity.this, REQUEST_PERMISSIONS,
                            permissions, new MPermissionUtils.OnPermissionListener() {
                                @Override
                                public void onPermissionGranted() {
                                    pickImg(surplusNum);
                                }

                                @Override
                                public void onPermissionDenied() {
                                    MPermissionUtils.showTipsDialog(WritCommentActivity.this);
                                }
                            });
                }
            }
        });
        initData();

    }

    public static  boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() -editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if(scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }

    @OnClick(R.id.bt_pj)
    public void onViewClicked() {
        submitData();
    }


    private void initData() {
        if (data.size() >= MAX_PIC_NUM) {

        } else {
            data.add("-9");
        }
        adapter.notifyDataSetChanged();
    }


    private void pickImg(int surplus) {
        MultiImageSelector selector = MultiImageSelector.create(WritCommentActivity.this);
        selector.showCamera(true);
        selector.multi();
        selector.count(surplus);
        selector.start(WritCommentActivity.this, REQUEST_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intentData) {
        super.onActivityResult(requestCode, resultCode, intentData);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                loadingDialog.show();
                data.remove(data.size() - 1);
                List<String> pickData = intentData.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                WritCommentActivity.MyRunnable runnable = new WritCommentActivity.MyRunnable(pickData);
                runnable.run();
            }
        }
    }


    /**
     * 另起线程压缩图片
     */
    public static final String FILE_DIR_NAME = "com.meida.cosmeticsshopuser";//应用缓存地址
    public static final String FILE_IMG_NAME = "images";//放置图片缓存
    class MyRunnable implements Runnable {

        List<String> images;

        public MyRunnable(List<String> images) {
            this.images = images;
        }

        @Override
        public void run() {
            SdcardUtils sdcardUtils = new SdcardUtils();
            String filePath;
            Bitmap newBitmap;
            for (int i = 0; i < images.size(); i++) {
                String str = images.get(i);
                /*if (str.contains(App.getInstance().getString(R.string.glide_plus_icon_string))) {//选择的图片*/
                if (!str.startsWith(HttpIp.http)) {//选择的图片
                    //压缩
                    newBitmap = ImageUtils.compressScaleByWH(images.get(i),
                            480,800);
                    //文件地址
                    filePath = sdcardUtils.getSDPATH() + FILE_DIR_NAME + "/"
                            + FILE_IMG_NAME + "/" + String.format("img_%d.jpg", System.currentTimeMillis());
                    //保存图片
                    ImageUtils.save(newBitmap, filePath, Bitmap.CompressFormat.JPEG, true);

                    if (data.size() < MAX_PIC_NUM) {
                        data.add(filePath);
                    }

                } else {

                    if (data.size() < MAX_PIC_NUM) {
                        data.add(str);
                    }
                }

            }

            if (data.size() >= MAX_PIC_NUM) {

            } else {
                data.add("-9");
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingDialog.dismiss();
                    adapter.notifyDataSetChanged();

                }
            });

        }
    }


    /*评价订单*/
    private void submitData() {

        final String commentStr = etContent.getText().toString().trim();
        if (TextUtils.isEmpty(commentStr)) {
            showtoa("请输入评价内容");
            return;
        }

        Thread thread  =  new Thread(new Runnable() {
            @Override
            public void run() {
                float score1 = ratbar1.getRating();
                float score2 = ratbar2.getRating();
                float score3 = ratbar3.getRating();
                float score4 = ratbar4.getRating();

                StringBuilder builder = new StringBuilder();
                if (data.size() > 0) {
                    try {
                        for (int i = 0; i < data.size(); i++) {
                            String path = data.get(i);
                            if ((!TextUtils.isEmpty(path)) && (path.length() > 5)) {
                                builder.append(",").append(FileToBase64Util.encodeBase64File(data.get(i)));
                            }
                        }
                    } catch (Exception e) {

                    }
                }

                String imgsStr = "";
                if (builder.toString().trim().length() > 5) {
                    imgsStr = builder.toString().trim().substring(1, builder.toString().trim().length());
                }
                mRequest01 = getRequest(Params.evalOrder, true);
                mRequest01.add("content", commentStr);
                mRequest01.add("imgs", imgsStr);
                if (cbPj.isChecked()) {
                    mRequest01.add("anonymous", "1");
                } else {
                    mRequest01.add("anonymous", "");
                }
                //mRequest01.add("match",score);/*少一个总评分*/
                mRequest01.add("match", score1);
                mRequest01.add("goods", score2);
                mRequest01.add("attitude", score3);
                mRequest01.add("speed", score4);
                mRequest01.add("id", orderid);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                                new CustomHttpListener<ActionBean>(baseContext, true, ActionBean.class) {
                                    @Override
                                    public void doWork(ActionBean result, String code) {
                                        showtoa(result.getMsg());
                                        PaySuccess refresh = new PaySuccess();
                                        refresh.setB(false);
                                        refresh.setEvalSuccess(true);
                                        EventBus.getDefault().post(refresh);
                                        finish();
                                    }
                                }, false, true);
                    }
                });
            }
        });

       thread.start();

    }


}
