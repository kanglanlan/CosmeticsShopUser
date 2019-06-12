package com.meida.cosmeticsshopuser.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/12/25 0025.
 */

public class HomeBean extends BaseBean{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{

        private int total;
        private int per_page;
        private int current_page;
        private int last_page;

        private List<ShopItemBean> data;
        private GoodsData goodsdata;
        private NewsData news;
        private List<BannerItemBean> sowing;

        public GoodsData getGoodsdata() {
            return goodsdata;
        }

        public void setGoodsdata(GoodsData goodsdata) {
            this.goodsdata = goodsdata;
        }

        public NewsData getNews() {
            return news;
        }

        public void setNews(NewsData news) {
            this.news = news;
        }

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

        public List<ShopItemBean> getData() {
            return data;
        }

        public void setData(List<ShopItemBean> data) {
            this.data = data;
        }


        public List<BannerItemBean> getSowing() {
            return sowing;
        }

        public void setSowing(List<BannerItemBean> sowing) {
            this.sowing = sowing;
        }
    }

    public class GoodsData{
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

    public class NewsData{
        private int total;
        private int per_page;
        private int current_page;
        private int last_page;
        private List<NewsItemBean> data;

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

        public List<NewsItemBean> getData() {
            return data;
        }

        public void setData(List<NewsItemBean> data) {
            this.data = data;
        }
    }


}
