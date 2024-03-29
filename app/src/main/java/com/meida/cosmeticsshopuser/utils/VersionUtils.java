package com.meida.cosmeticsshopuser.utils;

import android.text.TextUtils;

/**
 * @author AcmenXD
 * @version v1.0
 * @github https://github.com/AcmenXD
 * @date 2016/12/15 12:03
 * @detail 版本号工具类
 */
public class VersionUtils {
    /**
     * 版本号对比
     *
     * @param locaVersion 本地版本吧
     * @param SVersion    服务器版本
     * @return error : 返回-2 既传入版本号格式有误
     * locaVersion > SVersion  return -1
     * locaVersion = SVersion  return 0
     * locaVersion < SVersion  return 1
     */
    public static int compareVersions(String locaVersion, String SVersion) {
        //返回结果: -2 ,-1 ,0 ,1
        if (TextUtils.isEmpty(locaVersion) || TextUtils.isEmpty(SVersion)) {
            return -2;
        }
        int result = 0;
        String matchStr = "[0-9]+(\\.[0-9]+)*";
        locaVersion = locaVersion.trim();
        SVersion = SVersion.trim();
        //非版本号格式,返回error
        if (!locaVersion.matches(matchStr) || !SVersion.matches(matchStr)) {
            return -2;
        }
        String[] oldVs = locaVersion.split("\\.");
        String[] newVs = SVersion.split("\\.");
        int oldLen = oldVs.length;
        int newLen = newVs.length;
        int len = oldLen > newLen ? oldLen : newLen;
        for (int i = 0; i < len; i++) {
            if (i < oldLen && i < newLen) {
                int o = Integer.parseInt(oldVs[i]);
                int n = Integer.parseInt(newVs[i]);
                if (o > n) {
                    result = -1;
                    break;
                } else if (o < n) {
                    result = 1;
                    break;
                }
            } else {
                if (oldLen > newLen) {
                    for (int j = i; j < oldLen; j++) {
                        if (Integer.parseInt(oldVs[j]) > 0) {
                            result = -1;
                        }
                    }
                    break;
                } else if (oldLen < newLen) {
                    for (int j = i; j < newLen; j++) {
                        if (Integer.parseInt(newVs[j]) > 0) {
                            result = 1;
                        }
                    }
                    break;
                }
            }
        }
        return result;
    }
}