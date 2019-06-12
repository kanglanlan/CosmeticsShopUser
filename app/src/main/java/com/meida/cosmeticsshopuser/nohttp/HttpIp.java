package com.meida.cosmeticsshopuser.nohttp;

import com.yolanda.nohttp.RequestMethod;

/**
 * Created by Administrator on 2018/12/22 0022.
 */

public class HttpIp {

    public static final String http = "http://";

    final public static RequestMethod POST = RequestMethod.POST;
    final public static RequestMethod GET = RequestMethod.GET;
    /*正式服务器*/
    public final static String BaseIp = "http://app.waiyueng.com";
    /*测试服务器*/
    //public final static String BaseIp = "http://hzp.weiruanmeng.com";
    /*http://hzp.weiruanmeng.com/api/likeface/Shop/getFirstData*/

    public final static String BaseDataIp = BaseIp + "/api/"; //  接口ip

    /*后台地址
    *
    *
    * */

}
