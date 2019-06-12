package com.meida.cosmeticsshopuser.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/12/26 0026.
 */

public class ShopGoodsBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean{

        private int total;
        private int per_page;
        private int current_page;
        private int last_page;

        private List<ShopBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public List<ShopBean> getData() {
            return data;
        }

        public void setData(List<ShopBean> data) {
            this.data = data;
        }
    }


    public class ShopBean{

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

        private Goods goodslist;

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

        public Goods getGoodslist() {
            return goodslist;
        }

        public void setGoodslist(Goods goodslist) {
            this.goodslist = goodslist;
        }
    }


    public class Goods{

        private int total;
        private int per_page;
        private int current_page;
        private int last_page;

        private List<GoodsItemBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public List<GoodsItemBean> getData() {
            return data;
        }

        public void setData(List<GoodsItemBean> data) {
            this.data = data;
        }
    }



}
