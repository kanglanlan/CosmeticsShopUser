package com.meida.cosmeticsshopuser.utils;

import android.text.TextUtils;

import static com.meida.cosmeticsshopuser.utils.TypeUtil.OrderType.DFH;
import static com.meida.cosmeticsshopuser.utils.TypeUtil.OrderType.DSH;
import static com.meida.cosmeticsshopuser.utils.TypeUtil.OrderType.DZF;
import static com.meida.cosmeticsshopuser.utils.TypeUtil.OrderType.GB;
import static com.meida.cosmeticsshopuser.utils.TypeUtil.OrderType.SC;
import static com.meida.cosmeticsshopuser.utils.TypeUtil.OrderType.THWC;
import static com.meida.cosmeticsshopuser.utils.TypeUtil.OrderType.THZ;
import static com.meida.cosmeticsshopuser.utils.TypeUtil.OrderType.YPJ;
import static com.meida.cosmeticsshopuser.utils.TypeUtil.OrderType.YSH;

/**
 * Created by Administrator on 2019/1/5 0005.
 */

public class TypeUtil {

    /*订单的几个状态*/
    public enum OrderType{

        DZF,/*待支付*/
        DFH,/*待发货*/
        DSH,/*待收货*/
        YSH,/*已收货*/
        YPJ,/*已评价*/
        GB,/*关闭*/
        SC,/*删除*/

        THZ,/*退货中*/
        THWC/*退货完成*/

        /*平台派单 12*/


    }

    public static String getOrderStatusStr(String status){

        if (TextUtils.isEmpty(status)){
            return "";
        }

        switch (status){
            case "1":
                return "待支付";
            case "2":
                return "待发货";
            case "3":
                return "待收货";
            case "4":
                return "已收货";
            case "5":
                return "已评价";
            case "6":
                return "退货中";
            case "7":
                return "退货完成";
            case "9":
                return "关闭";
            case "99":
                return "删除";

        }
        return "";
    }


    public static OrderType getOrderType(String status){
        if (TextUtils.isEmpty(status)){
            return null;
        }

        switch (status){
            case "1":
                return DZF;
            case "2":
                return DFH;
            case "3":
                return DSH;
            case "4":
                return YSH;
            case "5":
                return YPJ;
            case "6":
                return THZ;
            case "7":
                return THWC;
            case "9":
                return GB;
            case "99":
                return SC;

        }

        return null;

    }


    public enum DeliveryMode{

        TCPS,
        SJZS,
        SFKD,

    }


    /*1同城配送 2商家自送 3第三方快递*/
    public static String getDeliveryModeStr(int status){
        return  getDeliveryModeStr(status+"");
    }
    public static String getDeliveryModeStr(String status){
        if (TextUtils.isEmpty(status)){
            return "";
        }
        switch (status){
            case "1":
                return "同城配送";
            case "2":
                return "商家自送";
            case "3":
                return "快递";
        }
        return "";
    }


    /*支付方式  1支付宝  2微信*/
    public enum PayMode{

        WX,
        ALIPAY

    }

    public static PayMode getPayMode(String str){
        if (TextUtils.isEmpty(str)){
            return null;
        }

        switch (str){
            case "1":
                return PayMode.ALIPAY;
            case "2":
                return PayMode.WX;
        }

        return null;

    }


    public static String getPayModeStr(String str){
        if (TextUtils.isEmpty(str)){
            return "其它";
        }

        switch (str){
            case "1":
                return "在线支付(支付宝支付)";
            case "2":
                return "在线支付(微信支付)";
        }

        return "其它";

    }



}
