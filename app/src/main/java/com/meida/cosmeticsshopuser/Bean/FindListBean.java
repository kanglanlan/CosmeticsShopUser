package com.meida.cosmeticsshopuser.Bean;

import java.util.List;

/**
 * Created by Administrator on 2019/1/2 0002.
 */

public class FindListBean extends BaseBean{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean extends BaseListBean{

        private List<FindItemBean> data;

        public List<FindItemBean> getData() {
            return data;
        }

        public void setData(List<FindItemBean> data) {
            this.data = data;
        }
    }


    public static class FindItemBean{

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
        private ShopInfoBean shopinfo;
        private UserInfo userinfo;
        private String lookid;

        public String getLookid() {
            return lookid;
        }

        public void setLookid(String lookid) {
            this.lookid = lookid;
        }

        public UserInfo getUserinfo() {
            return userinfo;
        }

        public void setUserinfo(UserInfo userinfo) {
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

        public ShopInfoBean getShopinfo() {
            return shopinfo;
        }

        public void setShopinfo(ShopInfoBean shopinfo) {
            this.shopinfo = shopinfo;
        }
    }




    public static class ShopInfoBean{
        private String id;
        private String avatar;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }


    public static class UserInfo{
        private String id;
        private String avatar;
        private String user_nickname;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getTitle() {
            return user_nickname;
        }

        public void setTitle(String title) {
            this.user_nickname = title;
        }
    }



}
