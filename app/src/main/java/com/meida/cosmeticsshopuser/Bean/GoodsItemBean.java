package com.meida.cosmeticsshopuser.Bean;

/**
 * Created by Administrator on 2018/12/25 0025.
 */

public class GoodsItemBean {

    private String id;
    private String title;
    private String imgs;
    private int salesvolume;
    private int salesvolumea;
    private String favorablerate;
    private String price;
    private String coupon;

    public int getSalesvolume() {
        return salesvolume;
    }

    public void setSalesvolume(int salesvolume) {
        this.salesvolume = salesvolume;
    }

    public int getSalesvolumea() {
        return salesvolumea;
    }

    public void setSalesvolumea(int salesvolumea) {
        this.salesvolumea = salesvolumea;
    }

    public String getFavorablerate() {
        return favorablerate;
    }

    public void setFavorablerate(String favorablerate) {
        this.favorablerate = favorablerate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }
}
