package com.meida.cosmeticsshopuser.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.ActionBean;
import com.meida.cosmeticsshopuser.Bean.VersionBean;
import com.meida.cosmeticsshopuser.MyView.dialog.ActionDialog;
import com.meida.cosmeticsshopuser.MyView.dialog.UpdateVersionDialog;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.GlideCacheUtil;
import com.meida.cosmeticsshopuser.utils.PreferencesUtils;
import com.meida.cosmeticsshopuser.utils.ProjectUtils;
import com.meida.cosmeticsshopuser.utils.VersionUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;

public class SettingActivity extends BaseActivity {

    @Bind(R.id.cb_xiaoxi)
    CheckBox cbXiaoxi;
    @Bind(R.id.tv_version)
    TextView tvVersion;
    @Bind(R.id.cacheSize)
    TextView cacheSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        changeTitle("设置");
        cacheSize.setText(GlideCacheUtil.getInstance().getCacheSize(baseContext));

        //  是否接受推送  0不接收 1 接受
        String str_isp = PreferencesUtils.getString(baseContext, Params.UserISJPush, "1");
        if (!TextUtils.isEmpty(str_isp) && str_isp.equals("1")) {
            cbXiaoxi.setChecked(true);
        } else {
            cbXiaoxi.setChecked(false);
        }

        cbXiaoxi.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    PreferencesUtils.putString(baseContext, Params.UserISJPush, "1");
                    JPushInterface.resumePush(baseContext);
                } else {
                    PreferencesUtils.putString(baseContext, Params.UserISJPush, "-1");
                    JPushInterface.stopPush(baseContext);
                }
            }

        });
        tvVersion.setText(String.format("V%s", GlideCacheUtil.getVersion(baseContext)));


    }

    @OnClick({R.id.tv_changemima, R.id.tv_feedback, R.id.tv_help, R.id.tv_about, R.id.qingchuhuancun, R.id.ll_geng, R.id.tv_tuichu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_changemima:
                StartActivity(ChangepswActivity.class);
                break;
            case R.id.tv_feedback:
                StartActivity(FeedBackActivity.class);
                break;
            case R.id.tv_help:
                StartActivity(HelpActivity.class);
                //StartActivity(WebViewActivity.class,"8");
                break;
            case R.id.tv_about:
                StartActivity(WebViewActivity.class,"2");
                break;
            case R.id.qingchuhuancun:
                showdcleanDalog();
                break;
            case R.id.ll_geng:
                getVersionInfo();
                break;
            case R.id.tv_tuichu:
                ProjectUtils.doExit(baseContext,false);
                break;
        }
    }

    private void showdcleanDalog() {

        ActionDialog cleanDialog = new ActionDialog(baseContext,"确定要清除所有缓存吗？");
        cleanDialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
                GlideCacheUtil.getInstance().clearImageAllCache(baseContext);
                cacheSize.setText("0KB");
            }
        });
        cleanDialog.show();
    }

    private UpdateVersionDialog updateVersionDialog;
    private void getVersionInfo(){
        mRequest02 = getRequest(Params.updateVersion,false);
        mRequest02.add("app_type","1");
        mRequest02.add("device_type","1");
        CallServer.getRequestInstance().add(baseContext, 0, mRequest02,
                new CustomHttpListener<VersionBean>(baseContext,true,VersionBean.class) {
                    @Override
                    public void doWork(VersionBean result, String code) {
                        try{
                            //String versionname_old = GlideCacheUtil.getVersion(baseContext);
                            int versioncode_old = GlideCacheUtil.getVerCode(baseContext);
                            String versionname_old = GlideCacheUtil.getVersion(baseContext);

                            if (VersionUtils.compareVersions(versionname_old,result.getData().getVersion_number())!=1){
                                showtoa("当前已经是最新版本");
                            }else{
                                updateVersionDialog =
                                        new UpdateVersionDialog(baseContext,result.getData(),SettingActivity.this);
                                updateVersionDialog.show();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },false,false);
    }

    @Override
    protected void onDestroy() {
        if (updateVersionDialog != null) {
            updateVersionDialog.dismiss();
        }
        super.onDestroy();

    }


}
