package com.meida.cosmeticsshopuser.Bean;

import java.util.List;

/**
 * Created by Administrator on 2019/1/5 0005.
 */

public class OrderDetailBean extends BaseBean{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean {

        private String id;
        private String shopid;
        private String number;
        private String goodsnum;
        private String amount;
        private String freight;
        private String discountamount;
        private String ucid;
        private String total;
        private String receiver;
        private String phone;
        private String address;
        private String housenumber;
        private String longitude;
        private String latitude;
        private String create_time;
        private String uid;
        private String status;
        private String statustime;
        private String memo;
        private int distribution;
        private int issaledelete;
        private int isbuydelete;
        private String pay_type;
        private String linkman1;
        private String phone1;
        private String linkman2;
        private String phone2;
        private String openfh;
        private String openfhstatus;
        private String title;
        private List<ShopCartBean.CartGoods> item;
        private String goods;


        public String getGoods() {
            return goods;
        }

        public void setGoods(String goods) {
            this.goods = goods;
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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getStatustime() {
            return statustime;
        }

        public void setStatustime(String statustime) {
            this.statustime = statustime;
        }

        public List<ShopCartBean.CartGoods> getItem() {
            return item;
        }

        public void setItem(List<ShopCartBean.CartGoods> item) {
            this.item = item;
        }

        public void setNumber(String number) {
            this.number = number;
        }
        public String getNumber() {
            return number;
        }

        public String getGoodsnum() {
            return goodsnum;
        }

        public void setGoodsnum(String goodsnum) {
            this.goodsnum = goodsnum;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
        public String getAmount() {
            return amount;
        }

        public void setFreight(String freight) {
            this.freight = freight;
        }
        public String getFreight() {
            return freight;
        }

        public void setDiscountamount(String discountamount) {
            this.discountamount = discountamount;
        }
        public String getDiscountamount() {
            return discountamount;
        }

        public void setUcid(String ucid) {
            this.ucid = ucid;
        }
        public String getUcid() {
            return ucid;
        }

        public void setTotal(String total) {
            this.total = total;
        }
        public String getTotal() {
            return total;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }
        public String getReceiver() {
            return receiver;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
        public String getPhone() {
            return phone;
        }

        public void setAddress(String address) {
            this.address = address;
        }
        public String getAddress() {
            return address;
        }

        public void setHousenumber(String housenumber) {
            this.housenumber = housenumber;
        }
        public String getHousenumber() {
            return housenumber;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
        public String getLongitude() {
            return longitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }
        public String getLatitude() {
            return latitude;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }
        public String getMemo() {
            return memo;
        }

        public void setDistribution(int distribution) {
            this.distribution = distribution;
        }
        public int getDistribution() {
            return distribution;
        }

        public void setIssaledelete(int issaledelete) {
            this.issaledelete = issaledelete;
        }
        public int getIssaledelete() {
            return issaledelete;
        }

        public void setIsbuydelete(int isbuydelete) {
            this.isbuydelete = isbuydelete;
        }
        public int getIsbuydelete() {
            return isbuydelete;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public void setLinkman1(String linkman1) {
            this.linkman1 = linkman1;
        }
        public String getLinkman1() {
            return linkman1;
        }

        public void setPhone1(String phone1) {
            this.phone1 = phone1;
        }
        public String getPhone1() {
            return phone1;
        }

        public void setLinkman2(String linkman2) {
            this.linkman2 = linkman2;
        }
        public String getLinkman2() {
            return linkman2;
        }

        public void setPhone2(String phone2) {
            this.phone2 = phone2;
        }
        public String getPhone2() {
            return phone2;
        }

        public String getOpenfh() {
            return openfh;
        }

        public void setOpenfh(String openfh) {
            this.openfh = openfh;
        }

        public String getOpenfhstatus() {
            return openfhstatus;
        }

        public void setOpenfhstatus(String openfhstatus) {
            this.openfhstatus = openfhstatus;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }


        private Comment comment;
        private SendGoods sendgoods;

        public Comment getComment() {
            return comment;
        }

        public void setComment(Comment comment) {
            this.comment = comment;
        }

        public SendGoods getSendgoods() {
            return sendgoods;
        }

        public void setSendgoods(SendGoods sendgoods) {
            this.sendgoods = sendgoods;
        }
    }


    public class Comment{
        private String id;
        private String goodsid;
        private String uid;
        private String createtime;
        private String content;
        private String imgs;
        private String anonymous;
        private String match;
        private String goods;
        private String attitude;
        private String speed;
        private String shopid;
        private String orderid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGoodsid() {
            return goodsid;
        }

        public void setGoodsid(String goodsid) {
            this.goodsid = goodsid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }

        public String getAnonymous() {
            return anonymous;
        }

        public void setAnonymous(String anonymous) {
            this.anonymous = anonymous;
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
    }

    public class SendGoods{
        private String distribution;
        private String id;
        private String oldstatus;
        private String newstatus;
        private String contacts;
        private String phone;
        private String imgs;

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }

        public String getDistribution() {
            return distribution;
        }

        public void setDistribution(String distribution) {
            this.distribution = distribution;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOldstatus() {
            return oldstatus;
        }

        public void setOldstatus(String oldstatus) {
            this.oldstatus = oldstatus;
        }

        public String getNewstatus() {
            return newstatus;
        }

        public void setNewstatus(String newstatus) {
            this.newstatus = newstatus;
        }

        public String getContacts() {
            return contacts;
        }

        public void setContacts(String contacts) {
            this.contacts = contacts;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }


}
