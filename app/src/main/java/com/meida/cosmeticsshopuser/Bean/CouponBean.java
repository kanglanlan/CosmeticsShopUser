package com.meida.cosmeticsshopuser.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/1/7 0007.
 */

public class CouponBean extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean{
        private List<CouponItemBean> data;
        private TJ tj;

        public List<CouponItemBean> getData() {
            return data;
        }

        public void setData(List<CouponItemBean> data) {
            this.data = data;
        }

        public TJ getTj() {
            return tj;
        }

        public void setTj(TJ tj) {
            this.tj = tj;
        }
    }


    public class CouponItemBean implements Serializable{

        private String id;
        private String shopid;
        private String favourable;
        private String amount;
        private String stock;
        private String num;
        private String limitnumber;
        private String begintime;
        private String endtime;
        private String goods;
        private String status;
        private String show;
        private String title;
        private String uid;
        private String ccouponid;
        private String createtime;


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

        public String getFavourable() {
            return favourable;
        }

        public void setFavourable(String favourable) {
            this.favourable = favourable;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getLimitnumber() {
            return limitnumber;
        }

        public void setLimitnumber(String limitnumber) {
            this.limitnumber = limitnumber;
        }

        public String getBegintime() {
            return begintime;
        }

        public void setBegintime(String begintime) {
            this.begintime = begintime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getGoods() {
            return goods;
        }

        public void setGoods(String goods) {
            this.goods = goods;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getShow() {
            return show;
        }

        public void setShow(String show) {
            this.show = show;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getCcouponid() {
            return ccouponid;
        }

        public void setCcouponid(String ccouponid) {
            this.ccouponid = ccouponid;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }
    }

    public class TJ{
        private int ksy;
        private int ysy;
        private int ygq;

        public int getKsy() {
            return ksy;
        }

        public void setKsy(int ksy) {
            this.ksy = ksy;
        }

        public int getYsy() {
            return ysy;
        }

        public void setYsy(int ysy) {
            this.ysy = ysy;
        }

        public int getYgq() {
            return ygq;
        }

        public void setYgq(int ygq) {
            this.ygq = ygq;
        }
    }


}
