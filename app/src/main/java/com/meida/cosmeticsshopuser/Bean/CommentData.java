package com.meida.cosmeticsshopuser.Bean;

/**
 * Created by Administrator on 2018/11/6 0006.
 *
 *获取系统时间
 */

public class CommentData {


    /**
     * code : 1
     * msg :
     * data : {"request_time":1529633924}
     */

    private String code;
    private String msg;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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

        private String request_time;
        private String is_collect;
        private String is_like;
        private String like_num;
        private String is_booking;
        private String booking_num;
        private String check_status;

        public String getLike_num() {
            return like_num;
        }

        public void setLike_num(String like_num) {
            this.like_num = like_num;
        }

        public String getCheck_status() {
            return check_status;
        }

        public void setCheck_status(String check_status) {
            this.check_status = check_status;
        }

        public String getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(String is_collect) {
            this.is_collect = is_collect;
        }

        public String getIs_like() {
            return is_like;
        }

        public void setIs_like(String is_like) {
            this.is_like = is_like;
        }

        public String getIs_booking() {
            return is_booking;
        }

        public void setIs_booking(String is_booking) {
            this.is_booking = is_booking;
        }

        public String getBooking_num() {
            return booking_num;
        }

        public void setBooking_num(String booking_num) {
            this.booking_num = booking_num;
        }

        public String getRequest_time() {
            return request_time;
        }

        public void setRequest_time(String request_time) {
            this.request_time = request_time;
        }
    }
}
