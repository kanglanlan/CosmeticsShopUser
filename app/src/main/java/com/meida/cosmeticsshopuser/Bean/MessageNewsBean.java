package com.meida.cosmeticsshopuser.Bean;

import java.util.List;

/**
 * Created by Administrator on 2019/1/7 0007.
 */

public class MessageNewsBean extends BaseBean{


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean extends BaseListBean{

        private List<MessageNewsItemBean> data;


        public List<MessageNewsItemBean> getData() {
            return data;
        }

        public void setData(List<MessageNewsItemBean> data) {
            this.data = data;
        }
    }

    public class MessageNewsItemBean{

        private String id;
        private String createtime;/*TODO 两个时间*/
        private News news;
        private String look;

        public String getLook() {
            return look;
        }

        public void setLook(String look) {
            this.look = look;
        }

        public News getNews() {
            return news;
        }

        public void setNews(News news) {
            this.news = news;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public class News{

           /* @SerializedName("id")
            private String id2 ;*/
            private String thumbnail;
            private String createtime;
            private String id ;
            private String post_hits;
            private String post_title;

          /*  public String getId2() {
                return id2;
            }

            public void setId2(String id2) {
                this.id2 = id2;
            }*/

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPost_hits() {
                return post_hits;
            }

            public void setPost_hits(String post_hits) {
                this.post_hits = post_hits;
            }

            public String getPost_title() {
                return post_title;
            }

            public void setPost_title(String post_title) {
                this.post_title = post_title;
            }
        }

    }


}
