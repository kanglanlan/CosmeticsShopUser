package com.meida.cosmeticsshopuser.Bean.eventbus;

import com.meida.cosmeticsshopuser.Bean.AddrBean;

/**
 * Created by Administrator on 2019/1/14 0014.
 */

public class EditAddrBean {

    /*提交订单 时  用于 删除 地址 和 编辑地址 时 使用*/

    private boolean isDelete;

    private String id = "";
    private AddrBean.AddrItemBean itemBean;

    public AddrBean.AddrItemBean getItemBean() {
        return itemBean;
    }

    public void setItemBean(AddrBean.AddrItemBean itemBean) {
        this.itemBean = itemBean;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EditAddrBean(boolean isDelete, String id, AddrBean.AddrItemBean itemBean) {
        this.isDelete = isDelete;
        this.id = id;
        this.itemBean = itemBean;
    }
}
