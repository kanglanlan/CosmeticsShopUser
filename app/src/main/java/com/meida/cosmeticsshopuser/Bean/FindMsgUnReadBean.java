package com.meida.cosmeticsshopuser.Bean;

/**
 * Created by Administrator on 2019/3/6 0006.
 */

public class FindMsgUnReadBean extends BaseBean{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean{
        private String count;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }

}
