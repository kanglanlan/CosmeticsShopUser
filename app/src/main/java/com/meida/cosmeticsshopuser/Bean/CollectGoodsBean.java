package com.meida.cosmeticsshopuser.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/12/20 0020.
 */

public class CollectGoodsBean extends BaseBean{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean extends BaseListBean{
        private List<CollectGoodsItemBean> data;

        public List<CollectGoodsItemBean> getData() {
            return data;
        }

        public void setData(List<CollectGoodsItemBean> data) {
            this.data = data;
        }
    }


    public static class CollectGoodsItemBean{
        private String id;
        private String colledtid;
        private String shopid;
        private String title;
        private String img;
        private int salesvolume;
        private int salesvolumea;
        private String imgs;
        private String content;
        private String favorablerate;
        private String price;
        private String oldprice;

        private String lookid;

        public String getLookid() {
            return lookid;
        }

        public void setLookid(String lookid) {
            this.lookid = lookid;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getColledtid() {
            return colledtid;
        }

        public void setColledtid(String colledtid) {
            this.colledtid = colledtid;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

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

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getOldprice() {
            return oldprice;
        }

        public void setOldprice(String oldprice) {
            this.oldprice = oldprice;
        }
    }







}
