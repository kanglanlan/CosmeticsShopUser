package com.meida.cosmeticsshopuser.Bean;

import java.util.List;

/**
 * Created by Administrator on 2019/1/5 0005.
 */

public class FreightBean extends BaseBean{

    private List<Freight> data;

    public List<Freight> getData() {
        return data;
    }

    public void setData(List<Freight> data) {
        this.data = data;
    }

    public class Freight{

        private String id;
        private String show;
        private String rateprice;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShow() {
            return show;
        }

        public void setShow(String show) {
            this.show = show;
        }

        public String getRateprice() {
            return rateprice;
        }

        public void setRateprice(String rateprice) {
            this.rateprice = rateprice;
        }
    }

}
