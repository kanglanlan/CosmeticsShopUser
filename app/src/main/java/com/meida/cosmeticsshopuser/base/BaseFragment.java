package com.meida.cosmeticsshopuser.base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;

import com.meida.cosmeticsshopuser.MyView.MUIToast;
import com.meida.cosmeticsshopuser.Activity.LoginActivity;
import com.meida.cosmeticsshopuser.nohttp.HttpIp;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.CommonUtil;
import com.meida.cosmeticsshopuser.utils.PreferencesUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.Request;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimAdapterEx;


/**
 * 作者 亢兰兰
 * 时间 2018/5/11 0011
 * 公司  郑州软盟
 */

public class BaseFragment extends Fragment {
    /**
     * RecyclerView数据管理的LayoutManager
     */
    public LinearLayoutManager linearLayoutManager;
    public GridLayoutManager gridLayoutManager;
    public StaggeredGridLayoutManager staggeredGridLayoutManager;
    /**
     * SlimAdapter的adapter
     */
    public SlimAdapter mAdapter;
    public SlimAdapterEx mAdapterex;
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

    //页面跳转
    public void StartActivity(Class c) {
        Intent intent = new Intent(getActivity(), c);
        this.startActivity(intent);
    }

    public void StartActivity(Class c,int tag) {
        Intent intent = new Intent(getActivity(), c);
        intent.putExtra("tag",tag);
        this.startActivity(intent);
    }

    public void call(Context context, String phone) {
        if (TextUtils.isEmpty(phone)) {
            CommonUtil.showToast(context, "无效电话号码");
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
            if (!TextUtils.isEmpty(PreferencesUtils.getString(getContext(), Params.Token))) {
                mrequest.addHeader("XX-Token", PreferencesUtils.getString(getContext(), Params.Token));
            }
            if (!TextUtils.isEmpty(PreferencesUtils.getString(getContext(), Params.UserID))) {
                mrequest.add("uid", PreferencesUtils.getString(getContext(), Params.UserID));
            }
        }
        return mrequest;
    }


    //显示吐丝
    public void showtoa(String string) {
        MUIToast.show(getContext(),string);
    }

    public void showtoa(int stringid) {
        MUIToast.toast(getContext(),stringid);
    }


    @Override
    public void onDestroy() {
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


    protected boolean needLogin(){
        if (TextUtils.isEmpty(PreferencesUtils.getString(getContext(), Params.UserID))) {
            StartActivity(LoginActivity.class);
            return true;
        }else{
            return false;/*不需要登录*/
        }

    }






}
