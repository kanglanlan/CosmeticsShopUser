package com.meida.cosmeticsshopuser.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/12/27 0027.
 */

public class ShopDetailBean extends BaseBean{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean{
        private int id;
        private int uid;
        private String title;
        private String name;
        private int categoryid;
        private long areaid;
        private String address;
        private String doornum;
        private String linkman;
        private String img;
        private int stock;
        private int salesvolume;
        private int salesvolumea;
        private String distribution;
        private int status;
        private String longitude;
        private String latitude;
        private String avatar;
        private String background;
        private String protocol;
        private String phone;
        private String createtime;
        private String withdrawableamount;
        private String amountfcashwithdrawals;
        private String poundage;
        private String unsettled;
        private String alipay;
        private String truename;
        private String wechatnumber;
        private String wxtruename;
        private int look;
        private String reason;
        private String collection;
        private String iscollection;
        private String bonb;
        private int orderindex;
        private String isalipay;
        private int like;
        private int sharingnum;
        private String match;
        private String goods;
        private String attitude;
        private String speed;
        private long follow;
        private long praise;
        private long negative;
        private long mean;
        private String favorablerate;
        private String cid;
        private String merger_name;
        private String kfid;
        private String coupon;
        private Goods goodslist;
        private String sign;
        private String yysj1;

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getYysj1() {
            return yysj1;
        }

        public void setYysj1(String yysj1) {
            this.yysj1 = yysj1;
        }

        public String getIscollection() {
            return iscollection;
        }

        public void setIscollection(String iscollection) {
            this.iscollection = iscollection;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCategoryid() {
            return categoryid;
        }

        public void setCategoryid(int categoryid) {
            this.categoryid = categoryid;
        }

        public long getAreaid() {
            return areaid;
        }

        public void setAreaid(long areaid) {
            this.areaid = areaid;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDoornum() {
            return doornum;
        }

        public void setDoornum(String doornum) {
            this.doornum = doornum;
        }

        public String getLinkman() {
            return linkman;
        }

        public void setLinkman(String linkman) {
            this.linkman = linkman;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
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

        public String getDistribution() {
            return distribution;
        }

        public void setDistribution(String distribution) {
            this.distribution = distribution;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getBackground() {
            return background;
        }

        public void setBackground(String background) {
            this.background = background;
        }

        public String getProtocol() {
            return protocol;
        }

        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getWithdrawableamount() {
            return withdrawableamount;
        }

        public void setWithdrawableamount(String withdrawableamount) {
            this.withdrawableamount = withdrawableamount;
        }

        public String getAmountfcashwithdrawals() {
            return amountfcashwithdrawals;
        }

        public void setAmountfcashwithdrawals(String amountfcashwithdrawals) {
            this.amountfcashwithdrawals = amountfcashwithdrawals;
        }

        public String getPoundage() {
            return poundage;
        }

        public void setPoundage(String poundage) {
            this.poundage = poundage;
        }

        public String getUnsettled() {
            return unsettled;
        }

        public void setUnsettled(String unsettled) {
            this.unsettled = unsettled;
        }

        public String getAlipay() {
            return alipay;
        }

        public void setAlipay(String alipay) {
            this.alipay = alipay;
        }

        public String getTruename() {
            return truename;
        }

        public void setTruename(String truename) {
            this.truename = truename;
        }

        public String getWechatnumber() {
            return wechatnumber;
        }

        public void setWechatnumber(String wechatnumber) {
            this.wechatnumber = wechatnumber;
        }

        public String getWxtruename() {
            return wxtruename;
        }

        public void setWxtruename(String wxtruename) {
            this.wxtruename = wxtruename;
        }

        public int getLook() {
            return look;
        }

        public void setLook(int look) {
            this.look = look;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getCollection() {
            return collection;
        }

        public void setCollection(String collection) {
            this.collection = collection;
        }

        public String getBonb() {
            return bonb;
        }

        public void setBonb(String bonb) {
            this.bonb = bonb;
        }

        public int getOrderindex() {
            return orderindex;
        }

        public void setOrderindex(int orderindex) {
            this.orderindex = orderindex;
        }

        public String getIsalipay() {
            return isalipay;
        }

        public void setIsalipay(String isalipay) {
            this.isalipay = isalipay;
        }

        public int getLike() {
            return like;
        }

        public void setLike(int like) {
            this.like = like;
        }

        public int getSharingnum() {
            return sharingnum;
        }

        public void setSharingnum(int sharingnum) {
            this.sharingnum = sharingnum;
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

        public long getFollow() {
            return follow;
        }

        public void setFollow(long follow) {
            this.follow = follow;
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

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getMerger_name() {
            return merger_name;
        }

        public void setMerger_name(String merger_name) {
            this.merger_name = merger_name;
        }

        public String getKfid() {
            return kfid;
        }

        public void setKfid(String kfid) {
            this.kfid = kfid;
        }

        public String getCoupon() {
            return coupon;
        }

        public void setCoupon(String coupon) {
            this.coupon = coupon;
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
