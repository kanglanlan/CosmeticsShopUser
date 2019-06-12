package com.meida.cosmeticsshopuser.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.meida.cosmeticsshopuser.Bean.GoodsDetailBean;
import com.meida.cosmeticsshopuser.base.BaseFragment;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.utils.WebViewUtil;

/**
 * Created by Administrator on 2018/12/21 0021.
 */

public class FragmentWebView extends BaseFragment{

    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initEvent();
        initData();
    }

    private void initView(View rootView){
        webView = rootView.findViewById(R.id.webview);
        //webView.loadUrl("http://www.baidu.com/");
    }

    private void initEvent(){

    }

    private void initData(){

    }

    public static FragmentWebView newInstance() {
        Bundle bundle = new Bundle();
        FragmentWebView fragment = new FragmentWebView();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setData(GoodsDetailBean.DataBean data){
        WebViewUtil.setWebHtml(webView,data.getContent());
    }


}
