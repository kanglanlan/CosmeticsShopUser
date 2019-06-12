package com.meida.cosmeticsshopuser.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/1/2 0002.
 */

public class FindDetailBean {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean{

        private String id;
        private String flag;
        private String classid;
        private long like;
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
        private String show;
        private String follow;
        private ShopInfoBean shopinfo;
        private UserInfo userinfo;

        private CommentBean commentlist;


        public ShopInfoBean getShopinfo() {
            return shopinfo;
        }

        public void setShopinfo(ShopInfoBean shopinfo) {
            this.shopinfo = shopinfo;
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

        public long getLike() {
            return like;
        }

        public void setLike(long like) {
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

        public String getShow() {
            return show;
        }

        public void setShow(String show) {
            this.show = show;
        }

        public String getFollow() {
            return follow;
        }

        public void setFollow(String follow) {
            this.follow = follow;
        }

        public CommentBean getCommentlist() {
            return commentlist;
        }

        public void setCommentlist(CommentBean commentlist) {
            this.commentlist = commentlist;
        }
    }

    public class CommentBean extends BaseListBean{
        private List<CommentItemBean> data;

        public List<CommentItemBean> getData() {
            return data;
        }

        public void setData(List<CommentItemBean> data) {
            this.data = data;
        }
    }

    public class CommentItemBean implements Serializable{
        private String id;
        private String sharingid;
        private String uid;
        private String createtime;
        private String content;
        private String imgs;
        private long like;
        private String comment;
        private String pid;
        private String touid;
        private String user_nickname;
        private String avatar;
        private String show;/*0 未点赞  1已点赞*/
        private String follow;/*0 未关注  1已关注*/
        private List<CommentChild> child;

        public List<CommentChild> getChild() {
            return child;
        }

        public void setChild(List<CommentChild> child) {
            this.child = child;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSharingid() {
            return sharingid;
        }

        public void setSharingid(String sharingid) {
            this.sharingid = sharingid;
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

        public long getLike() {
            return like;
        }

        public void setLike(long like) {
            this.like = like;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getTouid() {
            return touid;
        }

        public void setTouid(String touid) {
            this.touid = touid;
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

        public String getShow() {
            return show;
        }

        public void setShow(String show) {
            this.show = show;
        }

        public String getFollow() {
            return follow;
        }

        public void setFollow(String follow) {
            this.follow = follow;
        }
    }


    public class CommentChild implements Serializable{

        private String id;
        private String sharingid;
        private String content;
        private String user_nickname;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSharingid() {
            return sharingid;
        }

        public void setSharingid(String sharingid) {
            this.sharingid = sharingid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUser_nickname() {
            return user_nickname;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
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
