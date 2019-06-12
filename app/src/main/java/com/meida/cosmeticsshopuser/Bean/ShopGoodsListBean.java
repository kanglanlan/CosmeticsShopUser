package com.meida.cosmeticsshopuser.Bean;

import java.util.List;

/**
 * Created by Administrator on 2019/1/8 0008.
 */

public class ShopGoodsListBean extends BaseBean{

    private Goods data;

    public Goods getData() {
        return data;
    }

    public void setData(Goods data) {
        this.data = data;
    }

    public class DataBean{
        private String id;
        private String title;
        private Goods goodslist;

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

        public Goods getGoodslist() {
            return goodslist;
        }

        public void setGoodslist(Goods goodslist) {
            this.goodslist = goodslist;
        }
    }

    public class Goods extends BaseListBean{

        private List<GoodsItemBean> data;

        public List<GoodsItemBean> getData() {
            return data;
        }

        public void setData(List<GoodsItemBean> data) {
            this.data = data;
        }
    }

}
