package com.meida.cosmeticsshopuser.Bean;

/**
 * Created by Administrator on 2018/12/25 0025.
 */

public class NewsItemBean {

    private String id;
    private String post_title;
    private String createtime;/*TODO 两个时间*/
    private String thumbnail;
    private String post_hits;
    private String lookid;
    private String create_time;

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getLookid() {
        return lookid;
    }

    public void setLookid(String lookid) {
        this.lookid = lookid;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPost_hits() {
        return post_hits;
    }

    public void setPost_hits(String post_hits) {
        this.post_hits = post_hits;
    }
}
