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

import com.yolanda.nohttp.rest.Response;

/**
 * 接受回调结果
 * </br>
 * Created in Nov 4, 2015 12:54:50 PM
 *
 * @author YOLANDA
 */
public interface HttpListener<T> {

    /**
     * 请求成功
     */
    void onSucceed(int what, Response<T> response);

    /**
     * 请求失败
     */
    void onFailed(int what, Response<T> response);
}
