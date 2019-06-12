package com.meida.cosmeticsshopuser.Bean;

import java.util.List;

/**
 * Created by Administrator on 2019/1/5 0005.
 */

public class OrderBean extends BaseBean{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean extends BaseListBean{
        private List<OrderItemBean> data;

        public List<OrderItemBean> getData() {
            return data;
        }

        public void setData(List<OrderItemBean> data) {
            this.data = data;
        }
    }



    public class OrderItemBean {

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
        private String distribution;
        private int issaledelete;
        private int isbuydelete;
        private int pay_type;
        private String linkman1;
        private String phone1;
        private String linkman2;
        private String phone2;
        private int openfh;
        private int openfhstatus;
        private String title;
        private String avatar;
        private String goods;
        private String kfid;
        private List<GoodsItemBean> item;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public void setStatustime(String statustime) {
            this.statustime = statustime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<GoodsItemBean> getItem() {
            return item;
        }

        public void setItem(List<GoodsItemBean> item) {
            this.item = item;
        }

        public String getStatustime() {
            return statustime;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }
        public String getMemo() {
            return memo;
        }

        public String getDistribution() {
            return distribution;
        }

        public void setDistribution(String distribution) {
            this.distribution = distribution;
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

        public void setPay_type(int pay_type) {
            this.pay_type = pay_type;
        }
        public int getPay_type() {
            return pay_type;
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

        public void setOpenfh(int openfh) {
            this.openfh = openfh;
        }
        public int getOpenfh() {
            return openfh;
        }

        public void setOpenfhstatus(int openfhstatus) {
            this.openfhstatus = openfhstatus;
        }
        public int getOpenfhstatus() {
            return openfhstatus;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
        public String getAvatar() {
            return avatar;
        }

        public void setGoods(String goods) {
            this.goods = goods;
        }
        public String getGoods() {
            return goods;
        }


        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getKfid() {
            return kfid;
        }

        public void setKfid(String kfid) {
            this.kfid = kfid;
        }
    }

    /**
     * Auto-generated: 2019-01-05 18:24:41
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class GoodsItemBean {

        private int id;
        private int shopid;
        private int orderid;
        private int goodsid;
        private String title;
        private int goodsspcid;
        private String spectitle;
        private String price;
        private int num;
        private String img;
        private String salesvolume;
        private String salesvolumea;
        private String stock;
        private String imgs;
        private int categoryid;
        private String content;
        private String contentimgs;
        private String isrecommendshop;
        private String isfreeshipping;
        private String isopen;
        private int pid;
        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public void setShopid(int shopid) {
            this.shopid = shopid;
        }
        public int getShopid() {
            return shopid;
        }

        public void setOrderid(int orderid) {
            this.orderid = orderid;
        }
        public int getOrderid() {
            return orderid;
        }

        public void setGoodsid(int goodsid) {
            this.goodsid = goodsid;
        }
        public int getGoodsid() {
            return goodsid;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }

        public void setGoodsspcid(int goodsspcid) {
            this.goodsspcid = goodsspcid;
        }
        public int getGoodsspcid() {
            return goodsspcid;
        }

        public void setSpectitle(String spectitle) {
            this.spectitle = spectitle;
        }
        public String getSpectitle() {
            return spectitle;
        }

        public void setPrice(String price) {
            this.price = price;
        }
        public String getPrice() {
            return price;
        }

        public void setNum(int num) {
            this.num = num;
        }
        public int getNum() {
            return num;
        }

        public void setImg(String img) {
            this.img = img;
        }
        public String getImg() {
            return img;
        }

        public void setSalesvolume(String salesvolume) {
            this.salesvolume = salesvolume;
        }
        public String getSalesvolume() {
            return salesvolume;
        }

        public void setSalesvolumea(String salesvolumea) {
            this.salesvolumea = salesvolumea;
        }
        public String getSalesvolumea() {
            return salesvolumea;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }
        public String getStock() {
            return stock;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }
        public String getImgs() {
            return imgs;
        }

        public void setCategoryid(int categoryid) {
            this.categoryid = categoryid;
        }
        public int getCategoryid() {
            return categoryid;
        }

        public void setContent(String content) {
            this.content = content;
        }
        public String getContent() {
            return content;
        }

        public void setContentimgs(String contentimgs) {
            this.contentimgs = contentimgs;
        }
        public String getContentimgs() {
            return contentimgs;
        }

        public void setIsrecommendshop(String isrecommendshop) {
            this.isrecommendshop = isrecommendshop;
        }
        public String getIsrecommendshop() {
            return isrecommendshop;
        }

        public void setIsfreeshipping(String isfreeshipping) {
            this.isfreeshipping = isfreeshipping;
        }
        public String getIsfreeshipping() {
            return isfreeshipping;
        }

        public void setIsopen(String isopen) {
            this.isopen = isopen;
        }
        public String getIsopen() {
            return isopen;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }
        public int getPid() {
            return pid;
        }

    }


}
