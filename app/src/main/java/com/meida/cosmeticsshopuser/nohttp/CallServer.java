/*
 * Copyright © YOLANDA. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.meida.cosmeticsshopuser.nohttp;

import android.content.Context;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.download.DownloadQueue;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;

/**
 * Created in Oct 23, 2015 1:00:56 PM
 *
 * @author YOLANDA
 */
public class CallServer  {

    private static CallServer callServer;

    /**
     * 请求队列
     */
    private RequestQueue requestQueue;

    /**
     * 下载队列
     */
    private static DownloadQueue downloadQueue;

    public CallServer() {
        // 创建请求队列, 默认并发3个请求, 传入你想要的数字可以改变默认并发数 ;
        requestQueue = NoHttp.newRequestQueue();
    }

    /**
     * 请求队列
     */
    public synchronized static CallServer getRequestInstance() {
        if (callServer == null)
            callServer = new CallServer();
        return callServer;
    }

    /**
     * 下载队列
     */
    public static DownloadQueue getDownloadInstance() {
        if (downloadQueue == null)
            // 创建下载队列, 默认并发3个下载, 传入你想要的数字可以改变默认并发数 ;
            downloadQueue = NoHttp.newDownloadQueue();
        return downloadQueue;
    }

    /**
     * 添加一个请求到请求队列
     *
     * @param context   context用来实例化dialog
     * @param what      用来标志请求, 当多个请求使用同一个{@link HttpListener}时, 在回调方法中会返回这个what
     * @param request   请求对象
     * @param callback  结果回调对象
     * @param canCancel 是否允许用户取消请求
     * @param isLoading 是否显示dialog
     */
    public <T> void add(Context context, int what, Request<T> request, HttpListener<T> callback, boolean canCancel, boolean isLoading) {
        requestQueue.add(what, request, new HttpResponseListener<T>(context, request, callback, canCancel, isLoading));

    }
    /**
     * 添加一个请求到请求队列，允许取消，标志请求为0
     *
     * @param context   context用来实例化dialog
     * @param request   请求对象
     * @param callback  结果回调对象
     * @param isLoading 是否显示dialog
     */
    public <T> void add(Context context, Request<T> request, HttpListener<T> callback, boolean isLoading) {
        requestQueue.add(0, request, new HttpResponseListener<>(context, request, callback, false, isLoading));
    }
    /**
     * 取消这个sign标记的所有请求
     */
    public void cancelBySign(Object sign) {
        requestQueue.cancelBySign(sign);

    }

    /**
     * 取消队列中所有请求
     */
    public void cancelAll() {
        requestQueue.cancelAll();
    }

    /**
     * 退出app时停止所有请求
     */
    public void stopAll() {
        requestQueue.stop();
    }

}
