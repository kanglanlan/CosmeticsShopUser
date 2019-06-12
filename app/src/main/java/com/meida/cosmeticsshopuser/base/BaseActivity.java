package com.meida.cosmeticsshopuser.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Activity.LoginActivity;
import com.meida.cosmeticsshopuser.MyView.MUIToast;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.nohttp.HttpIp;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.ActivityStack;
import com.meida.cosmeticsshopuser.utils.PreferencesUtils;
import com.meida.cosmeticsshopuser.utils.SystemBarTintManager;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.Request;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimAdapterEx;

import java.util.List;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jpush.android.api.JPushInterface;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;


public class BaseActivity extends SwipeBackActivity implements
        TextWatcher, View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    public LinearLayoutManager linearLayoutManager;
    public LinearLayoutManager linearLayoutManager2;
    public GridLayoutManager gridLayoutManager;
    public StaggeredGridLayoutManager staggeredGridLayoutManager;
    private SwipeBackLayout mSwipeBackLayout;
    /**
     * 是否正在上拉加载中
     */
    public boolean isLoadingMore;
    public int pager = 1;
    /**
     * 网络请求对象.
     */
    public Request<String> mRequest01;
    public Request<String> mRequest02;
    public Request<String> mRequest03;
    public Request<String> mRequest04;
    public Request<String> mRequest05;
    /**
     * 上下文context
     */
    public Activity baseContext;
    /**
     * SlimAdapter的adapter
     */
    public SlimAdapter mAdapter;
    public SlimAdapterEx mAdapterex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseContext = this;
        ActivityStack.getScreenManager().pushActivity(this);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        initSystemBar();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    protected void setEnableGesture(boolean cehua) {
        mSwipeBackLayout.setEnableGesture(cehua);
    }

    public void call(String phone) {
        if (TextUtils.isEmpty(phone)) {
            showtoa("无效电话号码");
            return;
        }
        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));//跳转到拨号界面，同时传递电话号码
        startActivity(dialIntent);
    }

    //Nohttp请求
    public Request getRequest(String Str_Head, boolean isLogin) {
        Log.e("Str_Head",Str_Head);
        Request mrequest = NoHttp.createStringRequest(HttpIp.BaseDataIp + Str_Head, HttpIp.POST);
        mrequest.addHeader("XX-Device-Type", "mobile");
        if (isLogin) {
            if (!TextUtils.isEmpty(PreferencesUtils.getString(baseContext, Params.Token))) {
                mrequest.addHeader("XX-Token", PreferencesUtils.getString(baseContext, Params.Token));
            }
            if (!TextUtils.isEmpty(PreferencesUtils.getString(baseContext, Params.UserID))) {
                mrequest.add("uid", PreferencesUtils.getString(baseContext, Params.UserID));
            }
        }
        return mrequest;
    }

    //Nohttp请求
    public Request getRequestNoUid(String Str_Head, boolean isLogin) {
        Log.e("Str_Head",Str_Head);
        Request mrequest = NoHttp.createStringRequest(HttpIp.BaseDataIp + Str_Head, HttpIp.POST);
        mrequest.addHeader("XX-Device-Type", "mobile");
        if (isLogin) {
            if (!TextUtils.isEmpty(PreferencesUtils.getString(baseContext, Params.Token))) {
                mrequest.addHeader("XX-Token", PreferencesUtils.getString(baseContext, Params.Token));
            }
            /*if (!TextUtils.isEmpty(PreferencesUtils.getString(baseContext, Params.UserID))) {
                mrequest.add("uid", PreferencesUtils.getString(baseContext, Params.UserID));
            }*/
        }
        return mrequest;
    }

    public void back(View view) {
        finish();
    }

    protected TextView tv_title,tv_right_title;
    //改变标题
    public void changeTitle(String title) {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText(title);
        tv_right_title = (TextView)findViewById(R.id.tv_title_right);
    }

    public void changeTitle(String title, String rightTitle) {
        changeTitle(title);
        tv_right_title = (TextView)findViewById(R.id.tv_title_right);
        tv_right_title.setText(rightTitle);
        tv_right_title.setVisibility(View.VISIBLE);
    }


    //显示吐丝
    public void showtoa(String string) {
        MUIToast.show(this,string);
    }

    public void showtoa(int stringid) {
        MUIToast.toast(this,stringid);
    }

    //页面跳转
    public void StartActivity(Class c) {
        Intent intent = new Intent(this, c);
        this.startActivity(intent);
    }

    //页面跳转
    public void StartActivity(Class c, String id) {
        Intent intent = new Intent(this, c);
        intent.putExtra("id", id);
        this.startActivity(intent);
    }

    //页面跳转
    public void StartActivity(Class c, String id, String name) {
        Intent intent = new Intent(this, c);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        this.startActivity(intent);
    }

    //隐藏键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!"ShowInfoActivity".equals(getClass().getSimpleName())) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
                View v = getCurrentFocus();
                if (isShouldHideInput(v, ev)) {
                    hideSoftInput(v.getWindowToken());
                }
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (!"ShowInfoActivity".equals(getClass().getSimpleName())) {
            if (v != null && (v instanceof EditText)) {
                int[] l = {0, 0};
                v.getLocationInWindow(l);
                int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                        + v.getWidth();
                return !(event.getX() > left && event.getX() < right
                        && event.getY() > top && event.getY() < bottom);
            }
            // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        }
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     */
    private void hideSoftInput(IBinder token) {
        if (!"ShowInfoActivity".equals(getClass().getSimpleName())) {
            if (token != null) {
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(token,
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出时可以取消这个请求
        if (mRequest01 != null)
            mRequest01.cancel();
        if (mRequest02 != null)
            mRequest02.cancel();
        if (mRequest03 != null)
            mRequest03.cancel();
        if (mRequest04 != null)
            mRequest04.cancel();
        if (mRequest05 != null)
            mRequest05.cancel();
    }


    public boolean hideInputWindow() {
        final View v = getWindow().peekDecorView();
        InputMethodManager imm = null;
        if (v != null && v.getWindowToken() != null) {
            imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        return imm.isActive();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }


    public void initSystemBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(false);
    }

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 判断某一个类是否存在任务栈里面
     *
     * @return
     */
    private boolean isExsitMianActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        ComponentName cmpName = intent.resolveActivity(getPackageManager());
        boolean flag = false;
        if (cmpName != null) { // 说明系统中存在这个activity
            ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(10);
            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                if (taskInfo.baseActivity.equals(cmpName)) { // 说明它已经启动了
                    flag = true;
                    break;  //跳出循环，优化效率
                }
            }
        }
        return flag;
    }


    protected boolean needLogin(){
        if (TextUtils.isEmpty(PreferencesUtils.getString(baseContext, Params.UserID))) {
            StartActivity(LoginActivity.class);
            return true;
        }else{
            return false;/*不需要登录*/
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
        JAnalyticsInterface.onPageStart(getApplicationContext(),this.getClass().getCanonicalName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
        JAnalyticsInterface.onPageEnd(getApplicationContext(),this.getClass().getCanonicalName());
    }


}
