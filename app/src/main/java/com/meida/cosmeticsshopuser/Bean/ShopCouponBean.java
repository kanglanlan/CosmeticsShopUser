package com.meida.cosmeticsshopuser.Bean;

import java.util.List;

/**
 * Created by Administrator on 2019/1/8 0008.
 */

public class ShopCouponBean extends BaseBean{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean extends BaseListBean{
        private List<ItemBean> data;

        public List<ItemBean> getData() {
            return data;
        }

        public void setData(List<ItemBean> data) {
            this.data = data;
        }
    }

    public class ItemBean{
        private String id;
        private String shopid;
        private String favourable;
        private String begintime;
        private String endtime;
        private String title;
        private String amount;
        private String userstauus;
        private String usercid;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
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

        public String getFavourable() {
            return favourable;
        }

        public void setFavourable(String favourable) {
            this.favourable = favourable;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUserstauus() {
            return userstauus;
        }

        public void setUserstauus(String userstauus) {
            this.userstauus = userstauus;
        }

        public String getUsercid() {
            return usercid;
        }

        public void setUsercid(String usercid) {
            this.usercid = usercid;
        }
    }


}
