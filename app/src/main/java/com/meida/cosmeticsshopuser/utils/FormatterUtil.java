package com.meida.cosmeticsshopuser.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.nohttp.Params;
import com.willy.ratingbar.ScaleRatingBar;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Administrator on 2018/12/21 0021.
 */

public class FormatterUtil {


    public static String formatBigNumber(long num){

        try{
            if (num>=10000){
                double d = num*1.0/10000;
                NumberFormat nf = NumberFormat.getInstance();
                nf.setMaximumFractionDigits(2);
                return nf.format(d)+"万";
            }/*else if (num>=1000){
                double d = num*1.0/1000;
                NumberFormat nf = NumberFormat.getInstance();
                nf.setMaximumFractionDigits(2);
                return nf.format(d)+"千";
            }*/
        }catch (Exception e){

        }
        return num+"";
    }


    public static String formatBigNumber(String num){

        long a = 0;
        try{
            a = Long.parseLong(num);
            if (a>=10000){
                double d = a*1.0/10000;
                NumberFormat nf = NumberFormat.getInstance();
                nf.setMaximumFractionDigits(2);
                return nf.format(d)+"万";
            }/*else if (a>=1000){
                double d = a*1.0/1000;
                NumberFormat nf = NumberFormat.getInstance();
                nf.setMaximumFractionDigits(2);
                return nf.format(d)+"千";
            }*/
        }catch (Exception e){

        }
        return num;
    }

    public static String formatBigNumber(String num,String tag){

        long a = 0;
        try{
            a = Long.parseLong(num);
            if (a>=10000){
                double d = a*1.0/10000;
                NumberFormat nf = NumberFormat.getInstance();
                nf.setMaximumFractionDigits(2);
                return nf.format(d)+"tag";
            }/*else if (a>=1000){
                double d = a*1.0/1000;
                NumberFormat nf = NumberFormat.getInstance();
                nf.setMaximumFractionDigits(2);
                return nf.format(d)+"千";
            }*/
        }catch (Exception e){

        }
        return num;
    }

    /*String 转 double */
    public static double StringToDouble(String string){
        double d = 0;
        try{
            d = Double.parseDouble(string);
        }catch (Exception e){
            try{
                d = Long.parseLong(string)+0.0;
            }catch (Exception e1){

            }
        }
        return d;
    }


    /*String 转 double */
    public static int StringToInt(String string,int defaultValue){
        int d = defaultValue;
        try{
            d = Integer.parseInt(string);
        }catch (Exception e){
        }
        return d;
    }

    /*价格显示 ，最低 两位 小数*/
    public static String formatPrice(double d){
        DecimalFormat df = new DecimalFormat("##0.00");
        return df.format(d);
    }

    public static String formatPrice2(double d){
        DecimalFormat df = new DecimalFormat("##0.00");
        return Params.RMB+df.format(d);
    }

    /*string 转 float*/
    public static float StringToFloat(String string){
        float f = 0.0f;
        try{
            f = Float.parseFloat(string);
        }catch (Exception e){

        }
        return f;
    }

    /*距离格式化*/
    @SuppressLint("SetTextI18n")
    public static void formatDistance(long l , TextView textView){
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
       /* if (l>=10000){
            double d = l*1.0/10000;
            textView.setText(nf.format(d)+"万m");
        }else */if (l>=1000){
            double d = l*1.0/1000;
            textView.setText(nf.format(d)+"km");
        }else{
            double d = l*1.0;
            textView.setText(nf.format(d)+"m");
        }

    }

    /*销量格式化*/
    @SuppressLint("SetTextI18n")
    public static void formatSaleNum(long a, long b, TextView textView){
        long l = a+b;
        /*if (l>=10000){
            long x = l/10000;
            textView.setText("月销"+x+"万+");
        }else if (l>1000){
            long x = l/1000;
            textView.setText("月销"+x+"千+");
        }else{
            textView.setText("月销"+l);
        }*/

        if (l>3000){
            textView.setText("月销3千+");
        }else{
            textView.setText("月销"+l);
        }

    }


    @SuppressLint("SetTextI18n")
    public static void formatSaleNum2(long a, long b, TextView textView){
        long l = a+b;
        /*if (l>=10000){
            long x = l/10000;
            textView.setText("销量："+x+"万+");
        }else if (l>1000){
            long x = l/1000;
            textView.setText("销量："+x+"千+");
        }else{
            textView.setText("销量："+l);
        }*/

        if (l>3000){
            textView.setText("月销3千+");
        }else{
            textView.setText("月销"+l);
        }

    }


    /*格式化价钱  不带羊头符， 两位小数，整数部分大写，小数部分小写 */
    public static void formatPrice(String str,TextView textView){
        try{
            double d = Double.parseDouble(str);
            DecimalFormat df = new DecimalFormat("##0.00");
            String priceStr = df.format(d);
            textView.setTextSize(13);
            int index = priceStr.indexOf(".");
            SpanUtil spanUtil = new SpanUtil(textView.getContext());
            spanUtil.setSizeSpan(16,priceStr,0,index,textView);
        }catch (Exception e){
            e.printStackTrace();
            if (TextUtils.isEmpty(str)){
                textView.setText("??");
            }else{
                textView.setText(str);
            }
        }
    }

    /*格式化价钱  带羊头符， 两位小数，整数部分大写，小数部分小写 */
    public static void formatPrice2(String str,TextView textView){
        try{
            double d = Double.parseDouble(str);
            DecimalFormat df = new DecimalFormat("##0.00");
            String priceStr = "¥"+df.format(d);
            textView.setTextSize(13);
            int index = priceStr.indexOf(".");
            SpanUtil spanUtil = new SpanUtil(textView.getContext());
            spanUtil.setSizeSpan(16,priceStr,1,index+1,textView);
        }catch (Exception e){
            if (TextUtils.isEmpty(str)){
                textView.setText("??");
            }else{
                textView.setText(str);
            }
        }
    }


    /*格式化好评 小数点 0~1 转化为 百分比*/
    @SuppressLint("SetTextI18n")
    public static void formatPositiveRate(String str, TextView textView){
        try{
            double d = Double.parseDouble(str);
            DecimalFormat df = new DecimalFormat("##0");
            textView.setText(df.format(d)+"%好评");
        }catch (Exception e){
            textView.setText(str+"好评");
        }
    }

    /*星星评分格式化  double 取一位 小数*/
    public static String formatStarValue(String str){
        try{
            double d = Double.parseDouble(str);
            DecimalFormat df = new DecimalFormat("##0.0");
            return df.format(d);
        }catch (Exception e){

        }
        return str;
    }

    /*设置星星进度值  目前 取 整数部分*/
    public static void setStarRating(String starRating, ScaleRatingBar bar){
        try{
            float f = StringToFloat(starRating);
            int a = (int)f;
            bar.setRating(a*1.0f);
        }catch (Exception e){
            e.printStackTrace();
            bar.setRating(0.0f);
        }

    }


    /*评论数 等 普通数字格式化*/
    public static String formatNumber(String string){
        return string;
    }

    public static String formatNumber(long l){
        return l+"";
    }

    /*格式化手机号 中间 四位  隐藏*/
    public static void formatPhone(String string,TextView textView){
        try{
            if ( (!TextUtils.isEmpty(string)) && string.length()>=11 ){
                StringBuilder builder = new StringBuilder();
                builder.append(string.substring(0,3));
                builder.append("****");
                builder.append(string.substring(string.length()-4,string.length()));
                textView.setText(builder.toString());
            }
        }catch (Exception e){
            textView.setText(string);
        }
    }



    /*获取有效数字位 用户优惠券展示*/
    /*优惠券显示处理*/
    public static String sortCouponPrice(String orignalStr){
        if (TextUtils.isEmpty(orignalStr) || "null".equals(orignalStr)){
            return "0";
        }

        try{
            double d = Double.parseDouble(orignalStr);
            NumberFormat nf = NumberFormat.getInstance();
            nf.setGroupingUsed(false);
            nf.setMaximumFractionDigits(2);
            return nf.format(d);
        }catch (Exception e){
        }
        return orignalStr;
    }

    /*价格显示处理*/
    public static String sortPrice(String orignalStr){
        if (TextUtils.isEmpty(orignalStr) || "null".equals(orignalStr)){
            return "";
        }

        try{
            double d = Double.parseDouble(orignalStr);
            NumberFormat nf = NumberFormat.getInstance();
            nf.setGroupingUsed(false);
            nf.setMinimumFractionDigits(2);
            return nf.format(d);
        }catch (Exception e){
        }
        return orignalStr;
    }


    public static void sortBadger(int num,TextView textView){
        if (num<=0){
            textView.setText("0");
            textView.setVisibility(View.GONE);
        }else if (num>99){
            textView.setText("99+");
            textView.setVisibility(View.VISIBLE);
        }else{
            textView.setText(String.valueOf(num));
            textView.setVisibility(View.VISIBLE);
        }
    }

    public static String getMaxText(String text){
        if (TextUtils.isEmpty(text)){
            return "";
        }
        if (text.length()>500){
            return text.substring(0,500)+"...";
        }
        return text;
    }




}
