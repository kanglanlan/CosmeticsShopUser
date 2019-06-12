package com.meida.cosmeticsshopuser.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/12/26 0026.
 */

public class ShopTypeBean extends BaseBean{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean extends BaseListBean{

        private List<Bean> data;

        public List<Bean> getData() {
            return data;
        }

        public void setData(List<Bean> data) {
            this.data = data;
        }

    }

    public class Bean{

        private String id;
        private String title;
        private String orderindex;
        private String img;
        private String flag;
        private String pid;
        private String status;
        private Child child;

        public Child getChild() {
            return child;
        }

        public void setChild(Child child) {
            this.child = child;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOrderindex() {
            return orderindex;
        }

        public void setOrderindex(String orderindex) {
            this.orderindex = orderindex;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public class Child extends BaseListBean{

        private List<TypeBean> data;

        public List<TypeBean> getData() {
            return data;
        }

        public void setData(List<TypeBean> data) {
            this.data = data;
        }
    }



}
