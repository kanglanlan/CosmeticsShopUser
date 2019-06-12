package com.meida.cosmeticsshopuser.Bean;

import java.util.List;

/**
 * Created by Administrator on 2019/1/4 0004.
 */

public class SecondCommentBean extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean extends BaseListBean{
        private List<CommentItemBean> data;

        public List<CommentItemBean> getData() {
            return data;
        }

        public void setData(List<CommentItemBean> data) {
            this.data = data;
        }
    }


    public class CommentItemBean{

        private String id;
        private String sharingid;
        private String uid;
        private String createtime;
        private String content;
        private String imgs;
        private long like;
        private long comment;
        private String pid;
        private String touid;
        private String ownuserid;
        private String user_nickname;
        private String avatar;
        private String touser_nickname;
        private String show;

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

        public long getComment() {
            return comment;
        }

        public void setComment(long comment) {
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

        public String getOwnuserid() {
            return ownuserid;
        }

        public void setOwnuserid(String ownuserid) {
            this.ownuserid = ownuserid;
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

        public String getTouser_nickname() {
            return touser_nickname;
        }

        public void setTouser_nickname(String touser_nickname) {
            this.touser_nickname = touser_nickname;
        }

        public String getShow() {
            return show;
        }

        public void setShow(String show) {
            this.show = show;
        }
    }

}
