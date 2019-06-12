package com.meida.cosmeticsshopuser.Bean;

/**
 * Created by Administrator on 2018/12/25 0025.
 */

public class ShopItemBean {

    private String id;
    private String avatar;
    private String title;
    private String goods;
    private long salesvolume;
    private long salesvolumea;
    private int distribution1;
    private int distribution2;
    private int distribution3;
    private long juli;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public long getSalesvolume() {
        return salesvolume;
    }

    public void setSalesvolume(long salesvolume) {
        this.salesvolume = salesvolume;
    }

    public long getSalesvolumea() {
        return salesvolumea;
    }

    public void setSalesvolumea(long salesvolumea) {
        this.salesvolumea = salesvolumea;
    }

    public void setSalesvolumea(int salesvolumea) {
        this.salesvolumea = salesvolumea;
    }

    public int getDistribution1() {
        return distribution1;
    }

    public void setDistribution1(int distribution1) {
        this.distribution1 = distribution1;
    }

    public int getDistribution2() {
        return distribution2;
    }

    public void setDistribution2(int distribution2) {
        this.distribution2 = distribution2;
    }

    public int getDistribution3() {
        return distribution3;
    }

    public void setDistribution3(int distribution3) {
        this.distribution3 = distribution3;
    }


    public long getJuli() {
        return juli;
    }

    public void setJuli(long juli) {
        this.juli = juli;
    }
}
