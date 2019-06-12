package com.meida.cosmeticsshopuser.Bean;

import com.meida.cosmeticsshopuser.utils.FormatterUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/12/21 0021.
 */

public class ShopCartBean extends BaseBean{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean{
        private List<Shop> goods;
        private Recommend recommendGoods;

        public List<Shop> getGoods() {
            return goods;
        }

        public void setGoods(List<Shop> goods) {
            this.goods = goods;
        }


        public Recommend getRecommendGoods() {
            return recommendGoods;
        }

        public void setRecommendGoods(Recommend recommendGoods) {
            this.recommendGoods = recommendGoods;
        }
    }


    public static class Shop implements Serializable{
        private String id;
        private String title;
        private String total;
        private List<CartGoods> goodslist;
        private boolean isCheck;

        /*店铺下选中的商品价格合计*/
        private double shopSumPrice = 0;

        public double getShopSumPrice() {
            return shopSumPrice;
        }

        public void setShopSumPrice(double shopSumPrice) {
            this.shopSumPrice = shopSumPrice;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

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

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public List<CartGoods> getGoodslist() {
            return goodslist;
        }

        public void setGoodslist(List<CartGoods> goodslist) {
            this.goodslist = goodslist;
        }
    }

    public static class CartGoods implements Serializable{
        private String id;
        private String uid;
        private String goodsid;
        private String goodsspcid;
        private int num;
        private String imgs;
        private String title;
        private String isfreeshipping;/*是否包邮*/
        private String isfreesend;/*	包送 1、是 2 否*/
        private String isfreedelivery;/*包配送 1、是 2 否*/
        private String price;
        private int stock;
        private String tag1;
        private String tag2;
        private String tag3;
        private String categoryid;
        private String spectitle;
        private boolean isCheck;


        public String getIsfreesend() {
            return isfreesend;
        }

        public void setIsfreesend(String isfreesend) {
            this.isfreesend = isfreesend;
        }

        public String getIsfreedelivery() {
            return isfreedelivery;
        }

        public void setIsfreedelivery(String isfreedelivery) {
            this.isfreedelivery = isfreedelivery;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getGoodsid() {
            return goodsid;
        }

        public void setGoodsid(String goodsid) {
            this.goodsid = goodsid;
        }

        public String getGoodsspcid() {
            return goodsspcid;
        }

        public void setGoodsspcid(String goodsspcid) {
            this.goodsspcid = goodsspcid;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIsfreeshipping() {
            return isfreeshipping;
        }

        public void setIsfreeshipping(String isfreeshipping) {
            this.isfreeshipping = isfreeshipping;
        }

        public String getPrice() {
            return price;
        }

        public double getPriceValue() {
            return FormatterUtil.StringToDouble(price);
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getStock() {
            return stock;
        }

        public String getTag1() {
            return tag1;
        }

        public void setTag1(String tag1) {
            this.tag1 = tag1;
        }

        public String getTag2() {
            return tag2;
        }

        public void setTag2(String tag2) {
            this.tag2 = tag2;
        }

        public String getTag3() {
            return tag3;
        }

        public void setTag3(String tag3) {
            this.tag3 = tag3;
        }

        public String getCategoryid() {
            return categoryid;
        }

        public void setCategoryid(String categoryid) {
            this.categoryid = categoryid;
        }

        public String getSpectitle() {
            return spectitle;
        }

        public void setSpectitle(String spectitle) {
            this.spectitle = spectitle;
        }
    }


    public class Recommend{
        private String total;
        private String per_page;
        private String current_page;
        private String last_page;
        private List<GoodsItemBean> data;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getPer_page() {
            return per_page;
        }

        public void setPer_page(String per_page) {
            this.per_page = per_page;
        }

        public String getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(String current_page) {
            this.current_page = current_page;
        }

        public String getLast_page() {
            return last_page;
        }

        public void setLast_page(String last_page) {
            this.last_page = last_page;
        }

        public List<GoodsItemBean> getData() {
            return data;
        }

        public void setData(List<GoodsItemBean> data) {
            this.data = data;
        }
    }

    public class RecommendGoods{
        private String id;
        private String title;
        private String imgs;
        private String price;
        private String oldprice;
        private String salesvolume;
        private String salesvolumea;
        private String favorablerate;

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

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
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

        public String getSalesvolume() {
            return salesvolume;
        }

        public void setSalesvolume(String salesvolume) {
            this.salesvolume = salesvolume;
        }

        public String getSalesvolumea() {
            return salesvolumea;
        }

        public void setSalesvolumea(String salesvolumea) {
            this.salesvolumea = salesvolumea;
        }

        public String getFavorablerate() {
            return favorablerate;
        }

        public void setFavorablerate(String favorablerate) {
            this.favorablerate = favorablerate;
        }
    }




}
