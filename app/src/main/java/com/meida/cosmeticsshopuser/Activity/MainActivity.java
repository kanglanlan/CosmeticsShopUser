package com.meida.cosmeticsshopuser.Activity;

import android.Manifest;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.meida.cosmeticsshopuser.Bean.BaseBean;
import com.meida.cosmeticsshopuser.Bean.DistanceRangeBean;
import com.meida.cosmeticsshopuser.Bean.RongUserInfoBean;
import com.meida.cosmeticsshopuser.Bean.SystermBean;
import com.meida.cosmeticsshopuser.Bean.VersionBean;
import com.meida.cosmeticsshopuser.Bean.eventbus.KeyEventBean;
import com.meida.cosmeticsshopuser.Bean.eventbus.LocationSuccess;
import com.meida.cosmeticsshopuser.Bean.eventbus.UserInfoRefresh;
import com.meida.cosmeticsshopuser.MyView.Loading.LoadingDialog;
import com.meida.cosmeticsshopuser.MyView.dialog.UpdateVersionDialog;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.base.App;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.fragment.Fragment1;
import com.meida.cosmeticsshopuser.fragment.Fragment2;
import com.meida.cosmeticsshopuser.fragment.Fragment3;
import com.meida.cosmeticsshopuser.fragment.Fragment4;
import com.meida.cosmeticsshopuser.fragment.Fragment5;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.HttpIp;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.meida.cosmeticsshopuser.utils.GlideCacheUtil;
import com.meida.cosmeticsshopuser.utils.LoggerUtil;
import com.meida.cosmeticsshopuser.utils.MPermissionUtils;
import com.meida.cosmeticsshopuser.utils.PreferencesUtils;
import com.meida.cosmeticsshopuser.utils.ProjectUtils;
import com.meida.cosmeticsshopuser.utils.VersionUtils;
import com.yolanda.nohttp.rest.CacheMode;
import com.yolanda.nohttp.rest.Request;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jiguang.analytics.android.api.CountEvent;
import cn.jiguang.analytics.android.api.Event;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

public class MainActivity extends BaseActivity implements
        RadioGroup.OnCheckedChangeListener {
    Fragment1 fr1;
    Fragment2 fr2;
    Fragment3 fr3;
    Fragment4 fr4;
    Fragment5 fr5;

    @Bind(R.id.rb_main_check_1)
    RadioButton rbMainCheck1;
    @Bind(R.id.rb_main_check_3)
    RadioButton rbMainCheck3;
    @Bind(R.id.rb_main_check_2)
    RadioButton rbMainCheck2;
    @Bind(R.id.rb_main_check_4)
    RadioButton rbMainCheck4;
    @Bind(R.id.rb_main_check_5)
    RadioButton rbMainCheck5;
    @Bind(R.id.rg_main_check)
    RadioGroup radioGroup;
    @Bind(R.id.tv_find_msg_num)
    public TextView tvFindMsgNum;
    @Bind(R.id.tv_message_num)
    TextView tvMessageNum;
    private FragmentTransaction transaction;
    private Fragment fragement;

    public static MainActivity instance;

    /*定位相关*/
    private static final int PERMISSION_lOCATION_REQUEST = 0x12;
    private String[] locationPermissions = new String[]{
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        instance = this;
        EventBus.getDefault().register(this);
        loadingDialog = new LoadingDialog(baseContext);
        radioGroup.setOnCheckedChangeListener(this);
        rbMainCheck1.performClick();
        initMap();
        setSwipeBackEnable(false);
        getSystemData();
        connectRongIM();
        getVersionInfo();
        List<DistanceRangeBean.RangeBean> beans = new ArrayList<>();
        PreferencesUtils.putList(baseContext,Params.KEY_DISTANCE_RANGE,beans);
        ProjectUtils.updateFindMsgNum(baseContext,tvFindMsgNum);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        transaction = getSupportFragmentManager().beginTransaction();
        if (fragement != null) {
            transaction.hide(fragement);
        }
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_main_check_1:
                if (null == fr1) {
                    fr1 = Fragment1.instantiation();
                    transaction.add(R.id.fl_main_fragment, fr1);
                }
                fragement = fr1;
                transaction.show(fragement);
                break;
            case R.id.rb_main_check_2:
                if (null == fr2) {
                    fr2 = Fragment2.instantiation();
                    transaction.add(R.id.fl_main_fragment, fr2);
                }
                fragement = fr2;
                transaction.show(fragement);
                break;
            case R.id.rb_main_check_3:
                if (null == fr3) {
                    fr3 = Fragment3.instantiation();
                    transaction.add(R.id.fl_main_fragment, fr3);
                }
                fragement = fr3;
                transaction.show(fragement);
                break;
            case R.id.rb_main_check_4:
                if (null == fr4) {
                    fr4 = Fragment4.instantiation();
                    transaction.add(R.id.fl_main_fragment, fr4);
                }
                fragement = fr4;
                transaction.show(fragement);
                break;
            case R.id.rb_main_check_5:
               /* ProjectUtils.updateFindMsgNum(baseContext,tvFindMsgNum);*/
                if (null == fr5) {
                    fr5 = Fragment5.instantiation();
                    transaction.add(R.id.fl_main_fragment, fr5);
                }
                fragement = fr5;
                transaction.show(fragement);
                break;
        }
        transaction.commitAllowingStateLoss();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); // 调用双击退出函数
        }
        return false;
    }

    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            finish();
            System.exit(0);
        }
    }

    /*获取系统参数*/
    private void getSystemData() {
        mRequest01 = getRequest(Params.getSystemData, false);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<SystermBean>(baseContext, true, SystermBean.class) {
                    @Override
                    public void doWork(SystermBean data, String code) {
                        try {
                            PreferencesUtils.putString(baseContext, Params.BOND, data.getData().getBond());
                            PreferencesUtils.putString(baseContext, Params.WITHDRAW_RATE, data.getData().getRate());
                            PreferencesUtils.putString(baseContext, Params.CLIENT_PHONE, data.getData().getService());
                            PreferencesUtils.putString(baseContext, Params.PROTECT_TXT, data.getData().getProtect());
                        } catch (Exception e) {
                        }
                    }
                }, false, false);
    }


    private void initMap() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setOnceLocationLatest(true);
        mLocationOption.setNeedAddress(true);
        //mLocationOption.setHttpTimeOut(20000);
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        if (MPermissionUtils.checkPermissionAllGranted(MainActivity.this, locationPermissions)) {
            //启动定位
            mLocationClient.startLocation();
            loadingDialog.setDetailsLabel("定位中...");
            loadingDialog.show();
        } else {
            ActivityCompat.requestPermissions(this, locationPermissions,
                    PERMISSION_lOCATION_REQUEST);//自定义的code
        }
    }

    public void relocateCity() {
        if (MPermissionUtils.checkPermissionAllGranted(MainActivity.this, locationPermissions)) {
            //启动定位
            mLocationClient.startLocation();
        } else {
            ActivityCompat.requestPermissions(this, locationPermissions,
                    PERMISSION_lOCATION_REQUEST);//自定义的code
        }
    }

    /*定位相关*/
    AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    LoggerUtil.e("LOCATION_SUCCESS", amapLocation.getProvince() + "  "
                            + amapLocation.getCity() + "   "
                            + amapLocation.getDistrict());
                    LoggerUtil.e("LOCATION_SUCCESS",amapLocation.toString());
                    EventBus.getDefault().post(new LocationSuccess(amapLocation, 1));

                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    LoggerUtil.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());

                    Event event = new CountEvent();
                    Map map = new HashMap();
                    map.put("AmapError-code",amapLocation.getErrorCode());
                    map.put("AmapError-info",amapLocation.getErrorInfo());
                    event.addExtMap(map);
                    JAnalyticsInterface.onEvent(baseContext,event);

                    EventBus.getDefault().post(new LocationSuccess(null, -1));
                }
            }

            if (loadingDialog != null && loadingDialog.isShowing()) {
                loadingDialog.setDetailsLabel("定位信息获取成功");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(1000);
                        loadingDialog.dismiss();
                    }
                });
            }

        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_lOCATION_REQUEST) {
            boolean isAllGranted = true;
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }
            if (isAllGranted) {
                mLocationClient.startLocation();
            } else {
                showtoa("定位授权失败！");
                EventBus.getDefault().post(new LocationSuccess(null, -1));
            }
        }
    }

    @Override
    protected void onDestroy() {
        mLocationClient.stopLocation();
        mLocationClient.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        try {
            RongIM.getInstance().removeUnReadMessageCountChangedObserver(myIUnReadMessageObserver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (updateVersionDialog != null) {
            updateVersionDialog.dismiss();
        }
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(UserInfoRefresh refresh) {

        /*String user_id = PreferencesUtils.getString(baseContext, Params.UserID);
        String nickname = PreferencesUtils.getString(baseContext, Params.KEY_NICKNAME);
        String userhead = PreferencesUtils.getString(baseContext, Params.KEY_USERHEAD);
        RongIM.getInstance().refreshUserInfoCache(new UserInfo(user_id, nickname,
                Uri.parse(userhead)));*/
        findUserInfoById(getConversationList());

        myIUnReadMessageObserver = new MyIUnReadMessageObserver();
        RongIM.getInstance().addUnReadMessageCountChangedObserver(myIUnReadMessageObserver, Conversation.ConversationType.PRIVATE);
        registerConnectStatusChangeListener();

    }

    private void connectRongIM() {
        if (TextUtils.isEmpty(PreferencesUtils.getString(baseContext, Params.UserID))) {
            LoggerUtil.e("RONGIM", "UN LOGIN");
            return;
        }
        String userid = PreferencesUtils.getString(baseContext, Params.UserID) + "";

        if (userid.equals(RongIM.getInstance().getCurrentUserId()) &&
                (RongIM.getInstance().getCurrentConnectionStatus() == RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED)) {
            LoggerUtil.e("RONGIM", "HAS CONNECT");
           /* String user_id = PreferencesUtils.getString(baseContext, Params.UserID);
            String nickname = PreferencesUtils.getString(baseContext, Params.KEY_NICKNAME);
            String userhead = PreferencesUtils.getString(baseContext, Params.KEY_USERHEAD);
            RongIM.getInstance().refreshUserInfoCache(new UserInfo(user_id, nickname,
                    Uri.parse(userhead)));*/
            return;
        }
        String token = PreferencesUtils.getString(baseContext, Params.RONG_TOKEN);
        LoggerUtil.e("rong_token", token);
        if (getApplicationInfo().packageName.equals(App.getCurProcessName(getApplicationContext()))) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {

                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    LoggerUtil.e("LoginActivity", "--onSuccess" + userid);
                    if (!TextUtils.isEmpty(userid)){
                        //String user_id = PreferencesUtils.getString(baseContext, Params.UserID);
                       /* String nickname = PreferencesUtils.getString(baseContext, Params.KEY_NICKNAME);
                        String userhead = PreferencesUtils.getString(baseContext, Params.KEY_USERHEAD);
                        RongIM.getInstance().refreshUserInfoCache(new UserInfo(userid, nickname,
                                Uri.parse(userhead)));*/
                        findUserInfoById(getConversationList());

                        myIUnReadMessageObserver = new MyIUnReadMessageObserver();
                        RongIM.getInstance().addUnReadMessageCountChangedObserver(myIUnReadMessageObserver, Conversation.ConversationType.PRIVATE);
                        registerConnectStatusChangeListener();
                    }


                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            });
        }
    }

    private MyIUnReadMessageObserver myIUnReadMessageObserver;

    private class MyIUnReadMessageObserver implements IUnReadMessageObserver {

        @Override
        public void onCountChanged(int i) {
            LoggerUtil.e("RONG_UN_READ", i + "");
            FormatterUtil.sortBadger(i, tvMessageNum);
            findUserInfoById(getConversationList());
        }
    }

    private String getConversationList() {
        StringBuffer stringBuffer = new StringBuffer();
        String str_listid = "";
        List<Conversation> list_rongim = RongIM.getInstance().getConversationList();
        if (list_rongim != null) {
            for (int i = 0; i < list_rongim.size(); i++) {
                stringBuffer.append(list_rongim.get(i).getTargetId() + ",");
            }
        }
        str_listid = stringBuffer.toString().trim();
        if (!TextUtils.isEmpty(str_listid) && str_listid.length() >= 2) {
            str_listid = str_listid.substring(0, str_listid.length() - 1);
        }
        LoggerUtil.e("--lfc", str_listid);
        return str_listid;
    }

    public void findUserInfoById(final String userId) {
        if (TextUtils.isEmpty(userId))
            return;
        Request<String> request = ProjectUtils.getRequest(baseContext, Params.getRongUserInfo, true);
        request.add("ids", userId);
        String nickname = PreferencesUtils.getString(baseContext, Params.KEY_NICKNAME);
        request.setCacheKey(nickname + userId);
        request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE); // 缓存模式
        CallServer.getRequestInstance().add(baseContext, 0, request,
                new CustomHttpListener<RongUserInfoBean>(baseContext, true, RongUserInfoBean.class) {
                    @Override
                    public void doWork(RongUserInfoBean data, String code) {
                        for (RongUserInfoBean.DataBean.ListBean listBean : data.getData().getData()) {
                            RongIM.getInstance().refreshUserInfoCache(new UserInfo(listBean.getId() + "", listBean.getUser_nickname(), Uri.parse(listBean.getAvatar())));
                        }

                    }

                    @Override
                    public void onFinally(JSONObject obj, String code, boolean isSucceed) {
                        super.onFinally(obj, code, isSucceed);

                    }

                }, true, false);
    }

    /*连接状态监听*/
    public void registerConnectStatusChangeListener(){
        RongIM.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
            @Override
            public void onChanged(ConnectionStatus connectionStatus) {
                LoggerUtil.e("registerConnectStatusChangeListener",connectionStatus.getValue()+"");
                switch (connectionStatus){
                    case CONNECTED:/*连接成功1*/
                        break;
                    case DISCONNECTED:/*断开连接3*/
                        break;
                    case CONNECTING:/*连接中2*/
                        break;
                    case NETWORK_UNAVAILABLE:/*网络不可用0*/
                        break;
                    case KICKED_OFFLINE_BY_OTHER_CLIENT:/*异地登录4*/
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ProjectUtils.doExit(baseContext,true);
                            }
                        });
                        break;
                }
            }
        });
    }


    /*版本更新*/
    UpdateVersionDialog updateVersionDialog;

    private void getVersionInfo() {
        Request<String> request = getRequest(Params.updateVersion, false);
        request.add("app_type", "1");
        request.add("device_type", "1");
        CallServer.getRequestInstance().add(baseContext, 0, request,
                new CustomHttpListener<VersionBean>(baseContext, true,false, VersionBean.class) {
                    @Override
                    public void doWork(VersionBean result, String code) {
                        try {
                            //String versionname_old = GlideCacheUtil.getVersion(baseContext);
                            int versioncode_old = GlideCacheUtil.getVerCode(baseContext);
                            String versionname_old = GlideCacheUtil.getVersion(baseContext);
                            if (VersionUtils.compareVersions(versionname_old,result.getData().getVersion_number())!=1){
                                /*showtoa("当前已经是最新版本");*/
                            } else {
                                updateVersionDialog =
                                        new UpdateVersionDialog(baseContext, result.getData(), MainActivity.this);
                                updateVersionDialog.show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, false, false);
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction()) {
            EventBus.getDefault().post(new KeyEventBean());
            return true;
        }
        return super.dispatchKeyEvent(event);
    }


}
