package com.meida.cosmeticsshopuser.utils;

import android.os.Build;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Administrator on 2018/10/30 0030.
 */

public class WebViewUtil {

    public static void setWebUrl(WebView webView,String url){
        webView.getSettings().setLoadWithOverviewMode(true);
        WebSettings settings2 =webView.getSettings();
        settings2.setJavaScriptEnabled(true);
        settings2.setBuiltInZoomControls(true);
        settings2.setDomStorageEnabled(true);
        settings2.setDisplayZoomControls(false);//设定缩放控件隐藏
        settings2.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings2.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings2.setBlockNetworkImage(false);//解决图片不显示
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            settings2.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }

    public static void setWebHtml(final WebView webview, String html) {
        WebSettings settings = webview.getSettings();
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setJavaScriptEnabled(true);
        settings.setSupportMultipleWindows(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        settings.setUseWideViewPort(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setDomStorageEnabled(true);
        settings.setDisplayZoomControls(false);//设定缩放控件隐藏
        settings.setBlockNetworkImage(false);//解决图片不显示
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webview.setWebChromeClient(new WebChromeClient());
        webview.setWebViewClient(new WebViewClient());
        webview.loadDataWithBaseURL(null,html,"text/html","utf-8",null);

    }
}
