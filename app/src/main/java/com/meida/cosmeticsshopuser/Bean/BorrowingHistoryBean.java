package com.meida.cosmeticsshopuser.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/12/29 0029.
 */

public class BorrowingHistoryBean extends BaseBean{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean{

        private GoodsData goodsdata;
        private ShopData shopdata;
        private NewsData newsdata;
        private Dynamicdata dynamicdata;

        public GoodsData getGoodsdata() {
            return goodsdata;
        }

        public void setGoodsdata(GoodsData goodsdata) {
            this.goodsdata = goodsdata;
        }

        public ShopData getShopdata() {
            return shopdata;
        }

        public void setShopdata(ShopData shopdata) {
            this.shopdata = shopdata;
        }

        public NewsData getNewsdata() {
            return newsdata;
        }

        public void setNewsdata(NewsData newsdata) {
            this.newsdata = newsdata;
        }

        public Dynamicdata getDynamicdata() {
            return dynamicdata;
        }

        public void setDynamicdata(Dynamicdata dynamicdata) {
            this.dynamicdata = dynamicdata;
        }
    }

    public class GoodsData extends BaseListBean{
        private List<CollectGoodsBean.CollectGoodsItemBean> data;


        public List<CollectGoodsBean.CollectGoodsItemBean> getData() {
            return data;
        }

        public void setData(List<CollectGoodsBean.CollectGoodsItemBean> data) {
            this.data = data;
        }
    }

    public class ShopData extends BaseListBean{
        private List<CollectShopBean.CollectShop> data;


        public List<CollectShopBean.CollectShop> getData() {
            return data;
        }

        public void setData(List<CollectShopBean.CollectShop> data) {
            this.data = data;
        }
    }

    public class NewsData extends BaseListBean{
        private List<NewsItemBean> data;

        public List<NewsItemBean> getData() {
            return data;
        }

        public void setData(List<NewsItemBean> data) {
            this.data = data;
        }
    }

    public class Dynamicdata extends BaseListBean{
        private List<FindListBean.FindItemBean> data;

        public List<FindListBean.FindItemBean> getData() {
            return data;
        }

        public void setData(List<FindListBean.FindItemBean> data) {
            this.data = data;
        }
    }







}
