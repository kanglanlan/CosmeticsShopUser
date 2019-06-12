package com.meida.cosmeticsshopuser.utils;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Activity.MessageDetailActivity;
import com.meida.cosmeticsshopuser.Activity.ProductDetailActivity;
import com.meida.cosmeticsshopuser.Bean.ActionBean;
import com.meida.cosmeticsshopuser.Bean.BannerItemBean;
import com.meida.cosmeticsshopuser.Bean.BaseBean;
import com.meida.cosmeticsshopuser.Bean.CartNumBean;
import com.meida.cosmeticsshopuser.Bean.FindMsgUnReadBean;
import com.meida.cosmeticsshopuser.Bean.MessageUnReadBean;
import com.meida.cosmeticsshopuser.Bean.ShareContentBean;
import com.meida.cosmeticsshopuser.Gallery.ImagePagerActivity;
import com.meida.cosmeticsshopuser.MyView.FlowLiner;
import com.meida.cosmeticsshopuser.MyView.MUIToast;
import com.meida.cosmeticsshopuser.MyView.dialog.ActionDialog;
import com.meida.cosmeticsshopuser.base.App;
import com.meida.cosmeticsshopuser.Activity.LoginActivity;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.jpush.TagAliasOperatorHelper;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.HttpIp;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.Request;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import io.rong.imkit.RongIM;

/**
 * Created by Administrator on 2018/12/26 0026.
 */

public class ProjectUtils {

    /*处理轮播图跳转*/
    public static void sortBannerClick(Context context,BannerItemBean bean){
        /*1、商品  2、新闻
        * pid 商品或新闻的ID*/
        try{
            String flag = bean.getFlag();
            String pid = bean.getPid();
            if ("1".equals(bean.getLink())){
                if ("1".equals(flag)){
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra("id",pid);
                    context.startActivity(intent);
                }else if ("2".equals(flag)){
                    Intent intent = new Intent(context, MessageDetailActivity.class);
                    intent.putExtra("id",pid);
                    intent.putExtra("ip",Params.getNewsDetail);
                    context.startActivity(intent);
                }else{
                /*Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("id",pid);
                context.startActivity(intent);*/
                }
            }
        }catch (Exception e){

        }
    }

    /*店铺标签
    * 同城配送
    * 商家自送
    * 快递
    * */
    public static void sortShopLabel(int d1, int d2, int d3, FlowLiner flowLiner){
        Context context = flowLiner.getContext();
        flowLiner.removeAllViews();
        if (d1==1){
            View view=View.inflate(context, R.layout.item_biqoain,null);
            TextView tv_biaoqian = view.findViewById(R.id.tv_biaoqian);
            tv_biaoqian.setText("同城配送");
            flowLiner.addView(view);
        }
        if (d2==1){
            View view=View.inflate(context, R.layout.item_biqoain,null);
            TextView tv_biaoqian = view.findViewById(R.id.tv_biaoqian);
            tv_biaoqian.setText("商家自送");
            flowLiner.addView(view);
        }

        if (d3==1){
            View view=View.inflate(context, R.layout.item_biqoain,null);
            TextView tv_biaoqian = view.findViewById(R.id.tv_biaoqian);
            tv_biaoqian.setText("快递");
            flowLiner.addView(view);
        }

    }


    public static Request getRequest(Context context ,String Str_Head, boolean isLogin) {
        Log.e("Str_Head",Str_Head);
        Request mrequest = NoHttp.createStringRequest(HttpIp.BaseDataIp + Str_Head, HttpIp.POST);
        mrequest.addHeader("XX-Device-Type", "mobile");
        if (isLogin) {
            if (!TextUtils.isEmpty(PreferencesUtils.getString(context, Params.Token))) {
                mrequest.addHeader("XX-Token", PreferencesUtils.getString(context, Params.Token));
            }
            if (!TextUtils.isEmpty(PreferencesUtils.getString(context, Params.UserID))) {
                mrequest.add("uid", PreferencesUtils.getString(context, Params.UserID));
            }
        }
        return mrequest;
    }

    public static boolean needLogin(Context context){
        if (TextUtils.isEmpty(PreferencesUtils.getString(context, Params.UserID))) {
            context.startActivity(new Intent(context,LoginActivity.class));
            return true;
        }else{
            return false;/*不需要登录*/
        }

    }


    /*复制字符串到剪贴板*/
    public static void clipStr(String clipStr,String toastStr){
        ClipboardManager cm = (ClipboardManager) App.getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(clipStr);
        MUIToast.show(App.getApplication(), toastStr);
    }


    /**
     * 调起系统发短信功能
     *
     * @param phoneNumber 电话
     * @param message     内容
     */
    public static void doSendSMSTo(Activity activity, String phoneNumber, String message) {
        if (PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
            intent.putExtra("sms_body", message);
            activity.startActivity(intent);
        }
    }

    /*调起系统拨打电话功能*/
    public static void doCallTo(Activity activity, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }


    public static void backToHome(){
        App.finishAllActivity();
    }

    /**
     * 显示大图
     *
     * @param baseContext
     * @param mlist_img
     * @param position
     */
    public static void ShowLargeImg(Context baseContext, List<String> mlist_img, int position) {
        Intent intent = new Intent(baseContext, ImagePagerActivity.class);
        intent.putExtra(
                ImagePagerActivity.EXTRA_IMAGE_URLS,
                mlist_img.toArray(new String[mlist_img.size()]));
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_SAVE, false);
        baseContext.startActivity(intent);
    }


    /*recycler 设置最大高度*/
    public static void setRecyclerMaxHeight(final RecyclerView recyclerview, final int maxHeight){
        recyclerview.getViewTreeObserver().addOnGlobalLayoutListener
                (new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams)
                                recyclerview.getLayoutParams();
                        recyclerview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        if (maxHeight==-2){
                            params.height = -2;
                        }else{
                            if (recyclerview.getHeight() > DisplayUtil.dp2px(maxHeight)) {
                                params.height = DisplayUtil.dp2px(maxHeight);
                            } else {
                                if (recyclerview.getHeight()<DisplayUtil.dp2px(maxHeight)){
                                    params.height = -2;
                                }else{
                                    params.height = recyclerview.getHeight();
                                }

                            }
                        }
                        recyclerview.setLayoutParams(params);
                    }
                });
    }


    /*更改购物车数量*/
    public static void updateCartNum(Context context, final TextView textView){
        if (TextUtils.isEmpty(PreferencesUtils.getString(context, Params.UserID))) {
            textView.setText("0");
            textView.setVisibility(View.GONE);
            return;
        }
        final Request<String> request = getRequest(context,Params.getCartGoodsNum,true);
        CallServer.getRequestInstance().add(context, 0, request,
                new CustomHttpListener<CartNumBean>(context,true,CartNumBean.class) {
                    @Override
                    public void doWork(CartNumBean result, String code) {
                        try{
                            int count = result.getData().getCount();
                            FormatterUtil.sortBadger(count,textView);
                        }catch (Exception e){

                        }
                    }
                },false,false);

    }

    /* {"code":1,"msg":"获取数据成功！",
    "data":[{"message_type":1,"num":0,"title":"","time":""},
    {"message_type":2,"num":0,"title":"","time":""}]}*/
    /*更改消息数量*/
    public static void updateMsgNum(Context context,final TextView textView){
        if (TextUtils.isEmpty(PreferencesUtils.getString(context, Params.UserID))) {
            textView.setText("0");
            textView.setVisibility(View.GONE);
            return;
        }
        final Request<String> request = getRequest(context,Params.getMsgUnReadNum,true);
        CallServer.getRequestInstance().add(context, 0, request,
                new CustomHttpListener<MessageUnReadBean>(context,true,MessageUnReadBean.class) {
                    @Override
                    public void doWork(MessageUnReadBean result, String code) {
                        try{
                            int count = 0;
                            if (result.getData()!=null && result.getData().size()>0){
                                for (int i = 0; i< result.getData().size();i++){
                                    count = count+result.getData().get(i).getNum();
                                }
                            }
                            FormatterUtil.sortBadger(count,textView);
                        }catch (Exception e){

                        }
                    }
                },false,false);
    }


    public static void setAlias(Context context, String alias){
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = TagAliasOperatorHelper.ACTION_SET;
        TagAliasOperatorHelper.sequence++;
        tagAliasBean.alias = alias;
        tagAliasBean.isAliasAction = true;
        TagAliasOperatorHelper.getInstance().handleAction
                (context.getApplicationContext(),TagAliasOperatorHelper.sequence,tagAliasBean);



    }


    public static void setTags(Context context, String tags){
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = TagAliasOperatorHelper.ACTION_SET;
        TagAliasOperatorHelper.sequence++;
        Set<String> tagSet = new LinkedHashSet<String>();
        tagSet.add(tags);
        tagAliasBean.tags = tagSet;
        tagAliasBean.isAliasAction = false;
        TagAliasOperatorHelper.getInstance().handleAction
                (context.getApplicationContext(),TagAliasOperatorHelper.sequence,tagAliasBean);

    }

    public static void share(final Activity activity, final String title, final String content){
        /*if (Params.shareContentBean!=null){
            ShareUtil.getInstance().share(activity,
                    Params.shareContentBean.getData().getUrl(),
                    title,
                    content,
                    Params.shareContentBean.getData().getImgurl());
        }else{*/
            final Request<String> request = getRequest(activity,Params.getShareContent,true);
            request.add("flag",3);
            CallServer.getRequestInstance().add(activity, 0, request,
                    new CustomHttpListener<ShareContentBean>(activity,true,ShareContentBean.class) {
                        @Override
                        public void doWork(ShareContentBean result, String code) {
                            try{
                                Params.shareContentBean = result;
                                ShareUtil.getInstance().share(activity,
                                        Params.shareContentBean.getData().getUrl(),
                                        title,
                                        content,
                                        Params.shareContentBean.getData().getImgurl());
                            }catch (Exception e){
                                e.printStackTrace();
                                MUIToast.show(activity,result.getMsg());
                            }
                        }
                    },false,false);
       /* }*/
    }

    public static void share(final Activity activity, final String title,int type){
        /*if (Params.shareContentBean!=null){
            ShareUtil.getInstance().share(activity,
                    Params.shareContentBean.getData().getUrl(),
                    title,
                    Params.shareContentBean.getData().getContent(),
                    Params.shareContentBean.getData().getImgurl());
        }else{*/
            final Request<String> request = getRequest(activity,Params.getShareContent,true);
            request.add("flag",type);
            CallServer.getRequestInstance().add(activity, 0, request,
                    new CustomHttpListener<ShareContentBean>(activity,true,ShareContentBean.class) {
                        @Override
                        public void doWork(ShareContentBean result, String code) {
                            try{
                                Params.shareContentBean = result;
                                ShareUtil.getInstance().share(activity,
                                        Params.shareContentBean.getData().getUrl(),
                                        title,
                                        Params.shareContentBean.getData().getContent(),
                                        Params.shareContentBean.getData().getImgurl());
                            }catch (Exception e){
                                e.printStackTrace();
                                MUIToast.show(activity,result.getMsg());
                            }
                        }
                    },false,false);
        /*}*/
    }

    /*public static void share(final Activity activity){
       *//* if (Params.shareContentBean!=null){
            ShareUtil.getInstance().share(activity,
                    Params.shareContentBean.getData().getUrl(),
                    Params.shareContentBean.getData().getTitle(),
                    Params.shareContentBean.getData().getContent(),
                    Params.shareContentBean.getData().getImgurl());
        }else{*//*
            final Request<String> request = getRequest(activity,Params.getShareContent,true);
            request.add("flag",1);
            CallServer.getRequestInstance().add(activity, 0, request,
                    new CustomHttpListener<ShareContentBean>(activity,true,ShareContentBean.class) {
                        @Override
                        public void doWork(ShareContentBean result, String code) {
                            try{
                                Params.shareContentBean = result;
                                ShareUtil.getInstance().share(activity,
                                        Params.shareContentBean.getData().getUrl(),
                                        Params.shareContentBean.getData().getTitle(),
                                        Params.shareContentBean.getData().getContent(),
                                        Params.shareContentBean.getData().getImgurl());
                            }catch (Exception e){
                                e.printStackTrace();
                                MUIToast.show(activity,result.getMsg());
                            }
                        }
                    },false,false);
       *//* }*//*
    }*/

    public static void doLocalExit(Context baseContext){
        PreferencesUtils.putString(baseContext,Params.KEY_SEX,"");
        PreferencesUtils.putString(baseContext,Params.KEY_BIRTHDAY,"");
       /* PreferencesUtils.putString(baseContext,Params.KEY_PHONE,"");
        PreferencesUtils.putString(baseContext,Params.KEY_PWD,"");*/
        PreferencesUtils.putString(baseContext,Params.KEY_NICKNAME,"");
        PreferencesUtils.putString(baseContext,Params.KEY_USERHEAD,"");
        PreferencesUtils.putString(baseContext,Params.KEY_USERSIGN,"");
        PreferencesUtils.putString(baseContext,Params.UserID,"");
        PreferencesUtils.putString(baseContext,Params.Token,"");
        //baseContext.startActivity(new Intent(baseContext,LoginActivity.class));
        RongIM.getInstance().logout();
        App.finishAllActivityExceptLogin();
    }

    public static void doExit(final Context baseContext,boolean showDialog){
        PreferencesUtils.putString(baseContext,Params.KEY_SEX,"");
        PreferencesUtils.putString(baseContext,Params.KEY_BIRTHDAY,"");
       /* PreferencesUtils.putString(baseContext,Params.KEY_PHONE,"");
        PreferencesUtils.putString(baseContext,Params.KEY_PWD,"");*/
        PreferencesUtils.putString(baseContext,Params.KEY_NICKNAME,"");
        PreferencesUtils.putString(baseContext,Params.KEY_USERHEAD,"");
        PreferencesUtils.putString(baseContext,Params.KEY_USERSIGN,"");
        PreferencesUtils.putString(baseContext,Params.UserID,"");
        PreferencesUtils.putString(baseContext,Params.Token,"");
        netExit(baseContext);
        RongIM.getInstance().logout();
        if (showDialog){
            try{
                ActionDialog actionDialog = new ActionDialog(ActivityStack.getScreenManager().currentActivity(),"您的账号被异地登录\n您被迫下线",2);
                actionDialog.setOnlyConfirmListeer(new ActionDialog.OnOnlyConfirmListeer() {
                    @Override
                    public void onOnlyConfirm() {
                        baseContext.startActivity(new Intent(baseContext,LoginActivity.class));
                        App.finishAllActivityExceptLogin();
                    }
                });
                actionDialog.show();
            }catch (Exception e){
                e.printStackTrace();
                baseContext.startActivity(new Intent(baseContext,LoginActivity.class));
                App.finishAllActivityExceptLogin();
            }
        }else{
            baseContext.startActivity(new Intent(baseContext,LoginActivity.class));
            App.finishAllActivityExceptLogin();
        }

    }


    public static void netExit(Context context){
        if (TextUtils.isEmpty(PreferencesUtils.getString(context, Params.UserID))) {
            return;
        }
        Request<String> mRequest01 = getRequest(context,Params.exitLogin,true);
        CallServer.getRequestInstance().add(context, 0, mRequest01,
                new CustomHttpListener<BaseBean>(context,true,BaseBean.class) {
                    @Override
                    public void doWork(BaseBean result, String code) {
                        try{

                        }catch (Exception e){

                        }
                    }
                },false,false);
    }

     /*清空发现未读消息数量*/
     public static void clearFindMsgNum(Context context,final TextView textView){
         if (TextUtils.isEmpty(PreferencesUtils.getString(context, Params.UserID))) {
             textView.setText("0");
             textView.setVisibility(View.GONE);
             return;
         }
         final Request<String> request = getRequest(context,Params.clearFindMsgNum,true);
         CallServer.getRequestInstance().add(context, 0, request,
                 new CustomHttpListener<BaseBean>(context,true,BaseBean.class) {
                     @Override
                     public void doWork(BaseBean result, String code) {
                         try{
                             int count = 0;
                             FormatterUtil.sortBadger(count,textView);
                         }catch (Exception e){

                         }
                     }
                 },false,false);
     }

    /*获取发现未读消息数量*/
    public static void updateFindMsgNum(Context context,final TextView textView){
        if (TextUtils.isEmpty(PreferencesUtils.getString(context, Params.UserID))) {
            textView.setText("0");
            textView.setVisibility(View.GONE);
            return;
        }
        final Request<String> request = getRequest(context,Params.getFindMsgNum,true);
        CallServer.getRequestInstance().add(context, 0, request,
                new CustomHttpListener<FindMsgUnReadBean>(context,true,FindMsgUnReadBean.class) {
                    @Override
                    public void doWork(FindMsgUnReadBean result, String code) {
                        try{
                            int count = FormatterUtil.StringToInt(result.getData().getCount(),0);
                            FormatterUtil.sortBadger(count,textView);
                        }catch (Exception e){

                        }
                    }
                },false,false);
    }




}
