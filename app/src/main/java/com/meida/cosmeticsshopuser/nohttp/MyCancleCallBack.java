package com.meida.cosmeticsshopuser.nohttp;

import com.yolanda.nohttp.able.Cancelable;

/**
 * Created by Administrator on 2019/1/15 0015.
 */

public class MyCancleCallBack implements Cancelable {
    private  boolean isCancle=false;
    @Override
    public void cancel() {

    }

    @Override
    public boolean isCanceled() {
        return isCancle;
    }
}
