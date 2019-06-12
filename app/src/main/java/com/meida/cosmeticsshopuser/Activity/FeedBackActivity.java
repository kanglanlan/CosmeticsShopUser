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
import android.view.View;
import android.widget.EditText;

import com.meida.cosmeticsshopuser.Bean.ActionBean;
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
import com.meida.cosmeticsshopuser.utils.ImageUtils;
import com.meida.cosmeticsshopuser.utils.MPermissionUtils;
import com.meida.cosmeticsshopuser.utils.SdcardUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.nereo.multi_image_selector.MultiImageSelector;

public class FeedBackActivity extends BaseActivity {

    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.bt_tijiao)
    View bt_tijiao;

    /*图片选择相关*/
    private static final int MAX_PIC_NUM = 3;
    private static final int REQUEST_IMAGE = 0x10;
    private static final int REQUEST_PERMISSIONS = 0x11;

    private String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private GridImgAdapter adapter;
    private ArrayList<String> data = new ArrayList<>();
    private LoadingDialog loadingDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);
        changeTitle("意见反馈");
        loadingDialog = new LoadingDialog(baseContext);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int screenWith = metric.widthPixels;
        int itemWidth = DisplayUtil.dp2px(120);
        int spanCount = screenWith / itemWidth;
        FullyGridLayoutManager flm = new FullyGridLayoutManager(baseContext, spanCount);
        flm.setOrientation(FullyGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(flm);
        adapter = new GridImgAdapter(baseContext,
                R.layout.item_grid_img, data, MAX_PIC_NUM);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        MPermissionUtils.requestPermissionsResult(baseContext, REQUEST_PERMISSIONS,
                permissions, new MPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {

                    }

                    @Override
                    public void onPermissionDenied() {
                        MPermissionUtils.showTipsDialog(baseContext);
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

                if (MPermissionUtils.checkPermissionAllGranted(baseContext, permissions)) {
                    pickImg(surplusNum);
                } else {
                    MPermissionUtils.requestPermissionsResult(baseContext, REQUEST_PERMISSIONS,
                            permissions, new MPermissionUtils.OnPermissionListener() {
                                @Override
                                public void onPermissionGranted() {
                                    pickImg(surplusNum);
                                }

                                @Override
                                public void onPermissionDenied() {
                                    MPermissionUtils.showTipsDialog(baseContext);
                                }
                            });
                }

            }
        });

        initData();

        bt_tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedback();
            }
        });

    }

    private void initData(){
        if (data.size() >= MAX_PIC_NUM) {

        } else {
            data.add("-9");
        }
        adapter.notifyDataSetChanged();
    }



    private void pickImg(int surplus) {
        MultiImageSelector selector = MultiImageSelector.create(baseContext);
        selector.showCamera(true);
        selector.multi();
        selector.count(surplus);
        selector.start(baseContext, REQUEST_IMAGE);
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
                MyRunnable runnable = new MyRunnable(pickData);
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


    private void feedback(){

        final String feedbackStr = etContent.getText().toString().trim();
        if (TextUtils.isEmpty(feedbackStr)){
            showtoa("请填写您的意见或建议");
            return;
        }


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mRequest01 = getRequest(Params.feedback,true);
                mRequest01.add("title",feedbackStr);
                mRequest01.add("user_type","2");
                if (data.size()>0){
                    try{
                        String imgsStr = "";
                        for (int i = 0; i<data.size(); i++){
                            String str = data.get(i);
                            if (str!=null && str.length()>5){
                                imgsStr = imgsStr+","+ FileToBase64Util.encodeBase64File(str);
                            }
                        }
                        if (imgsStr.length()>5){
                            mRequest01.add("img",imgsStr.substring(1,imgsStr.length()));
                        }
                    }catch (Exception e){

                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                                new CustomHttpListener<ActionBean>(baseContext,true,ActionBean.class) {
                                    @Override
                                    public void doWork(ActionBean result, String code) {
                                        showtoa(result.getMsg());
                                        finish();
                                    }
                                },true,true);
                    }
                });
            }
        });
        thread.start();


    }







}
