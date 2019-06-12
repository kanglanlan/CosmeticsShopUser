package com.meida.cosmeticsshopuser.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/12/20 0020.
 */

public class BaseBean implements Serializable{

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
