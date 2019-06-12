package com.meida.cosmeticsshopuser.utils;

import android.app.Activity;

import com.meida.cosmeticsshopuser.Bean.ShareEntity;
import com.meida.cosmeticsshopuser.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import java.util.Map;

/**
 * 分享工具类
 */
public class ShareUtil {

    private static ShareUtil shareUtil = null;

    public static ShareUtil getInstance() {
        if (shareUtil == null) {
            synchronized (ShareUtil.class) {
                if (shareUtil == null) {
                    shareUtil = new ShareUtil();
                }
            }
        }
        return shareUtil;
    }


    /*public void share(Activity activity, String url, UMShareListener umShareListener, String description) {
        UMWeb web = new UMWeb(url);
        web.setTitle(activity.getResources().getString(R.string.app_name));//标题
        web.setDescription(description);//描述
        UMImage image = new UMImage(activity, R.mipmap.logo);
        web.setThumb(image);

        ShareAction shareAction = new ShareAction(activity).withMedia(web)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.QQ
                )
                .setCallback(umShareListener);
        ShareBoardConfig config = new ShareBoardConfig();
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE);
        shareAction.open(config);

    }
*/
    public void share(Activity activity, String url,String title, String description,String imgUrl) {
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        web.setDescription(description);//描述
        UMImage image = new UMImage(activity, imgUrl);
        web.setThumb(image);

        ShareAction shareAction = new ShareAction(activity).withMedia(web)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.QQ
                );
        ShareBoardConfig config = new ShareBoardConfig();
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE);
        shareAction.open(config);

    }

    public void Login(final Activity activity, final SHARE_MEDIA media, final ShareCallBack callBack) {
        UMShareAPI.get(activity).doOauthVerify(activity, media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                if (map == null)
                    return;
                getPlatformInfo(activity, media, callBack);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                callBack.onError("Verify error");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
            }
        });

    }

    public void getPlatformInfo(Activity activity, SHARE_MEDIA media, final ShareCallBack callBack) {
        UMShareAPI.get(activity).getPlatformInfo(activity, media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                // 授权信息
                String uid = map.get("uid");
                String openid = map.get("openid");
                String unionid = map.get("unionid");
                String accessToken = map.get("accessToken");
                String expiration = map.get("expiration");

                // 用户信息
                String name = map.get("name");
                String iconurl = map.get("iconurl");
                String gender = map.get("gender");

                //第三方平台SDK源数据
                String originalResponse = map.get("originalResponse");

                ShareEntity entity = new ShareEntity();
                entity.setIcon(iconurl);
                entity.setNickName(name);
                entity.setThirdUid(uid);

                if (share_media == SHARE_MEDIA.QQ) {
                    entity.setType("0");
                } else if (share_media == SHARE_MEDIA.WEIXIN) {
                    entity.setType("1");
                }
                callBack.onSuccess(entity);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                callBack.onError("Verify error");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
            }
        });

    }

    public interface ShareCallBack {
        void onSuccess(ShareEntity entity);

        void onError(String error);
    }


}
