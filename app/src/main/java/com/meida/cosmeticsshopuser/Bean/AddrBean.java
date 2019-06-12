package com.meida.cosmeticsshopuser.Bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/1/4 0004.
 */

public class AddrBean extends BaseBean{

    private List<AddrItemBean> data;

    public List<AddrItemBean> getData() {
        return data;
    }

    public void setData(List<AddrItemBean> data) {
        this.data = data;
    }

    public static class AddrItemBean implements Serializable{

        private String id ;
        private String uid ;
        @SerializedName(value = "default")
        private String defaultStr ;
        private String consignee ;
        private String mobile ;
        private String address;
        private String doornumber;
        private String longitude ;
        private String latitude ;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getDefaultStr() {
            return defaultStr;
        }

        public void setDefaultStr(String defaultStr) {
            this.defaultStr = defaultStr;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDoornumber() {
            return doornumber;
        }

        public void setDoornumber(String doornumber) {
            this.doornumber = doornumber;
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
    }


}
