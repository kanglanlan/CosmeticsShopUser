package com.meida.cosmeticsshopuser.Bean.eventbus;

/**
 * Created by Administrator on 2019/1/7 0007.
 */

public class PaySuccess {

    boolean b;/*支付成功*/

    public PaySuccess(boolean b) {
        this.b = b;
    }

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }


    boolean evalSuccess;

    public boolean isEvalSuccess() {
        return evalSuccess;
    }

    public void setEvalSuccess(boolean evalSuccess) {
        this.evalSuccess = evalSuccess;
    }

    public PaySuccess() {
    }
}
