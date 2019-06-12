package com.meida.cosmeticsshopuser.Activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Toast;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

public class QiDongActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qi_dong);
        new Thread() {
            public void run() {
                SystemClock.sleep(2000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<String> list=new ArrayList<String>();
                        list.add(Permission.ACCESS_FINE_LOCATION);
                        list.add(Permission.ACCESS_COARSE_LOCATION);
                        list.add(Permission.READ_PHONE_STATE);
                        list.add(Permission.READ_EXTERNAL_STORAGE);
                        list.add(Permission.WRITE_EXTERNAL_STORAGE);
                        XXPermissions.with(baseContext)
                                //.constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                                //.permission(Permission.SYSTEM_ALERT_WINDOW, Permission.REQUEST_INSTALL_PACKAGES) //支持请求6.0悬浮窗权限8.0请求安装权限
//                                .permission(Permission.Group.STORAGE, Permission.Group.CALENDAR) //不指定权限则自动获取清单中的危险权限
                                .permission(list) //不指定权限则自动获取清单中的危险权限
                                .request(new OnPermission() {

                                    @Override
                                    public void hasPermission(List<String> granted, boolean isAll) {
                                        if (isAll) {
//                                            Toast.makeText(baseContext, "获取权限成功", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(baseContext, "获取权限成功，部分权限未正常授予", Toast.LENGTH_SHORT).show();
                                        }

                                        boolean b = PreferencesUtils.getBoolean(baseContext, Params.KEY_HAS_SHOW_GUIDE);
                                        if (b){
                                            StartActivity(MainActivity.class);
                                        }else{
                                            StartActivity(GuideActivity.class);
                                        }
                                        finish();
                                    }
                                    @Override
                                    public void noPermission(List<String> denied, boolean quick) {
                                        if (quick) {
                                            Toast.makeText(baseContext, "被永久拒绝授权，请手动授予权限", Toast.LENGTH_SHORT).show();
                                            //如果是被永久拒绝就跳转到应用权限系统设置页面
                                            XXPermissions.gotoPermissionSettings(baseContext);
                                        } else {
                                            Toast.makeText(baseContext, "获取权限失败", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });
            }
        }.start();
    }
}
