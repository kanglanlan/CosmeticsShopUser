package com.meida.cosmeticsshopuser.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.meida.cosmeticsshopuser.Bean.ActionBean;
import com.meida.cosmeticsshopuser.Bean.eventbus.UserInfoRefresh;
import com.meida.cosmeticsshopuser.MyView.CircleImageView;
import com.meida.cosmeticsshopuser.MyView.dialog.HeadPopuWindow;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.FileToBase64Util;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.meida.cosmeticsshopuser.utils.MPermissionUtils;
import com.meida.cosmeticsshopuser.utils.PreferencesUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;
import me.nereo.multi_image_selector.MultiImageSelector;

public class PersonalInfoActivity extends BaseActivity {

    /*图片选择相关*/
    private static final int REQUEST_IMAGE = 0x10;
    private static final int REQUEST_PERMISSIONS = 0x11;

    private String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Bind(R.id.img_photo)
    CircleImageView imgPhoto;
    @Bind(R.id.tv_personal_name)
    TextView tvPersonalName;
    @Bind(R.id.tv_personal_phone)
    TextView tvPersonalPhone;
    @Bind(R.id.tv_personal_bri)
    TextView tvPersonalBri;
    @Bind(R.id.tv_personal_sex)
    TextView tvPersonalSex;

    /*性别选择框*/
    private HeadPopuWindow sexDialog;

    /*时间选择框*/
    private TimePickerView timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        ButterKnife.bind(this);
        changeTitle("个人资料");
        EventBus.getDefault().register(this);
        initSexDialog();
        initTimePicker();
        initData();

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

    }

    /*初始化性别选择框*/
    private void initSexDialog(){
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_pick_sex,null);
        dialogView.findViewById(R.id.male).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPersonalSex.setText("男");
                sexDialog.dismiss();
                modifyUserInfo("","","","1");
            }
        });

        dialogView.findViewById(R.id.female).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPersonalSex.setText("女");
                sexDialog.dismiss();
                modifyUserInfo("","","","2");
            }
        });
        dialogView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sexDialog.dismiss();
            }
        });
        sexDialog = new HeadPopuWindow(baseContext,dialogView);

    }

    /*初始化生日选择框*/
    private void initTimePicker(){
        /*获取当前日期*/
        Calendar calendar = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        Calendar selectDate = Calendar.getInstance();
        selectDate.add(Calendar.YEAR,-20);
        endDate.add(Calendar.YEAR,-100);
        timePicker = new TimePickerBuilder(baseContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                try{
                    @SuppressLint("SimpleDateFormat")
                    DateFormat df  = DateFormat.getDateInstance();
                    String str = df.format(date)
                            .replace("年","-")
                            .replace("月","-")
                            .replace("日","");
                    modifyUserInfo("","",
                            str,
                            "");
                    tvPersonalBri.setText(str);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).setRangDate(endDate,calendar)
                .setDate(selectDate)
                .build();


    }

    @OnClick({R.id.ll_photo, R.id.ll_nicheng, R.id.ll_phone, R.id.ll_bri, R.id.ll_sex})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_photo:
                if (MPermissionUtils.checkPermissionAllGranted(baseContext, permissions)) {
                    pickImg();
                } else {
                    MPermissionUtils.requestPermissionsResult(baseContext, REQUEST_PERMISSIONS,
                            permissions, new MPermissionUtils.OnPermissionListener() {
                                @Override
                                public void onPermissionGranted() {
                                    pickImg();
                                }

                                @Override
                                public void onPermissionDenied() {
                                    MPermissionUtils.showTipsDialog(baseContext);
                                }
                            });
                }
                break;
            case R.id.ll_nicheng:
                if (needLogin()){
                    return;
                }
                StartActivity(ModifyNickNameActivity.class);
                break;
            case R.id.ll_phone:
                if (needLogin()){
                    return;
                }
                StartActivity(UserPhoneActivity.class);
                break;
            case R.id.ll_bri:
                timePicker.show();
                break;
            case R.id.ll_sex:
                sexDialog.show();
                break;
        }
    }

    private void initData(){
        String nicknameStr = PreferencesUtils.getString(baseContext, Params.KEY_NICKNAME);
        String phoneStr = PreferencesUtils.getString(baseContext,Params.KEY_PHONE);
        String headStr = PreferencesUtils.getString(baseContext,Params.KEY_USERHEAD);
        String birthdayStr = PreferencesUtils.getString(baseContext,Params.KEY_BIRTHDAY);
        String sexStr = PreferencesUtils.getString(baseContext,Params.KEY_SEX);

        GlideUtils.loadHead(headStr,imgPhoto);
        tvPersonalName.setText(nicknameStr);
        tvPersonalPhone.setText(phoneStr);
        tvPersonalBri.setText(birthdayStr);
        /*性别;0:保密,1:男,2:女*/
        if("1".equals(sexStr)){
            tvPersonalSex.setText("男");
        }else if ("2".equals(sexStr)){
            tvPersonalSex.setText("女");
        }else{
            tvPersonalSex.setText("保密");
        }


    }

    /*修改用户基本信息*/
    private void modifyUserInfo(final String headPath, final String hearStr, final String birthDayStr, final String sexStr){
        mRequest01 = getRequest(Params.modifyUserInfo,true);
        mRequest01.add("birthday",birthDayStr);
        mRequest01.add("sex",sexStr);
        mRequest01.add("avatar",hearStr);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<ActionBean>(baseContext,true,ActionBean.class) {
                    @Override
                    public void doWork(ActionBean result, String code) {
                        try{
                            showtoa(result.getMsg());
                            if (!TextUtils.isEmpty(headPath)){
                                PreferencesUtils.putString(baseContext,Params.KEY_USERHEAD,headPath);

                            }
                            if (!TextUtils.isEmpty(birthDayStr)){
                                PreferencesUtils.putString(baseContext,Params.KEY_BIRTHDAY,birthDayStr);
                            }
                            if (!TextUtils.isEmpty(sexStr)){
                                PreferencesUtils.putString(baseContext,Params.KEY_SEX,sexStr);
                            }
                            EventBus.getDefault().post(new UserInfoRefresh());
                        }catch (Exception e){

                        }
                    }
                },false,true);
    }

    private void pickImg() {
        MultiImageSelector selector = MultiImageSelector.create(baseContext);
        selector.showCamera(true);
        selector.single();
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
                List<String> pickData = intentData.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                if (pickData!=null && pickData.size()>0){
                    String headPath = pickData.get(pickData.size()-1);
                    GlideUtils.loadHead(headPath,imgPhoto);
                    try {
                        modifyUserInfo(headPath,FileToBase64Util.encodeBase64File(headPath),"","");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(UserInfoRefresh refresh){
        initData();
    }



}
