package com.meida.cosmeticsshopuser.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/12/27 0027.
 */

public class ShopEventBean extends BaseBean{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean extends BaseListBean{
        private List<ShopEventItemBean> data;

        public List<ShopEventItemBean> getData() {
            return data;
        }

        public void setData(List<ShopEventItemBean> data) {
            this.data = data;
        }


    }


    public class ShopEventItemBean{

        private String id;
        private String flag;
        private String classid;
        private String like;
        private String hits;
        private String comment;
        private String content;
        private String imgs;
        private String address;
        private String longitude;
        private String latitude;
        private String isaddress;
        private String shopid;
        private String uid;
        private String createtime;
        private String user_nickname;
        private String avatar;
        private String islike;

        private FindListBean.ShopInfoBean shopinfo;
        private FindListBean.UserInfo userinfo;

        public FindListBean.ShopInfoBean getShopinfo() {
            return shopinfo;
        }

        public void setShopinfo(FindListBean.ShopInfoBean shopinfo) {
            this.shopinfo = shopinfo;
        }

        public FindListBean.UserInfo getUserinfo() {
            return userinfo;
        }

        public void setUserinfo(FindListBean.UserInfo userinfo) {
            this.userinfo = userinfo;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getClassid() {
            return classid;
        }

        public void setClassid(String classid) {
            this.classid = classid;
        }

        public String getLike() {
            return like;
        }

        public void setLike(String like) {
            this.like = like;
        }

        public String getHits() {
            return hits;
        }

        public void setHits(String hits) {
            this.hits = hits;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public String getIsaddress() {
            return isaddress;
        }

        public void setIsaddress(String isaddress) {
            this.isaddress = isaddress;
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

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getUser_nickname() {
            return user_nickname;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getIslike() {
            return islike;
        }

        public void setIslike(String islike) {
            this.islike = islike;
        }
    }




}
