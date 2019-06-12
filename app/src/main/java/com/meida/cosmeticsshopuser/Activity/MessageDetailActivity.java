package com.meida.cosmeticsshopuser.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.meida.cosmeticsshopuser.Bean.BaseWebDetailBean;
import com.meida.cosmeticsshopuser.MyView.ObservableScrollView;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.WebX5.X5WebView;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.LoggerUtil;
import com.meida.cosmeticsshopuser.utils.PreferencesUtils;
import com.meida.cosmeticsshopuser.utils.ProjectUtils;
import com.meida.cosmeticsshopuser.utils.WebViewUtil;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;

public class MessageDetailActivity extends BaseActivity {

    private boolean canShare = false;

    private String id = "";

    X5WebView webview;

    private String ipPath = "";


    private ValueCallback<Uri> uploadFile;


    private ImageView share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        changeTitle("详情");
        canShare = getIntent().getBooleanExtra("canShare",false);
        if (canShare){
            share = (ImageView) findViewById(R.id.img_title_rigth);
            share.setImageResource(R.drawable.msg_share);
            share.setVisibility(View.VISIBLE);

        }
        id = getIntent().getStringExtra("id");
        String ip = getIntent().getStringExtra("ip");
        if (TextUtils.isEmpty(ip)){
            ipPath = Params.getMessageDetail;
        }else{
            ipPath = ip;
        }
        webview = (X5WebView) findViewById(R.id.webview);
        setWeb();
        getDetail();
        //webview.loadDataWithBaseURL(null,"&lt;p&gt;1&lt;\\/p&gt;", "text/html", "utf-8", null);

        if(webview.getX5WebViewExtension()!=null){
            LoggerUtil.e("x5 core");
        }else{
            LoggerUtil.e("sys core");
        }

    }

    /*订单消息 不是 html  文本*/
    private void getDetail(){
        mRequest01 = getRequest(ipPath,true);
        mRequest01.add("id",id);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<BaseWebDetailBean>(baseContext,true,BaseWebDetailBean.class) {
                    @Override
                    public void doWork(final BaseWebDetailBean result, String code) {
                        //WebViewUtil.setWebHtml(webview,result.getData().getContent());
                        webview.loadDataWithBaseURL(null,result.getData().getContent(), "text/html", "utf-8", null);

                        share.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (ProjectUtils.needLogin(baseContext)){
                                    return;
                                }
                                ProjectUtils.share(MessageDetailActivity.this,result.getData().getPost_title(),result.getData().getPost_excerpt());
                            }
                        });

                    }/*GsonTest/html  text/html*/
                },false,false);
    }


    private void setWeb(){
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(com.tencent.smtt.sdk.WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        webview.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsConfirm(com.tencent.smtt.sdk.WebView arg0, String arg1, String arg2,
                                       JsResult arg3) {
                return super.onJsConfirm(arg0, arg1, arg2, arg3);
            }

            View myVideoView;
            View myNormalView;
            IX5WebChromeClient.CustomViewCallback callback;

            // /////////////////////////////////////////////////////////
            //
            /**
             * 全屏播放配置
             */
            @Override
            public void onShowCustomView(View view,
                                         IX5WebChromeClient.CustomViewCallback customViewCallback) {
                FrameLayout normalView = (FrameLayout) findViewById(R.id.web_filechooser);
                ViewGroup viewGroup = (ViewGroup) normalView.getParent();
                viewGroup.removeView(normalView);
                viewGroup.addView(view);
                myVideoView = view;
                myNormalView = normalView;
                callback = customViewCallback;
            }

            @Override
            public void onHideCustomView() {
                if (callback != null) {
                    callback.onCustomViewHidden();
                    callback = null;
                }
                if (myVideoView != null) {
                    ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
                    viewGroup.removeView(myVideoView);
                    viewGroup.addView(myNormalView);
                }
            }

            @Override
            public boolean onJsAlert(com.tencent.smtt.sdk.WebView arg0, String arg1, String arg2,
                                     JsResult arg3) {
                /**
                 * 这里写入你自定义的window alert
                 */
                return super.onJsAlert(null, arg1, arg2, arg3);
            }
        });

        WebSettings webSetting = webview.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);
        long time = System.currentTimeMillis();
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webview != null && webview.canGoBack()) {
                webview.goBack();
                return true;
            } else
                return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    if (null != uploadFile) {
                        Uri result = data == null || resultCode != RESULT_OK ? null
                                : data.getData();
                        uploadFile.onReceiveValue(result);
                        uploadFile = null;
                    }
                    break;
                default:
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (null != uploadFile) {
                uploadFile.onReceiveValue(null);
                uploadFile = null;
            }

        }

    }


    @Override
    protected void onDestroy() {
        if (webview != null)
            webview.destroy();
        super.onDestroy();
    }



}
