package com.meida.cosmeticsshopuser.Bean;

/**
 * Created by Administrator on 2019/1/30 0030.
 */

public class OrderReturnAbleDetail extends BaseBean{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean{


        private String id;
        private String shopid;
        private String goodsid;
        private String price;
        private String num;
        private String title;
        private String imgs;
        private String kfid;
        private String shoptitle;

        public String getGoodsid() {
            return goodsid;
        }

        public void setGoodsid(String goodsid) {
            this.goodsid = goodsid;
        }

        public String getShoptitle() {
            return shoptitle;
        }

        public void setShoptitle(String shoptitle) {
            this.shoptitle = shoptitle;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
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

        public String getKfid() {
            return kfid;
        }

        public void setKfid(String kfid) {
            this.kfid = kfid;
        }
    }


}
