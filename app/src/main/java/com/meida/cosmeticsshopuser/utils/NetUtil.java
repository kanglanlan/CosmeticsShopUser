package com.meida.cosmeticsshopuser.utils;

import android.content.Context;

import com.meida.cosmeticsshopuser.Bean.CommentData;
import com.meida.cosmeticsshopuser.Bean.CommonDataM;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.HttpIp;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;

import static com.meida.cosmeticsshopuser.nohttp.Params.APP_TIMECHA;


/**
 * Created by Administrator on 2018/11/6 0006.
 */

public class NetUtil {

    public static String getNonce() {
        int no = (int) ((Math.random() * 9 + 1) * 100000);
        return no + "";
    }


    public static String gettimes() {
        String ti = APP_TIMECHA
                + System.currentTimeMillis() / 1000 + "";
        return ti;
    }
    public static void getServerTime(Context baseContext){
        Request<String> mRequest_GetData03 = NoHttp.createStringRequest(HttpIp.BaseDataIp+ Params.TEXTURL, RequestMethod.POST);
        CallServer.getRequestInstance().add(baseContext, 1,
                mRequest_GetData03, new CustomHttpListener(baseContext, true, CommonDataM.class) {
                    @Override
                    public void doWork(Object data, String code) {
                        CommentData times = (CommentData) data;
                        APP_TIMECHA = Long.parseLong(times.getData().getRequest_time()) - System.currentTimeMillis() / 1000;

                    }

                }, false, false);
    }

}
