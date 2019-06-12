package com.meida.cosmeticsshopuser.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import com.meida.cosmeticsshopuser.Bean.ActionBean;
import com.meida.cosmeticsshopuser.Bean.BaseBean;
import com.meida.cosmeticsshopuser.Bean.FindTypeBean;
import com.meida.cosmeticsshopuser.Bean.PublishFindTypeBean;
import com.meida.cosmeticsshopuser.Bean.eventbus.SuccessEvent;
import com.meida.cosmeticsshopuser.MyView.Loading.LoadingDialog;
import com.meida.cosmeticsshopuser.MyView.dialog.ActionDialog;
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
import com.meida.cosmeticsshopuser.utils.GlideCacheUtil;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.meida.cosmeticsshopuser.utils.ImageUtils;
import com.meida.cosmeticsshopuser.utils.LoggerUtil;
import com.meida.cosmeticsshopuser.utils.MPermissionUtils;
import com.meida.cosmeticsshopuser.utils.PreferencesUtils;
import com.meida.cosmeticsshopuser.utils.SdcardUtils;
import com.meida.cosmeticsshopuser.utils.SoftHideKeyBoardUtil;


import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;

public class AddFindActivity extends BaseActivity {

    private TextView cancel,publish;
    private EditText editText;
    private LinearLayout typeView;
    private TextView type;
    private CheckBox addrCheckBox;
    private LinearLayout addrView;
    private TextView addr;

    /*图片选择相关*/
    private static final int REQUEST_MAP = 0x33;
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

    /*选择分类弹框*/
    private OptionsPickerView typePicker;
    private List<FindTypeBean.TypeBean> typeData = new ArrayList<>();
    private List<String> typeStrData = new ArrayList<>();
    private int typeIndex = -1;

    /*退出编辑提示框*/
    private ActionDialog cancelDialog;
    /*存草稿 提示框*/
    private ActionDialog saveDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_find);
        SoftHideKeyBoardUtil.assistActivity(this);
        initView();
        initEvent();
        initData();
    }

    private void initView(){
        loadingDialog = new LoadingDialog(AddFindActivity.this);

        cancel = (TextView) findViewById(R.id.cancel);
        publish = (TextView) findViewById(R.id.publish);
        editText = (EditText) findViewById(R.id.editText);

        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (v.getId()) {
                    case R.id.editText:
                        // 解决scrollView中嵌套EditText导致不能上下滑动的问题
                        if (canVerticalScroll(editText))
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

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int screenWith = metric.widthPixels;
        int itemWidth = DisplayUtil.dp2px(120);
        int spanCount = screenWith / itemWidth;
        recyclerView = (android.support.v7.widget.RecyclerView) findViewById(R.id.recyclerView);
        FullyGridLayoutManager flm = new FullyGridLayoutManager(AddFindActivity.this, spanCount);
        flm.setOrientation(FullyGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(flm);
        adapter = new GridImgAdapter(AddFindActivity.this,
                R.layout.item_grid_img, data, MAX_PIC_NUM);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        typeView = (LinearLayout) findViewById(R.id.typeView);
        type = (TextView) findViewById(R.id.type);
        addrCheckBox = (CheckBox) findViewById(R.id.addrCheckBox);
        addrView = (LinearLayout) findViewById(R.id.addrView);
        addr = (TextView) findViewById(R.id.addr);
        initTypePicker();

        if (Params.aMapLocation!=null){
            LoggerUtil.e("aMapLocation",Params.aMapLocation.toString());
            addr.setText(Params.aMapLocation.getDescription());
            longitude = String.valueOf(Params.aMapLocation.getLongitude());
            latitude = String.valueOf(Params.aMapLocation.getLatitude());
        }

        cancelDialog = new ActionDialog(baseContext,"确定退出编辑？","取消","退出");
        cancelDialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
                finish();
            }
        });

        saveDialog = new ActionDialog(baseContext,"保留此次编辑？","不保留","保留");
        saveDialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
            @Override
            public void onLeftClick() {
                localClearData();
            }

            @Override
            public void onRightClick() {
                localSaveData();
            }
        });

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


    private void initEvent(){

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelDialog.show();
            }
        });
        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contentStr = editText.getText().toString().trim();
                if (TextUtils.isEmpty(contentStr) && data.size()<=1){
                    cancelDialog.show();
                }else{
                    saveDialog.show();
                }
            }
        });
        typeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typePicker.show();
            }
        });

        MPermissionUtils.requestPermissionsResult(AddFindActivity.this, REQUEST_PERMISSIONS,
                permissions, new MPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {

                    }

                    @Override
                    public void onPermissionDenied() {
                        MPermissionUtils.showTipsDialog(AddFindActivity.this);
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

                if (MPermissionUtils.checkPermissionAllGranted(AddFindActivity.this, permissions)) {
                    pickImg(surplusNum);
                } else {
                    MPermissionUtils.requestPermissionsResult(AddFindActivity.this, REQUEST_PERMISSIONS,
                            permissions, new MPermissionUtils.OnPermissionListener() {
                                @Override
                                public void onPermissionGranted() {
                                    pickImg(surplusNum);
                                }

                                @Override
                                public void onPermissionDenied() {
                                    MPermissionUtils.showTipsDialog(AddFindActivity.this);
                                }
                            });
                }

            }
        });

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publishFind();
            }
        });

        addrView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {/*MapChoiceActivity*/
                Intent intent = new Intent(baseContext,ChoiceAddrActivity.class);
                startActivityForResult(intent,REQUEST_MAP);
            }
        });
    }

    private void initData(){
        ArrayList<String> imgArr = (ArrayList<String>)
                PreferencesUtils.getList(baseContext,Params.KEY_FIND_PIC);
        if (imgArr!=null && imgArr.size()>0){
            data.addAll(imgArr);
        }

        String lastContentStr = PreferencesUtils.getString(baseContext,Params.KEY_FIND_TEXT);
        editText.setText(lastContentStr);
        editText.setSelection(editText.getText().toString().trim().length());

        if (data.size() >= MAX_PIC_NUM) {

        } else {
            data.add("-9");
        }
        adapter.notifyDataSetChanged();

        /*ArrayList<FindTypeBean.TypeBean> intentTypeData = (ArrayList<FindTypeBean.TypeBean>)
                getIntent().getSerializableExtra("typeData");
        if (intentTypeData!=null && intentTypeData.size()>0){
            typeData.clear();
            typeData.addAll(intentTypeData);
            for (int i = 0; i<typeData.size(); i++){
                typeStrData.add(typeData.get(i).getTitle());
            }
            typePicker.setPicker(typeStrData);
        }else{*/
            loadType();
        /*}*/

    }

    private void initTypePicker(){
        typePicker = new OptionsPickerBuilder(baseContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                typeIndex = options1;
                type.setText(typeData.get(options1).getTitle());
            }
        })
                /*.setLayoutRes(R.layout.dialog_title, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        *//*v.findViewById(R.id.dialogClose).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                typePicker.dismiss();
                            }
                        });*//*
                    }
                })*/
                .setTitleText("选择分类")
                .setContentTextSize(17)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0, 0,0)//默认选中项
                .setBgColor(Color.WHITE)
                .setTitleBgColor(getResources().getColor(R.color.white))
                .setTitleColor(getResources().getColor(R.color.text6))
                .setCancelColor(getResources().getColor(R.color.text6))
                .setSubmitColor(getResources().getColor(R.color.text6))
                .setTextColorCenter(getResources().getColor(R.color.blue))
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .setBackgroundId(getResources().getColor(R.color.transparent_1)) //设置外部遮罩颜色
                .build();
        typePicker.setPicker(typeStrData);

    }


    private void pickImg(int surplus) {
        MultiImageSelector selector = MultiImageSelector.create(AddFindActivity.this);
        selector.showCamera(true);
        selector.multi();
        selector.count(surplus);
        selector.start(AddFindActivity.this, REQUEST_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    String longitude = "", latitude = "";
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

        if (requestCode == REQUEST_MAP && resultCode == 100 && data != null) {
            //PoiItem poiItem = data.getParcelableExtra("poi");

           /* String provinceStr = intentData.getStringExtra("province");
            String cityStr = intentData.getStringExtra("city");
            String countryStr  = intentData.getStringExtra("district");*/

           /* StringBuilder builder = new StringBuilder();
            builder.append(provinceStr);
            builder.append(cityStr);
            builder.append(countryStr);*/

            /*addr.setText(builder.toString().trim());*/

            //if (poiItem != null) {
            addr.setText(intentData.getStringExtra("detailAddr"));
            longitude = intentData.getStringExtra("lng");
            latitude = intentData.getStringExtra("lat");
            //}
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
                            480,
                            800);
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


    /*发布分享*/
    private void publishFind(){

        if (TextUtils.isEmpty(type.getText().toString().trim())){
            showtoa("请选择分类");
            return;
        }

        final String contentStr = editText.getText().toString().trim();
        if (TextUtils.isEmpty(contentStr)){
            showtoa("内容不能为空");
            return;
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder builder = new StringBuilder();
                if (data.size()>0){
                    try{
                        for (int i = 0; i<data.size();i++){
                            String path = data.get(i);
                            if ( (!TextUtils.isEmpty(path)) &&  (path.length()>5) ){
                                builder.append(",").append(FileToBase64Util.encodeBase64File(data.get(i)));
                            }
                        }
                    }catch (Exception e){

                    }
                }

                String imgsStr = "";
                if (builder.toString().trim().length()>5){
                    imgsStr = builder.toString().trim().substring(1,builder.toString().trim().length());
                }

                final String imgss = imgsStr;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        publish.setEnabled(false);

                        mRequest01 = getRequest(Params.publishFind,true);
                        String userid = PreferencesUtils.getString(baseContext,Params.UserID);
                        mRequest01.add("shopid",userid);
                        mRequest01.add("flag","3");
                        mRequest01.add("classid",typeData.get(typeIndex).getId());
                        mRequest01.add("content",contentStr);
                        mRequest01.add("imgs",imgss);
                        mRequest01.add("address",addr.getText().toString().trim());
                        mRequest01.add("longitude",longitude);
                        mRequest01.add("latitude",latitude);
                        if (addrCheckBox.isChecked()){
                            mRequest01.add("isaddress","1");/*是否发送地址 1是 0否*/
                        }else{
                            mRequest01.add("isaddress","0");/*是否发送地址 1是 0否*/
                        }

                        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                                new CustomHttpListener<BaseBean>(baseContext,true,BaseBean.class) {
                                    @Override
                                    public void doWork(final BaseBean result, String code) {
                                        showtoa(result.getMsg());
                                        localClearData();
                                        EventBus.getDefault().post(new SuccessEvent(true));
                                        finish();
                                    }

                                    @Override
                                    public void onFinally(JSONObject obj, String code, boolean isNetSucceed) {
                                        super.onFinally(obj, code, isNetSucceed);
                                        publish.setEnabled(true);
                                    }
                                },false,true);
                    }
                });


            }
        });
        thread.start();



    }


    private void loadType(){
        mRequest02 = getRequest(Params.getPublishFindType,true);
        mRequest02.add("flag","3");
        CallServer.getRequestInstance().add(baseContext, 0, mRequest02,
                new CustomHttpListener<PublishFindTypeBean>(baseContext,true,PublishFindTypeBean.class) {
                    @Override
                    public void doWork(PublishFindTypeBean result, String code) {
                        try{
                            List<FindTypeBean.TypeBean> beans = result.getData().getData();
                            if (beans!=null && beans.size()>0){
                                typeData.clear();
                                typeData.addAll(beans);
                                for (int i = 0; i<typeData.size(); i++){
                                    typeStrData.add(typeData.get(i).getTitle());
                                }
                                typePicker.setPicker(typeStrData);
                            }
                        }catch (Exception e){

                        }
                    }
                },false,false);


    }

    private void localSaveData(){
        String contentStr = editText.getText().toString().trim();
        PreferencesUtils.putString(baseContext,Params.KEY_FIND_TEXT,contentStr);
        ArrayList<String> imgArr = new ArrayList<>();
        if ("-9".equals(data.get(data.size()-1))){
            imgArr.addAll(data.subList(0,data.size()-1));
        }else{
            imgArr.addAll(data);
        }
        PreferencesUtils.putList(baseContext,Params.KEY_FIND_PIC,imgArr);
        finish();
    }

    private void localClearData(){
        ArrayList<String> imgArr = new ArrayList<>();
        PreferencesUtils.putString(baseContext,Params.KEY_FIND_TEXT,"");
        PreferencesUtils.putList(baseContext,Params.KEY_FIND_PIC,imgArr);
        finish();
    }


}
