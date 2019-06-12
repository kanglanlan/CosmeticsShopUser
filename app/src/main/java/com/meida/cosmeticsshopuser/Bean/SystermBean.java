package com.meida.cosmeticsshopuser.Bean;

/**
 * Created by Administrator on 2018/12/25 0025.
 */

public class SystermBean extends BaseBean{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean{
        private String bond;
        private String rate;
        private String service;
        private String protect;

        public String getProtect() {
            return protect;
        }

        public void setProtect(String protect) {
            this.protect = protect;
        }

        public String getBond() {
            return bond;
        }

        public void setBond(String bond) {
            this.bond = bond;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }
    }

}
