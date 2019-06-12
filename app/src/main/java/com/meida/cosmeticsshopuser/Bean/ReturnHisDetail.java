package com.meida.cosmeticsshopuser.Bean;

/**
 * Created by Administrator on 2019/1/30 0030.
 */

public class ReturnHisDetail extends BaseBean{

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
        private String orderid;
        private String goodsid;
        private String itemid;
        private String title;
        private String spectitle;
        private String price;
        private String num;
        private String imgs;
        private String status;/* 1待审核 2确定 3拒绝*/
        private String reasonmm;
        private String reason;
        private String shoptitle;
        private String total;
        private String orderno;
        private String createtime;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
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

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getGoodsid() {
            return goodsid;
        }

        public void setGoodsid(String goodsid) {
            this.goodsid = goodsid;
        }

        public String getItemid() {
            return itemid;
        }

        public void setItemid(String itemid) {
            this.itemid = itemid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSpectitle() {
            return spectitle;
        }

        public void setSpectitle(String spectitle) {
            this.spectitle = spectitle;
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

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getReasonmm() {
            return reasonmm;
        }

        public void setReasonmm(String reasonmm) {
            this.reasonmm = reasonmm;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }
    }

}
