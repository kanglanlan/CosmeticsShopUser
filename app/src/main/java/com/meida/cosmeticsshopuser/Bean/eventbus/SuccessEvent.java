package com.meida.cosmeticsshopuser.Bean.eventbus;

/**
 * Created by Administrator on 2019/1/4 0004.
 */

public class SuccessEvent {

    private boolean b;

    public SuccessEvent(boolean b) {
        this.b = b;
    }

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }
}
