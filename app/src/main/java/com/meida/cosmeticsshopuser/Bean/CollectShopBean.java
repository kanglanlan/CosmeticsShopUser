package com.meida.cosmeticsshopuser.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/12/28 0028.
 */

public class CollectShopBean extends BaseBean{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean extends BaseListBean{
        private List<CollectShop> data;

        public List<CollectShop> getData() {
            return data;
        }

        public void setData(List<CollectShop> data) {
            this.data = data;
        }
    }

    public static class CollectShop{
        private String id;
        private String title;
        private String name;
        private String address;
        private String img;
        private int salesvolume;
        private int salesvolumea;
        private String avatar;
        private String background;
        private String protocol;
        private String phone;
        private int look;
        private int collection;
        private int like;
        private int sharingnum;
        private String favorablerate;
        private String rytoken;
        private int colledtid;
        private int juli;
        private String goods;
        private String lookid;

        public String getLookid() {
            return lookid;
        }

        public void setLookid(String lookid) {
            this.lookid = lookid;
        }


        public String getGoods() {
            return goods;
        }

        public void setGoods(String goods) {
            this.goods = goods;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getSalesvolume() {
            return salesvolume;
        }

        public void setSalesvolume(int salesvolume) {
            this.salesvolume = salesvolume;
        }

        public int getSalesvolumea() {
            return salesvolumea;
        }

        public void setSalesvolumea(int salesvolumea) {
            this.salesvolumea = salesvolumea;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getBackground() {
            return background;
        }

        public void setBackground(String background) {
            this.background = background;
        }

        public String getProtocol() {
            return protocol;
        }

        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getLook() {
            return look;
        }

        public void setLook(int look) {
            this.look = look;
        }

        public int getCollection() {
            return collection;
        }

        public void setCollection(int collection) {
            this.collection = collection;
        }

        public int getLike() {
            return like;
        }

        public void setLike(int like) {
            this.like = like;
        }

        public int getSharingnum() {
            return sharingnum;
        }

        public void setSharingnum(int sharingnum) {
            this.sharingnum = sharingnum;
        }

        public String getFavorablerate() {
            return favorablerate;
        }

        public void setFavorablerate(String favorablerate) {
            this.favorablerate = favorablerate;
        }

        public String getRytoken() {
            return rytoken;
        }

        public void setRytoken(String rytoken) {
            this.rytoken = rytoken;
        }

        public int getColledtid() {
            return colledtid;
        }

        public void setColledtid(int colledtid) {
            this.colledtid = colledtid;
        }

        public int getJuli() {
            return juli;
        }

        public void setJuli(int juli) {
            this.juli = juli;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

}
