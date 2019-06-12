package com.meida.cosmeticsshopuser.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/1/2 0002.
 */

public class FindTypeBean extends BaseBean{


    private List<TypeBean> data;

    public List<TypeBean> getData() {
        return data;
    }

    public void setData(List<TypeBean> data) {
        this.data = data;
    }

    public static class TypeBean implements Serializable{
        private String id;
        private String flag;
        private String title;
        private String orderindex;
        private String status;
        private String parent_id;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }
    }

}
