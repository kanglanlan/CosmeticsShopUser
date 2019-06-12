package com.meida.cosmeticsshopuser.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meida.cosmeticsshopuser.base.App;
import com.meida.cosmeticsshopuser.R;

/**
 * Created by Administrator on 2018/12/25 0025.
 */

public class GlideUtils {

    /*加载头像*/
    @SuppressLint("CheckResult")
    public static void loadHead(String url, ImageView img){
        int pd = R.drawable.ico_img115;
        RequestOptions options = new RequestOptions();
        options.error(pd);
        options.placeholder(pd);
        options.centerCrop();
        Glide.with(App.getApplication()).load(url).apply(options).into(img);
    }

    /*加载首页轮播图*/
    @SuppressLint("CheckResult")
    public static void loadBanner(String url, ImageView img){
        int pd = R.mipmap.pd_banner;
        RequestOptions options = new RequestOptions();
        options.error(pd);
        options.placeholder(pd);
        options.centerCrop();
        Glide.with(App.getApplication()).load(url).apply(options).into(img);
    }

    /*商品图 正方形*/
    @SuppressLint("CheckResult")
    public static void loadGoods(String url,ImageView img){
        int pd = R.mipmap.moren;
        RequestOptions options = new RequestOptions();
        options.error(pd);
        options.placeholder(pd);
        options.centerCrop();
        Glide.with(App.getApplication()).load(url).apply(options).into(img);
    }

    /*商品图 正方形*/
    @SuppressLint("CheckResult")
    public static void loadGoods2(String urls,ImageView img){
        if (TextUtils.isEmpty(urls)){
            return;
        }
        String str[] = urls.split(",");
        if (str.length<=0){
            return;
        }
        String url = str[0];
        int pd = R.mipmap.moren;
        RequestOptions options = new RequestOptions();
        options.error(pd);
        options.placeholder(pd);
        options.centerCrop();
        Glide.with(App.getApplication()).load(url).apply(options).into(img);
    }

    /*资讯图*/
    @SuppressLint("CheckResult")
    public static void loadNews(String url, ImageView img){
        int pd = R.mipmap.pd_news;
        RequestOptions options = new RequestOptions();
        options.error(pd);
        options.placeholder(pd);
        options.centerCrop();
        Glide.with(App.getApplication()).load(url).apply(options).into(img);
    }

    /*推荐商品图*/
    @SuppressLint("CheckResult")
    public static void loadRecommendProduct(String url, ImageView img){
        int pd = R.mipmap.pd_news;
        RequestOptions options = new RequestOptions();
        options.error(pd);
        options.placeholder(pd);
        options.centerCrop();
        Glide.with(App.getApplication()).load(url).apply(options).into(img);
    }

    /*店铺图logo rect*/
    @SuppressLint("CheckResult")
    public static void loadShop(String url, ImageView img){
        int pd = R.mipmap.moren;
        RequestOptions options = new RequestOptions();
        options.error(pd);
        options.placeholder(pd);
        options.centerCrop();
        Glide.with(App.getApplication()).load(url).apply(options).into(img);
    }

    /*店铺图背景图*/
    @SuppressLint("CheckResult")
    public static void loadShopBg(String url, ImageView img){
        int pd = R.mipmap.pd_shop_bg;
        RequestOptions options = new RequestOptions();
        options.error(pd);
        options.placeholder(null);
        options.centerCrop();
        Glide.with(App.getApplication()).load(url).apply(options).into(img);
    }


    /*店铺图logo rect*/
    @SuppressLint("CheckResult")
    public static void loadImg(String url, ImageView img){
        int pd = R.mipmap.moren;
        RequestOptions options = new RequestOptions();
        options.error(pd);
        options.placeholder(pd);
        options.centerCrop();
        Glide.with(App.getApplication()).load(url).apply(options).into(img);
    }

    /*其它 自定义*/
    public static void loadImg(String url, ImageView img,RequestOptions options){
        Glide.with(App.getApplication()).load(url).apply(options).into(img);
    }


}
