package com.meida.cosmeticsshopuser.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/12/28 0028.
 */

public class GoodsDetailBean extends BaseBean{


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
        private int status;
        private int isrecommend;
        private String title;
        private String img;
        private int tag1;
        private int tag2;
        private int tag3;
        private int tag4;
        private int tag5;
        private int tag6;
        private long salesvolume;
        private long salesvolumea;
        private int stock;
        private String imgs;
        private String createtime;
        private int categoryid;
        private String content;
        private String contentimgs;
        private int isrecommendshop;
        private String isfreeshipping;/*是否包邮*/
        private String isfreesend;/*	包送 1、是 2 否*/
        private String isfreedelivery;/*包配送 1、是 2 否*/
        private String distribution1;/*同城配送*/
        private String distribution2;/*商家自送*/
        private String distribution3;/*快递*/
        private int isopen;
        private int pid;
        private int look;
        private int collection;
        private String aftersale;
        private String returngoods;
        private int orderindex;
        private String match;
        private String goods;
        private String attitude;
        private String speed;
        private long praise;
        private long negative;
        private long mean;
        private String favorablerate;
        private String price;
        private String oldprice;
        private String shoptitle;
        private String shopuid;
        private String iscollect;
        private String kfid;
        private List<Specify> spec;
        private String spectitle;
        private List<ShopEvalBean.ShopEvalItemBean> commentlist;
        private Goods goodslist;

        private ShopInfo shopinfo;

        public ShopInfo getShopinfo() {
            return shopinfo;
        }

        public void setShopinfo(ShopInfo shopinfo) {
            this.shopinfo = shopinfo;
        }

        public class ShopInfo{
            private String title;
            private String avatar;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getIsrecommend() {
            return isrecommend;
        }

        public void setIsrecommend(int isrecommend) {
            this.isrecommend = isrecommend;
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

        public int getTag1() {
            return tag1;
        }

        public void setTag1(int tag1) {
            this.tag1 = tag1;
        }

        public int getTag2() {
            return tag2;
        }

        public void setTag2(int tag2) {
            this.tag2 = tag2;
        }

        public int getTag3() {
            return tag3;
        }

        public void setTag3(int tag3) {
            this.tag3 = tag3;
        }

        public int getTag4() {
            return tag4;
        }

        public void setTag4(int tag4) {
            this.tag4 = tag4;
        }

        public int getTag5() {
            return tag5;
        }

        public void setTag5(int tag5) {
            this.tag5 = tag5;
        }

        public int getTag6() {
            return tag6;
        }

        public void setTag6(int tag6) {
            this.tag6 = tag6;
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

        public int getStock() {
            return stock;
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

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public int getCategoryid() {
            return categoryid;
        }

        public void setCategoryid(int categoryid) {
            this.categoryid = categoryid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContentimgs() {
            return contentimgs;
        }

        public void setContentimgs(String contentimgs) {
            this.contentimgs = contentimgs;
        }

        public int getIsrecommendshop() {
            return isrecommendshop;
        }

        public void setIsrecommendshop(int isrecommendshop) {
            this.isrecommendshop = isrecommendshop;
        }

        public String getIsfreeshipping() {
            return isfreeshipping;
        }

        public void setIsfreeshipping(String isfreeshipping) {
            this.isfreeshipping = isfreeshipping;
        }

        public String getDistribution1() {
            return distribution1;
        }

        public void setDistribution1(String distribution1) {
            this.distribution1 = distribution1;
        }

        public String getDistribution2() {
            return distribution2;
        }

        public void setDistribution2(String distribution2) {
            this.distribution2 = distribution2;
        }

        public String getDistribution3() {
            return distribution3;
        }

        public void setDistribution3(String distribution3) {
            this.distribution3 = distribution3;
        }

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

        public int getIsopen() {
            return isopen;
        }

        public void setIsopen(int isopen) {
            this.isopen = isopen;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getLook() {
            return look;
        }

        public void setLook(int look) {
            this.look = look;
        }

        public int getCollection() {
            return collection;
        }

        public void setCollection(int collection) {
            this.collection = collection;
        }

        public String getAftersale() {
            return aftersale;
        }

        public void setAftersale(String aftersale) {
            this.aftersale = aftersale;
        }

        public String getReturngoods() {
            return returngoods;
        }

        public void setReturngoods(String returngoods) {
            this.returngoods = returngoods;
        }

        public int getOrderindex() {
            return orderindex;
        }

        public void setOrderindex(int orderindex) {
            this.orderindex = orderindex;
        }

        public String getMatch() {
            return match;
        }

        public void setMatch(String match) {
            this.match = match;
        }

        public String getGoods() {
            return goods;
        }

        public void setGoods(String goods) {
            this.goods = goods;
        }

        public String getAttitude() {
            return attitude;
        }

        public void setAttitude(String attitude) {
            this.attitude = attitude;
        }

        public String getSpeed() {
            return speed;
        }

        public void setSpeed(String speed) {
            this.speed = speed;
        }

        public long getPraise() {
            return praise;
        }

        public void setPraise(long praise) {
            this.praise = praise;
        }

        public long getNegative() {
            return negative;
        }

        public void setNegative(long negative) {
            this.negative = negative;
        }

        public long getMean() {
            return mean;
        }

        public void setMean(long mean) {
            this.mean = mean;
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

        public String getShoptitle() {
            return shoptitle;
        }

        public void setShoptitle(String shoptitle) {
            this.shoptitle = shoptitle;
        }

        public String getShopuid() {
            return shopuid;
        }

        public void setShopuid(String shopuid) {
            this.shopuid = shopuid;
        }

        public String getIscollect() {
            return iscollect;
        }

        public void setIscollect(String iscollect) {
            this.iscollect = iscollect;
        }

        public String getKfid() {
            return kfid;
        }

        public void setKfid(String kfid) {
            this.kfid = kfid;
        }

        public List<Specify> getSpec() {
            return spec;
        }

        public void setSpec(List<Specify> spec) {
            this.spec = spec;
        }

        public String getSpectitle() {
            return spectitle;
        }

        public void setSpectitle(String spectitle) {
            this.spectitle = spectitle;
        }

        public List<ShopEvalBean.ShopEvalItemBean> getCommentlist() {
            return commentlist;
        }

        public void setCommentlist(List<ShopEvalBean.ShopEvalItemBean> commentlist) {
            this.commentlist = commentlist;
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


    public class Specify{

        private String id;
        private String status;
        private String goodsid;
        private String price;
        private String title;
        private int stock;
        private int salesvolume;
        private int salesvolumea;
        private String groupprice;
        private String oldprice;
        private String pid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getGoodsid() {
            return goodsid;
        }

        public void setGoodsid(String goodsid) {
            this.goodsid = goodsid;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
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

        public String getGroupprice() {
            return groupprice;
        }

        public void setGroupprice(String groupprice) {
            this.groupprice = groupprice;
        }

        public String getOldprice() {
            return oldprice;
        }

        public void setOldprice(String oldprice) {
            this.oldprice = oldprice;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }
    }


}
