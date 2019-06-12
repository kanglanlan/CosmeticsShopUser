package com.meida.cosmeticsshopuser.Bean;

import java.util.List;

/**
 * Created by LFC
 * on 2018/11/30.
 */

public class RongUserInfoBean
{


    /**
     * code : 1
     * msg : 成功!
     * data : {"list":[{"id":2,"logo":"http://hd-upload-img.oss-cn-shenzhen.aliyuncs.com/file_direct_oss/20181121/1542781505_632507.jpg","nickname":"张三"},{"id":3,"logo":"","nickname":"李四"}]}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ListBean> data;

        public List<ListBean> getData() {
            return data;
        }

        public void setData(List<ListBean> data) {
            this.data = data;
        }

        public static class ListBean {
            /**
             * id : 2
             * logo : http://hd-upload-img.oss-cn-shenzhen.aliyuncs.com/file_direct_oss/20181121/1542781505_632507.jpg
             * nickname : 张三
             */

            private int id;
            private String avatar;
            private String user_nickname;
            private String shopid;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getUser_nickname() {
                return user_nickname;
            }

            public void setUser_nickname(String user_nickname) {
                this.user_nickname = user_nickname;
            }

            public String getShopid() {
                return shopid;
            }

            public void setShopid(String shopid) {
                this.shopid = shopid;
            }
        }
    }
}
