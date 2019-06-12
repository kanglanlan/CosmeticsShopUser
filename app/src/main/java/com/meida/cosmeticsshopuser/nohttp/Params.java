package com.meida.cosmeticsshopuser.nohttp;

import android.os.Environment;
import android.widget.ThemedSpinnerAdapter;

import com.amap.api.location.AMapLocation;
import com.meida.cosmeticsshopuser.Bean.CityBean;
import com.meida.cosmeticsshopuser.Bean.ShareContentBean;

import java.io.File;

/**
 * Created by Administrator on 2018/12/25 0025.
 */

public class Params {

    /*当下支付成功的订单id*/
    public static String orderid = "";


    /*公共变量 存储 定位信息 没有 定位成功 shi  null*/
    public static AMapLocation aMapLocation;

    /*选择的位置信息*/
    public static CityBean.Area chooseArea;

    /*定位 默认点*/
    public static String LOCATE_CITY = "深圳市";
    public static String LOCATE_LAT = "114.085947";
    public static String LOCATE_LNG = "22.547";
    public static String LOCATE_ID = "440300";

    public static int locateType;/*  1  定位成功  2  选择别的城市   -1  定位失败*/


    public static final  String UM_KEY = "5c32c40bb465f56e2e0014c5";
    public static final String WX_APP_ID = "wx9e557cf2b1247de0";
    public static final String WX_SECRET = "9d0c93884fa42b7378761cbe97677773";
    public static final String RONG_KEY = "uwd1c0sxupdv1";
    public static final String RONG_SECRET = "VB6AP4WmdhZk";
    public static final String QQ_APP_ID = "1107956680";
    public static final String QQ_APP_KEY = "7tVH33wRQtWUERCQ";
    public static final String JPUSH_KEY = "049f0d677efac95c90da6050";
    public static final String JPUSH_SECRET = "223942ee41c3a0ac764fba9d";



    public static final String RMB = "¥";
    public static final String RMB_ = "¥ ";


    //接口使用
    public static long APP_TIMECHA;

    /*接口地址*/
    public static final String TEXTURL = "App.Site.Index";   //获取时间戳

    /*买家首页信息*/
    public static final String getHomeData = "likeface/Shop/getFirstData";
    /*获取系统参数*/
    public static final String getSystemData = "likeface/Public/getSystemSet";
    /*获取搜索范围 地理距离*/
    public static final String getDistanceRange = "likeface/Public/getRangeList";
    /*获取热门搜索*/
    public static final String getHotSearch = "likeface/Public/getSearchList";
    /*获取城市列表*/
    public static final String getCityList = "likeface/Public/getCityList";
    /*获取店铺商品混合列表列表*/
    public static final String getShopGoodsList = "likeface/Goods/getGoodsListVague";
    public static final String getShopInGoodsList = "likeface/Shopping_mall/getShopGoodsList";
    /*商城首页数据*/
    public static final String getMallData = "likeface/Shopping_mall/getFirstData";
    /*获取店铺或商品分类*/
    public static final String getCategory = "likeface/Shop/getCategoryList";
    /*登录*/
    public static final String login = "user/Public/login";
    /*注册*/
    public static final String register = "user/Public/register";
    /*发送验证码*/
    public static final String sendCode = "likeface/Public/userSMSSend";
    /*验证验证码*/
    public static final String checkCode = "likeface/Public/userCheckCode";
    /*获取webview文本*/
    public static final String getWebDetail = "likeface/Public/document";
    /*忘记密码*/
    public static final String findPwd = "user/Public/setPasswordVerify";
    /*修改密码 重置*/
    public static final String resetPwd = "user/Profile/changePassword";
    /*用户退出*/
    public static final String exitLogin = "user/Profile/logout";
    /*修改用户基本信息*/
    public static final String modifyUserInfo = "user/Profile/userInfo";
    /*版本更新*/
    public static final String updateVersion = "likeface/Version/index";
    /*获取调查问卷题题目*/
    public static final String getQuestionnaireList = "likeface/Profile/getQuestionnaireList";
    /*提交调查问卷答卷*/
    public static final String submitQuestionnaireAnswer = "likeface/Profile/submitQuestionnaire";
    /*用户反馈*/
    public static final String feedback = "likeface/Profile/feedback";
    /*获取用户个人信息*/
    public static final String getUserInfo = "user/Profile/getInfo";
    /*修改手机号*/
    public static final String modifyPhone = "user/Profile/updateMobile";
    /*获取商品评价列表*/
    public static final String getGoodsEvalList = "likeface/Shopping_mall/getShopGoodsComment";
    /*获取店铺动态列表*/
    public static final String getShopEventList = "likeface/Shopping_mall/getShopSharing";
    /*获取店铺详情*/
    public static final String getShopDetail = "likeface/Shopping_mall/getShopInfo";
    /*获取商品详情*/
    public static final String getGoodsDetail = "likeface/Shopping_mall/getGoodsInfo";
    /*商品评价*/
    public static final String getGetGoodsEvalList = "likeface/Shopping_mall/getGoodsComment";
    /*收藏商品 或 取消*/
    public static final String updateGoodsCollState = "likeface/Shopping_cart/collectGoods";
    /*收藏店铺 或 取消*/
    public static final String updateShopCollState = "likeface/Shopping_cart/collectShop";
    /*收藏的商品列表*/
    public static final String getCollectGoodsList = "likeface/Shopping_cart/getcollectGoods";
    /*收藏的店铺列表*/
    public static final String getCollectShopList = "likeface/Shopping_cart/getcollectShop";
    /*清空收藏的店铺*/
    public static final String clearCollectShops = "likeface/Shopping_cart/clearcollectShop";
    /*删除单个收藏店铺*/
    public static final String deleteCollectShop = "likeface/Shopping_cart/deletecollectShop";
    /*清空收藏的商品*/
    public static final String clearCollectGoods = "likeface/Shopping_cart/clearcollectGoods";
    /*删除收藏的单个商品*/
    public static final String deleteCollectGoods = "likeface/Shopping_cart/deletecollectGoods";
    /*点赞或取消点赞 店铺动态*/
    public static final String updateShopEventCollState = "likeface/Shop_manage_ext/sharingfollow";
    /*获取用户浏览记录*/
    public static final String getBorrowingHistory = "likeface/Info/look";
    /*清空用户浏览记录*/
    public static final String clearBorrowingHistory = "likeface/Info/clearLook";
    /*删除用户浏览记录*/
    public static final String deleteBorrowingHistory = "likeface/Info/deleteLook";


    /*获取商品列表*/
    public static final String getGoodsList = "likeface/Goods/getGoodsList";
    /*获取店铺列表*/
    public static final String getShopList = "likeface/Goods/getShopList";

    /*发现模块*/
    /*发布发现的时候获取类别*/
    public static final String getPublishFindType = "likeface/Shop_manage_ext/getSharingFirst";
    /*发现类别*/
    public static final String getFindType = "likeface/public/getSharingClassification";
    /*发现类别*/
    public static final String getFindList = "likeface/sharing/getSharingListExt";
    /*发现详情*/
    public static final String getFindDetail = "likeface/Shop_manage_ext/sharingInfo";
    /*发现发表评论*/
    public static final String publishFindPJ = "likeface/Shop_manage_ext/sharingComment";
    /*发现 点赞 或 取消 点赞*/
    public static final String updateFindZanState = "likeface/Shop_manage_ext/sharingLike";
    /*评论发表评论*/
    public static final String publishPJPJ = "likeface/Shop_manage_ext/commentComment";
    /*评论点赞 取消*/
    public static final String updatePjZanState = "likeface/Shop_manage_ext/commentLike";
    /*关注 取消 关注 发现发布者*/
    public static final String updateFollowState = "likeface/Shop_manage_ext/sharingfollow";
    /*分享一级评论 以及 子评论*/
    public static final String getPJPJList = "likeface/Shop_manage_ext/sharingCommentChildExt";
    /*发布 发现*/
    public static final String publishFind = "likeface/Shop_manage_ext/sharing";
    /*我的分享*/
    public static final String getMyShareList = "likeface/Profile/sharingList";
    /*删除分享*/
    public static final String deleteShare = "likeface/Shop_manage_ext/deleteSharing";


    /*优惠券*/
    /*获取我的优惠券列表*/
    public static final String getMyCouponList = "likeface/Profile/receiveCouponList";

    /*店铺优惠券*/
    public static final String getShopCoupon = "likeface/Shopping_mall/getShopCoupon";
    /*领取店铺优惠券*/
    public static final String pickCoupon = "likeface/Profile/receiveCoupon";
    /*获取优惠券 购物*/
    public static final String getCouponForBuy = "likeface/Profile/receiveCouponListPay";




    /*浏览记录*/
    /*获取浏览记录*/
    public static final String getBrowseHistory = "likeface/Info/look";
    /*删除用户浏览记录*/
    public static final String deleteBrowseHistory = "likeface/Info/deleteLook";
    /*清空用户浏览记录*/
    public static final String clearBrowseHistory = "likeface/Info/clearLook";


    /*添加防护功能*/
    public static final String userOrderProtection = "likeface/Shopping_order/userOrderprotection";
    /*设置紧急联系人*/
    public static final String setUrgentContact = "likeface/Profile/setContact";
    /*获取紧急联系人*/
    public static final String getUrgentContact = "likeface/Profile/getContact";


    /*购物车相关*/
    /*加入购物车*/
    public static final String addToShopCar = "likeface/Shopping_cart/addCart";
    /*提交订单*/
    public static final String submitOrder2 = "likeface/Shopping_cart/addUserOrder";
    public static final String submitOrder = "likeface/Shopping_cart/addUserOrderExt";
    /*购物车修改商品数量*/
    public static final String modifyShopCartNum = "likeface/Shopping_cart/updateUserCart";
    /*购物车删除商品*/
    public static final String deleteShopCart = "likeface/Shopping_cart/deleteUserCart";
    /*获取购物车*/
    public static final String getShopCartData = "likeface/Shopping_cart/getUserCart";
    /*根据店铺id 购买数量 获取运费*/
    public static final String getOrderFreight = "likeface/Shop_Manage/getRatePrice";
    /*购物车商品数量*/
    public static final String getCartGoodsNum = "likeface/Shopping_cart/getUserCartGoodsCount";




    /*订单相关*/
    /*订单支付*/
    public static final String payOrder = "likeface/Shopping_order/payUserOrder";
    /*订单列表详情*/
    public static final String getOrderDetail = "likeface/Shopping_order/getUserOrderInfo";
    /*订单列表*/
    public static final String getOrderList = "likeface/Shopping_order/getUserOrder";
    /*取消订单*/
    public static final String cancelOrder = "likeface/Shopping_order/cancelUserOrder";
    /*删除订单*/
    public static final String deleteOrder = "likeface/Shopping_order/deleteUserOrder";
    /*订单确认收货*/
    public static final String confirmOrder = "likeface/Shopping_order/confirmUserOrder";
    /*订单评价*/
    public static final String evalOrder = "likeface/Shopping_order/commentUserOrder";
    /*提醒发货*/
    public static final String remindSeller = "likeface/Shopping_order/Shippingreminder";
    /*我的评价*/
    public static final String myEvalList = "likeface/Profile/commentList";
    /*评价详情*/
    public static final String getMyEvalDetail = "likeface/Profile/commentInfo";
    /*删除评价*/
    public static final String deleteMyEval = "likeface/Profile/deletecomment";

    /*退货原因*/
    public static final String returnReason = "likeface/Public/getRetrunOrderReason";
    /*申请退货*/
    public static final String applyReturn = "likeface/Order_return/Apply";
    /*可以申请退货的列表*/
    public static final String getReturnAbleOrderList = "likeface/Order_return/getOrder";
    /*可以申请退货的订单项详情*/
    public static final String getReturnAbleOrderDetail = "likeface/Order_return/getOrderInfo";
    /*退货记录详情*/
    public static final String getReturnHisDetail = "likeface/Order_return/getReturnInfo";
    /*退货记录列表*/
    public static final String getReturnHisList = "likeface/Order_return/getReturnList";
    /*删除记录*/
    public static final String deleteReturnHis = "likeface/Order_return/deleteReturn";


    /*收货地址*/
    /*获取收货地址*/
    public static final String getAddrList = "likeface/Shopping_cart/getUserAddress";
    /*添加收货地址*/
    public static final String addAddr = "likeface/Shopping_cart/addUserAddress";
    /*修改收货地址*/
    public static final String modifyAddr = "likeface/Shopping_cart/updateUserAddress";
    /*删除收货地址*/
    public static final String deleteAddr = "likeface/Shopping_cart/deleteUserAddress";
    /*设置默认收货地址*/
    public static final String setDefaultAddr = "likeface/Shopping_cart/setUserAddressDefault";



    /*消息*/
    /*获取消息列表*/
    public static final String getMessageList = "likeface/message/getMessageList";
    /*读取消息详情*/
    public static final String getMessageDetail = "likeface/message/getMessageInfo";
    /*消息未读数目*/
    public static final String getMsgUnReadNum = "likeface/Message/getMessgeNoLook";


    /*新闻资讯列表*/
    public static final String getNewsList = "likeface/Goods/getNewsList";
    /*新闻详情*/
    public static final String getNewsDetail = "likeface/Goods/getNewsInfo";


    /*融云*/
    /*获取融云token*/
    public static final String getRongUserInfo = "likeface/Public/getRYtoken";


    /*分享*/
    public static ShareContentBean shareContentBean;
    /*获取分享的内容*/
    public static final String getShareContent = "likeface/Public/sharing";


    /*清空发现未读数量*/
    public static final String clearFindMsgNum = "likeface/Profile/setdatetime";
    /*获取发现未读数量*/
    public static final String getFindMsgNum = "likeface/Profile/getUserSharingCount";


    /*本地存储键值*/
    public static final String UserID = "UserID";   // 用户id
    public static final String Token = "Token";   // 用户 token
    /*融云token*/
    public static final String RONG_TOKEN = "RONG_TOKEN";
    public static final String BOND = "SYS_BOND";/*保证金*/
    public static final String WITHDRAW_RATE = "SYS_RATE";/*提现手续费*/
    public static final String CLIENT_PHONE = "SYS_CLIENT_PHONE";/*客服手机号*/
    public static final String PROTECT_TXT = "PROTECT_TXT";/*防护提示文本*/
    /*热门搜索*/
    public static final String KEY_HOT_SEARCH = "KEY_HOT_SEARCH";
    /*城市列表*/
    public static final String KEY_CITY_LIST = "KEY_CITY_LIST";
    public static final String KEY_HOT_CITY_LIST = "KEY_HOT_CITY_LIST";
    /*搜索地理范围*/
    public static final String KEY_DISTANCE_RANGE = "KEY_DISTANCE_RANGE";

    /*用户信息 KEY*/
    /*性别*/
    public static final String KEY_SEX = "KEY_SEX";
    /*生日*/
    public static final String KEY_BIRTHDAY = "KEY_BIRTHDAY";
    /*用户名*/
    public static final String KEY_PHONE = "KEY_PHONE";
    /*用户密码*/
    public static final String KEY_PWD = "KEY_PWD";
    /*用户昵称*/
    public static final String KEY_NICKNAME = "KEY_NICKNAME";
    /*用户头像*/
    public static final String KEY_USERHEAD = "KEY_USERHEAD";
    /*个性签名*/
    public static final String KEY_USERSIGN = "KEY_USERSIGN";
    /*是否展示过引导页*/
    public static final String KEY_HAS_SHOW_GUIDE = "KEY_HAS_SHOW_GUIDE";


    /*发布发现 讯草稿 功能*/
    /*文字*/
    public static final String KEY_FIND_TEXT = "KEY_FIND_TEXT";
    /*图片*/
    public static final String KEY_FIND_PIC = "KEY_FIND_PIC";

    public static final String UserISJPush = "UserISJPush";   //  是否接受推送  -1不接收 1 接受

    /*订单防护提示 false 没有选择防护时弹窗提示  选择防护时 不提示    true  永不提示*/
    public static final String KEY_IGNORE_ORDER_PROTECT = "KEY_IGNORE_ORDER_PROTECT";


    public static final String Data_FileDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "HuaDou";
    //    public static final String Data_FileDirPath = Environment.getDataDirectory().getParentFile().getAbsolutePath() + File.separator + "HuaDou";
    public static final String Data_FilePath = Data_FileDirPath;
    public static final String Data_IDCardPath = Data_FilePath + File.separator + "idcard1.jpg";
    public static final String Data_Cache = Data_FileDirPath + File.separator + "ImgCache";
    public static final String Data_Download = Data_FileDirPath + File.separator + "ImgDownLoad";







}
