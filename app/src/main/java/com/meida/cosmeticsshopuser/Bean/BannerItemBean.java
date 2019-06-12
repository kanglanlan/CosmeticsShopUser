package com.meida.cosmeticsshopuser.Bean;

/**
 * Created by Administrator on 2018/12/25 0025.
 */

public class BannerItemBean {

    private String id;
    private String orderindex;
    private String img;
    private String flag;
    private String pid;
    private String link;
    private String status;
    private String catecory;

    public String getCatecory() {
        return catecory;
    }

    public void setCatecory(String catecory) {
        this.catecory = catecory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderindex() {
        return orderindex;
    }

    public void setOrderindex(String orderindex) {
        this.orderindex = orderindex;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
