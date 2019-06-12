package com.meida.cosmeticsshopuser.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/12/22 0022.
 */

public class CityBean extends BaseBean{


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean{
        private List<Area> hot;
        private List<City> city;

        public List<Area> getHot() {
            return hot;
        }

        public void setHot(List<Area> hot) {
            this.hot = hot;
        }

        public List<City> getCity() {
            return city;
        }

        public void setCity(List<City> city) {
            this.city = city;
        }
    }


    public static class City implements Serializable{
        private String pinyin;
        private List<Area> lists;

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        public List<Area> getLists() {
            return lists;
        }

        public void setLists(List<Area> lists) {
            this.lists = lists;
        }
    }


    public static class Area implements Serializable{
        private String id;
        private String name;
        private String parent_id;
        private String short_name;
        private String level_type;
        private String city_code;
        private String zipcode;
        private String merger_name;
        private String lng;
        private String lat;
        private String pinyin;
        private String status;
        private String list_order;
        private String ishot;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getShort_name() {
            return short_name;
        }

        public void setShort_name(String short_name) {
            this.short_name = short_name;
        }

        public String getLevel_type() {
            return level_type;
        }

        public void setLevel_type(String level_type) {
            this.level_type = level_type;
        }

        public String getCity_code() {
            return city_code;
        }

        public void setCity_code(String city_code) {
            this.city_code = city_code;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getMerger_name() {
            return merger_name;
        }

        public void setMerger_name(String merger_name) {
            this.merger_name = merger_name;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getList_order() {
            return list_order;
        }

        public void setList_order(String list_order) {
            this.list_order = list_order;
        }

        public String getIshot() {
            return ishot;
        }

        public void setIshot(String ishot) {
            this.ishot = ishot;
        }
    }

}
