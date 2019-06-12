package com.meida.cosmeticsshopuser.Bean;

import java.util.List;

/**
 * Created by Administrator on 2019/1/7 0007.
 */

public class MessageBean extends BaseBean{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean extends BaseBean{
        private List<MessageItemBean> data;

        public List<MessageItemBean> getData() {
            return data;
        }

        public void setData(List<MessageItemBean> data) {
            this.data = data;
        }
    }

    public class MessageItemBean{

        private String id;
        private String message_type;
        private String message_id;
        private String content;
        private String createtime;
        private String title;
        private String look;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMessage_type() {
            return message_type;
        }

        public void setMessage_type(String message_type) {
            this.message_type = message_type;
        }

        public String getMessage_id() {
            return message_id;
        }

        public void setMessage_id(String message_id) {
            this.message_id = message_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLook() {
            return look;
        }

        public void setLook(String look) {
            this.look = look;
        }
    }


}
